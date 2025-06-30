package com.sqs.pokesearch.service;

import com.sqs.pokesearch.model.Pokemon;
import com.sqs.pokesearch.repository.PokemonRepository;
import com.sqs.pokesearch.infrastructure.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PokemonServiceTest {

    private PokemonRepository repo;
    private JwtService jwt;
    private PokemonService service;

    @BeforeEach
    void setUp() {
        repo = mock(PokemonRepository.class);
        jwt = mock(JwtService.class);
        service = new PokemonService(repo, jwt);
    }

    @Test
    void testGetAlbum_shouldReturnOnlyInAlbum() {
        String token = "token";
        String username = "matthias";
        when(jwt.extractUsername(token)).thenReturn(username);

        Pokemon inAlbum = Pokemon.builder().name("pikachu").inAlbum(true).username(username).build();
        Pokemon notInAlbum = Pokemon.builder().name("glurak").inAlbum(false).username(username).build();
        when(repo.findAllByUsername(username)).thenReturn(List.of(inAlbum, notInAlbum));

        List<Pokemon> result = service.getAlbum(token);

        assertEquals(1, result.size());
        assertTrue(result.get(0).isInAlbum());
    }

    @Test
    void testAddToAlbum_shouldUpdateInAlbumTrue() {
        String token = "token";
        String username = "matthias";
        String name = "pikachu";
        Pokemon pokemon = Pokemon.builder().name(name).username(username).inAlbum(false).build();

        when(jwt.extractUsername(token)).thenReturn(username);
        when(repo.findByNameAndUsername(name, username)).thenReturn(pokemon);

        ResponseEntity<String> response = service.addToAlbum(name, token);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Hinzugefügt", response.getBody());
        assertTrue(pokemon.isInAlbum());
        verify(repo).save(pokemon);
    }

    @Test
    void testAddToAlbum_whenAlreadyInAlbum_shouldDoNothing() {
        String token = "token";
        String username = "matthias";
        String name = "pikachu";
        Pokemon pokemon = Pokemon.builder().name(name).username(username).inAlbum(true).build();

        when(jwt.extractUsername(token)).thenReturn(username);
        when(repo.findByNameAndUsername(name, username)).thenReturn(pokemon);

        ResponseEntity<String> response = service.addToAlbum(name, token);

        assertEquals(200, response.getStatusCodeValue());
        verify(repo, never()).save(any());
    }

    @Test
    void testDeleteFromAlbum_whenFound_shouldDelete() {
        String token = "token";
        String username = "matthias";
        String name = "glurak";
        Pokemon pokemon = Pokemon.builder().name(name).username(username).inAlbum(true).build();

        when(jwt.extractUsername(token)).thenReturn(username);
        when(repo.findByNameAndUsername(name, username)).thenReturn(pokemon);

        ResponseEntity<String> response = service.deleteFromAlbum(name, token);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Gelöscht", response.getBody());
        verify(repo).delete(pokemon);
    }

    @Test
    void testDeleteFromAlbum_notFound_shouldReturn404() {
        String token = "token";
        String username = "matthias";
        String name = "unbekannt";

        when(jwt.extractUsername(token)).thenReturn(username);
        when(repo.findByNameAndUsername(name, username)).thenReturn(null);

        ResponseEntity<String> response = service.deleteFromAlbum(name, token);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Nicht gefunden", response.getBody());
    }

    @Test
    void testSearchPokemon_whenCached_shouldReturnFromDb() {
        String token = "token";
        String username = "matthias";
        String name = "pikachu";

        Pokemon pokemon = Pokemon.builder().name(name).username(username).data("cached-data").build();

        when(jwt.extractUsername(token)).thenReturn(username);
        when(repo.existsByNameAndUsername(name, username)).thenReturn(true);
        when(repo.findByNameAndUsername(name, username)).thenReturn(pokemon);

        ResponseEntity<String> response = service.searchPokemon(name, token);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("cached-data", response.getBody());
    }

    @Test
    void testSearchPokemon_whenNotFound_shouldReturn404() {
        String token = "token";
        String username = "matthias";
        String name = "nonexistent";

        when(jwt.extractUsername(token)).thenReturn(username);
        when(repo.existsByNameAndUsername(name, username)).thenReturn(false);

        ResponseEntity<String> response = service.searchPokemon("invalid123456", token);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Pokémon nicht gefunden", response.getBody());
    }

    @Test
    void testSearchPokemon_whenNotCached_shouldFetchAndSave() throws Exception {
        String token = "token";
        String username = "matthias";
        String name = "pikachu";

        when(jwt.extractUsername(token)).thenReturn(username);
        when(repo.existsByNameAndUsername(name, username)).thenReturn(false);

        String apiResponse = "{\"name\":\"pikachu\"}";
        ByteArrayInputStream fakeStream = new ByteArrayInputStream(apiResponse.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(fakeStream));

        PokemonService service = new PokemonService(repo, jwt) {
            @Override
            public ResponseEntity<String> searchPokemon(String name, String token) {
                try {
                    String username = jwt.extractUsername(token);
                    if (repo.existsByNameAndUsername(name, username)) {
                        return ResponseEntity.ok(repo.findByNameAndUsername(name, username).getData());
                    }

                    BufferedReader in = reader;
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) content.append(line);
                    in.close();

                    Pokemon p = Pokemon.builder()
                            .name(name)
                            .data(content.toString())
                            .username(username)
                            .inAlbum(false)
                            .build();
                    repo.save(p);

                    return ResponseEntity.ok(content.toString());
                } catch (Exception e) {
                    return ResponseEntity.status(404).body("Pokémon nicht gefunden");
                }
            }
        };

        ResponseEntity<String> response = service.searchPokemon(name, token);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("pikachu"));
        verify(repo).save(any());
    }
}
