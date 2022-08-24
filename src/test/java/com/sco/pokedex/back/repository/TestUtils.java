package com.sco.pokedex.back.repository;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public final class TestUtils {

    private TestUtils() {}

    public static void initStubs(WireMockServer server) {
        server.stubFor(get(urlEqualTo("/pokemon?limit=0&offset=0"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"count\": 3\n" +
                                "}\n")));

        server.stubFor(get(urlEqualTo("/pokemon?limit=3&offset=0"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"count\": 3,\n" +
                                "  \"next\": null,\n" +
                                "  \"previous\": null,\n" +
                                "  \"results\": [\n" +
                                "    {\n" +
                                "      \"name\": \"bulbasaur\",\n" +
                                "      \"url\": \"https://pokeapi.co/api/v2/pokemon/1/\"\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"name\": \"ivysaur\",\n" +
                                "      \"url\": \"https://pokeapi.co/api/v2/pokemon/2/\"\n" +
                                "    },\n" +
                                "    {\n" +
                                "      \"name\": \"venusaur\",\n" +
                                "      \"url\": \"https://pokeapi.co/api/v2/pokemon/3/\"\n" +
                                "    }\n" +
                                "  ]\n" +
                                "}\n")));

        server.stubFor(get(urlEqualTo("/pokemon/bulbasaur"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"name\": \"bulbasaur\",\n" +
                                "  \"weight\": 150,\n" +
                                "  \"height\": 10\n" +
                                "}")));

        server.stubFor(get(urlEqualTo("/pokemon/ivysaur"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"name\": \"ivysaur\",\n" +
                                "  \"weight\": 100,\n" +
                                "  \"height\": 5\n" +
                                "}")));

        server.stubFor(get(urlEqualTo("/pokemon/venusaur"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"name\": \"venusaur\",\n" +
                                "  \"weight\": 50,\n" +
                                "  \"height\": 7\n" +
                                "}")));
    }
}
