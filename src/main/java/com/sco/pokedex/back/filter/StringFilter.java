package com.sco.pokedex.back.filter;

import com.sco.pokedex.back.dto.PokemonDto;

import java.util.function.Function;
import java.util.function.Predicate;

public class StringFilter implements Predicate<PokemonDto> {

    private final Function<PokemonDto, String> fieldValueGetter;

    private final String filterValue;

    public StringFilter(Function<PokemonDto, String> fieldValueGetter,
                        String filterValue) {
        this.fieldValueGetter = fieldValueGetter;
        this.filterValue = filterValue;
    }

    @Override
    public boolean test(PokemonDto pokemonDto) {
        return fieldValueGetter.apply(pokemonDto).contains(filterValue);
    }
}
