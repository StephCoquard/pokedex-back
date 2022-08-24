package com.sco.pokedex.back.repository.dto;

import java.util.List;

public class PokemonsListApiDto {

    private Integer count;
    private String next;
    private String previous;
    private List<PokemonLightApiDto> results;

    public PokemonsListApiDto() {
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return this.next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return this.previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<PokemonLightApiDto> getResults() {
        return this.results;
    }

    public void setResults(List<PokemonLightApiDto> results) {
        this.results = results;
    }
}
