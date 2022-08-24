package com.sco.pokedex.back.filter;

import com.sco.pokedex.back.dto.PokemonDto;
import com.sco.pokedex.back.dto.filter.FilterDesc;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class FilterBuilder implements Function<List<FilterDesc>, Predicate<PokemonDto>> {

    private final FilterFactory factory;

    public FilterBuilder(FilterFactory factory) {
        this.factory = factory;
    }

    @Override
    public Predicate<PokemonDto> apply(List<FilterDesc> descs) {
        return descs.stream()
                .map(desc -> desc.getFilter(factory))
                .reduce(a -> true, Predicate::and);
    }
}
