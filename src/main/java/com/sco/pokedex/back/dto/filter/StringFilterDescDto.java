package com.sco.pokedex.back.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sco.pokedex.back.dto.PokemonDto;
import com.sco.pokedex.back.filter.FilterFactory;

import java.util.function.Predicate;

public class StringFilterDescDto implements FilterDesc {

    @JsonProperty("type")
    public final FilterTypeEnum type = FilterTypeEnum.STRING;

    @JsonProperty("propertyName")
    private String propertyName;

    @JsonProperty("value")
    private String value;

    public StringFilterDescDto() {}

    public StringFilterDescDto(String propertyName, String value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    @Override
    public FilterTypeEnum getType() {
        return type;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Predicate<PokemonDto> getFilter(FilterFactory factory) {
        return factory.getFilter(this);
    }
}
