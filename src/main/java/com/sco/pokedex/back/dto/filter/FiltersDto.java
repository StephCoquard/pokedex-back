package com.sco.pokedex.back.dto.filter;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FiltersDto {

    @JsonProperty("filters")
    private List<FilterDesc> filters;

    public List<FilterDesc> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterDesc> filters) {
        this.filters = filters;
    }
}
