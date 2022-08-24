package com.sco.pokedex.back.mapper;

import com.sco.pokedex.back.dto.PokemonDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import skaro.pokeapi.resource.pokemon.Pokemon;

@Mapper
public interface PokemonMapper {

    @Mapping(source = "height", target = "size")
    PokemonDto map(Pokemon pokemon);
}
