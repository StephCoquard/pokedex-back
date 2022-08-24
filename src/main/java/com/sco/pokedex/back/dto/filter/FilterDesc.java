package com.sco.pokedex.back.dto.filter;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sco.pokedex.back.dto.PokemonDto;
import com.sco.pokedex.back.filter.FilterFactory;

import java.util.function.Predicate;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = IntegerFilterDescDto.class, name = "INTEGER"),
        @JsonSubTypes.Type(value = StringFilterDescDto.class, name = "STRING")
})
public interface FilterDesc {

    FilterTypeEnum getType();

    String getPropertyName();

    Predicate<PokemonDto> getFilter(FilterFactory factory);
}
