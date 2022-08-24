package com.sco.pokedex.back.dto;

public class PokemonDto {

    private final String name;

    private final Integer size;

    private final Integer weight;

    public PokemonDto(String name, Integer size, Integer weight) {
        this.name = name;
        this.size = size;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getWeight() {
        return weight;
    }
}
