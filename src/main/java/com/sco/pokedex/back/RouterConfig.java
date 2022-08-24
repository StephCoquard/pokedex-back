package com.sco.pokedex.back;

import com.sco.pokedex.back.dto.PokemonsListDto;
import com.sco.pokedex.back.dto.filter.FilterDesc;
import com.sco.pokedex.back.dto.filter.FiltersDto;
import com.sco.pokedex.back.dto.PokemonDto;
import com.sco.pokedex.back.repository.PokemonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    private static final String PAGE_SIZE_PARAM_NAME = "pageSize";
    private static final String PAGE_INDEX_PARAM_NAME = "pageIndex";

    private final PokemonRepository pokemonRepository;

    private final Function<List<FilterDesc>, Predicate<PokemonDto>> filterBuilder;

    public RouterConfig(PokemonRepository pokemonRepository,
                        Function<List<FilterDesc>, Predicate<PokemonDto>> filterBuilder) {
        this.pokemonRepository = pokemonRepository;
        this.filterBuilder = filterBuilder;
    }

    @Bean
    RouterFunction<ServerResponse> getEmployeeByIdRoute() {
        return route(GET("/pokemons"),
                req -> {
                    var pageSize = req.queryParam(PAGE_SIZE_PARAM_NAME).map(Integer::parseInt).orElse(20);
                    var pageIndex = req.queryParam(PAGE_INDEX_PARAM_NAME).map(Integer::parseInt).orElse(0);
                    return ServerResponse
                            .ok()
                            .body(pokemonRepository.getPokemons(pageSize, pageIndex), PokemonsListDto.class);
                })
                .andRoute(POST("/pokemons/search"),
                        req -> {
                            var pageSize = req.queryParam(PAGE_SIZE_PARAM_NAME).map(Integer::parseInt).orElse(20);
                            var pageIndex = req.queryParam(PAGE_INDEX_PARAM_NAME).map(Integer::parseInt).orElse(0);
                            return req.bodyToMono(FiltersDto.class)
                                    .map(filters -> filterBuilder.apply(filters.getFilters()))
                                    .map(filterChain -> pokemonRepository
                                            .getFilteredPokemons(filterChain, pageSize, pageIndex))
                                    .flatMap(body -> ServerResponse.ok()
                                            .body(body, PokemonsListDto.class));
                        });
    }
}
