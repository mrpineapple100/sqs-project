package com.sqs.pokesearch.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {

    @Test
    void testBuilder() {
        Pokemon pokemon = Pokemon.builder()
                .id(1L)
                .name("Pikachu")
                .data("{\"type\":\"electric\"}")
                .username("ash")
                .inAlbum(true)
                .build();

        assertEquals(1L, pokemon.getId());
        assertEquals("Pikachu", pokemon.getName());
        assertEquals("{\"type\":\"electric\"}", pokemon.getData());
        assertEquals("ash", pokemon.getUsername());
        assertTrue(pokemon.isInAlbum());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(2L);
        pokemon.setName("Charmander");
        pokemon.setData("{\"type\":\"fire\"}");
        pokemon.setUsername("misty");
        pokemon.setInAlbum(false);

        assertEquals(2L, pokemon.getId());
        assertEquals("Charmander", pokemon.getName());
        assertEquals("{\"type\":\"fire\"}", pokemon.getData());
        assertEquals("misty", pokemon.getUsername());
        assertFalse(pokemon.isInAlbum());
    }

    @Test
    void testAllArgsConstructor() {
        Pokemon pokemon = new Pokemon(3L, "Bulbasaur", "{\"type\":\"grass\"}", "brock", true);

        assertEquals(3L, pokemon.getId());
        assertEquals("Bulbasaur", pokemon.getName());
        assertEquals("{\"type\":\"grass\"}", pokemon.getData());
        assertEquals("brock", pokemon.getUsername());
        assertTrue(pokemon.isInAlbum());
    }
}
