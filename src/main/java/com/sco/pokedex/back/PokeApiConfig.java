package com.sco.pokedex.back;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class PokeApiConfig implements WebFluxConfigurer {

    private final String baseUri;

    public PokeApiConfig(@Value("${pokeapi.base-uri}") String baseUri) {
        this.baseUri = baseUri;
    }

    @Bean
    public WebClient httpClient() {
        final var size = 16 * 1024 * 1024;
        final var strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
        return WebClient.builder()
                .baseUrl(baseUri)
                .exchangeStrategies(strategies)
                .build();
    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST")
                .maxAge(3600);
    }
}
