package com.sco.pokedex.back.repository;

import com.sco.pokedex.back.dto.PokemonDto;
import com.sco.pokedex.back.dto.PokemonsListDto;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

public interface PokemonRepository {

    Mono<PokemonsListDto> getAllPokemons();

    Mono<PokemonsListDto> getFilteredPokemons(Predicate<PokemonDto> filterChain, int pageSize, int pageIndex);

    Mono<PokemonsListDto> getPokemons(int pageSize, int pageIndex);

    Mono<Integer> count();

    Mono<PokemonDto> getPokemon(String name);

}
