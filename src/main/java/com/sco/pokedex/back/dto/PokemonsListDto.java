package com.sco.pokedex.back.dto;

import java.util.List;

public record PokemonsListDto(Integer totalCount,
                              Integer filteredCount,
                              List<PokemonDto> results) {
}
