package com.sqs.pokesearch.controller;

import com.sqs.pokesearch.model.Pokemon;
import com.sqs.pokesearch.service.PokemonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PokemonControllerTest {

    private PokemonService service;
    private PokemonController controller;

    @BeforeEach
    void setup() {
        service = mock(PokemonService.class);
        controller = new PokemonController(service);
    }

    @Test
    void search_shouldDelegateToService_andReturnResult() {
        String name = "pikachu";
        String token = "Bearer xyz";
        ResponseEntity<String> expected = ResponseEntity.ok("data");

        when(service.searchPokemon(name, token)).thenReturn(expected);

        ResponseEntity<String> response = controller.search(name, token);

        assertEquals(expected, response);
        verify(service).searchPokemon(name, token);
    }

    @Test
    void getAlbum_shouldReturnListOfPokemon() {
        String token = "Bearer xyz";
        List<Pokemon> list = List.of(new Pokemon());
        when(service.getAlbum(token)).thenReturn(list);

        ResponseEntity<List<Pokemon>> response = controller.getAlbum(token);

        assertEquals(list, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(service).getAlbum(token);
    }

    @Test
    void add_shouldCallService_andReturnResponse() {
        String name = "pikachu";
        String token = "Bearer xyz";
        ResponseEntity<String> expected = ResponseEntity.ok("added");

        when(service.addToAlbum(name, token)).thenReturn(expected);

        ResponseEntity<String> response = controller.add(name, token);

        assertEquals(expected, response);
        verify(service).addToAlbum(name, token);
    }

    @Test
    void delete_shouldCallService_andReturnResponse() {
        String name = "pikachu";
        String token = "Bearer xyz";
        ResponseEntity<String> expected = ResponseEntity.ok("deleted");

        when(service.deleteFromAlbum(name, token)).thenReturn(expected);

        ResponseEntity<String> response = controller.delete(name, token);

        assertEquals(expected, response);
        verify(service).deleteFromAlbum(name, token);
    }
}
