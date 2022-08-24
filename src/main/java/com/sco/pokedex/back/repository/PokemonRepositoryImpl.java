package com.sco.pokedex.back.repository;

import com.sco.pokedex.back.dto.PokemonsListDto;
import com.sco.pokedex.back.repository.dto.PokemonLightApiDto;
import com.sco.pokedex.back.repository.dto.PokemonsListApiDto;
import com.sco.pokedex.back.dto.PokemonDto;
import com.sco.pokedex.back.mapper.PokemonMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import skaro.pokeapi.resource.pokemon.Pokemon;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class PokemonRepositoryImpl implements PokemonRepository {

    private final WebClient client;

    private final String pokemonsUri;

    private final PokemonMapper pokemonMapper;

    PokemonRepositoryImpl(WebClient client,
                          @Value("${pokeapi.pokemons-uri}") String pokemonsUri,
                          PokemonMapper pokemonMapper) {
        this.client = client;
        this.pokemonsUri = pokemonsUri;
        this.pokemonMapper = pokemonMapper;
    }

    @Override
    public Mono<PokemonsListDto> getAllPokemons() {
        return count()
                .flatMap(count -> getPokemons(count, 0))
                .cache();
    }

    @Override
    public Mono<PokemonsListDto> getFilteredPokemons(Predicate<PokemonDto> filterChain,
                                                     int pageSize,
                                                     int pageIndex) {
        return getAllPokemons()
                .map(pokemonsListDto -> {
                    var filteredCount = (int) pokemonsListDto.results().stream()
                            .filter(filterChain)
                            .count();

                    return pokemonsListDto.results().stream()
                            .filter(filterChain)
                            .skip((long) pageIndex * pageSize)
                            .limit(pageSize)
                            .collect(Collectors.collectingAndThen(
                                    Collectors.toList(),
                                    result -> new PokemonsListDto(pokemonsListDto.totalCount(), filteredCount, result)));
                });
    }

    @Override
    public Mono<PokemonsListDto> getPokemons(int pageSize,
                                             int pageIndex) {
        var offset = pageIndex * pageSize;

        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(pokemonsUri)
                        .queryParam("limit", pageSize)
                        .queryParam("offset", offset)
                        .build())
                .exchangeToMono(response -> response.bodyToMono(PokemonsListApiDto.class)
                        .flatMap(apiDto -> {
                            var count = apiDto.getCount();
                            var monoDtos = apiDto.getResults().stream()
                                    .map(PokemonLightApiDto::getName)
                                    .map(this::getPokemon)
                                    .collect(Collectors.toList());
                            return Mono.zip(monoDtos, array -> Arrays.stream(array)
                                            .map(PokemonDto.class::cast)
                                            .collect(Collectors.toList()))
                                    .map(dtos -> new PokemonsListDto(count, 0, dtos));
                        }));
    }

    @Override
    public Mono<Integer> count() {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(pokemonsUri)
                        .queryParam("limit", 0)
                        .queryParam("offset", 0)
                        .build())
                .exchangeToMono(response -> response.bodyToMono(PokemonsListApiDto.class)
                        .map(PokemonsListApiDto::getCount));

    }

    @Override
    public Mono<PokemonDto> getPokemon(String name) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(pokemonsUri + "/{name}")
                        .build(name))
                .exchangeToMono(response -> response.bodyToMono(Pokemon.class)
                        .map(pokemonMapper::map));
    }
}
