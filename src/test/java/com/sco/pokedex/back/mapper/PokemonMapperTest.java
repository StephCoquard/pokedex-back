package com.sco.pokedex.back.mapper;

import com.sco.pokedex.back.dto.PokemonDto;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import skaro.pokeapi.resource.pokemon.Pokemon;

import static org.assertj.core.api.Assertions.assertThat;

class PokemonMapperTest {

    private final PokemonMapper mapper = Mappers.getMapper(PokemonMapper.class);

    @Test
    void map_should_map_pokemon() {
        // Given
        var pokemon = new Pokemon();
        pokemon.setName("venusaur");
        pokemon.setHeight(7);
        pokemon.setWeight(50);

        // When
        var result = mapper.map(pokemon);

        // Then
        assertThat(result)
                .extracting(PokemonDto::getName, PokemonDto::getSize, PokemonDto::getWeight)
                .contains("venusaur", 7, 50);
    }
}
