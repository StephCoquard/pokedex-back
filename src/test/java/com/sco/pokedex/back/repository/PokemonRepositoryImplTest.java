package com.sco.pokedex.back.repository;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.sco.pokedex.back.dto.PokemonDto;
import com.sco.pokedex.back.dto.filter.IntegerFilterDescDto;
import com.sco.pokedex.back.dto.filter.StringFilterDescDto;
import com.sco.pokedex.back.filter.FilterBuilder;
import com.sco.pokedex.back.filter.FilterFactory;
import com.sco.pokedex.back.mapper.PokemonMapperImpl;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PokemonRepositoryImplTest {

    private PokemonRepositoryImpl repository;

    private FilterBuilder filterBuilder;

    private final WireMockServer server = new WireMockServer(8080);

    @BeforeEach
    void init() {
        filterBuilder = new FilterBuilder(new FilterFactory());
        repository = new PokemonRepositoryImpl(
                WebClient.builder().baseUrl("http://localhost:8080").build(),
                "/pokemon",
                new PokemonMapperImpl());

        server.start();
        TestUtils.initStubs(server);
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    @Test
    void getFilteredPokemons_should_return_filtered_pokemons_with_lower_than() {
        // Given
        var filterDescs = List.of(
                new StringFilterDescDto("name", "v"),
                new IntegerFilterDescDto("weight", IntegerFilterDescDto.OperationTypeEnum.LOWER_THAN, 90)
        );
        var filterChain = filterBuilder.apply(filterDescs);

        // When
        var result = repository.getFilteredPokemons(filterChain, 3, 0);

        // Then
        StepVerifier.create(result)
                .assertNext(pokemons -> assertThat(pokemons)
                        .satisfies(p -> assertThat(p.filteredCount()).isEqualTo(1))
                        .satisfies(p -> assertThat(p.totalCount()).isEqualTo(3))
                        .satisfies(p -> assertThat(p.results()).extracting(PokemonDto::getName, PokemonDto::getSize, PokemonDto::getWeight)
                                .contains(Tuple.tuple("venusaur", 7, 50))))
                .verifyComplete();
    }

    @Test
    void getFilteredPokemons_should_return_filtered_pokemons_with_greater_than() {
        // Given
        var filterDescs = List.of(
                new StringFilterDescDto("name", "v"),
                new IntegerFilterDescDto("weight", IntegerFilterDescDto.OperationTypeEnum.GREATER_THAN, 90)
        );
        var filterChain = filterBuilder.apply(filterDescs);

        // When
        var result = repository.getFilteredPokemons(filterChain, 3, 0);

        // Then
        StepVerifier.create(result)
                .assertNext(pokemons -> assertThat(pokemons)
                        .satisfies(p -> assertThat(p.filteredCount()).isEqualTo(1))
                        .satisfies(p -> assertThat(p.totalCount()).isEqualTo(3))
                        .satisfies(p -> assertThat(p.results()).extracting(PokemonDto::getName, PokemonDto::getSize, PokemonDto::getWeight)
                                .contains(Tuple.tuple("ivysaur", 5, 100))))
                .verifyComplete();
    }
}
