package com.sco.pokedex.back.filter;

import com.sco.pokedex.back.dto.PokemonDto;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class IntegerFilter implements Predicate<PokemonDto> {

    private final Function<PokemonDto, Integer> fieldValueGetter;

    private final BiPredicate<Integer, Integer> filterFunction;

    private final Integer filterValue;

    public IntegerFilter(Function<PokemonDto, Integer> fieldValueGetter,
                         BiPredicate<Integer, Integer> filterFunction,
                         Integer filterValue) {
        this.fieldValueGetter = fieldValueGetter;
        this.filterFunction = filterFunction;
        this.filterValue = filterValue;
    }

    @Override
    public boolean test(PokemonDto pokemonDto) {
        return filterFunction.test(fieldValueGetter.apply(pokemonDto), filterValue);
    }
}
