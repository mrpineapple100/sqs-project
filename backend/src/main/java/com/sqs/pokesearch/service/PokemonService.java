package com.sqs.pokesearch.service;

import com.sqs.pokesearch.model.Pokemon;
import com.sqs.pokesearch.repository.PokemonRepository;
import com.sqs.pokesearch.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PokemonService {

    private final PokemonRepository repo;
    private final JwtService jwt;

    public ResponseEntity<String> searchPokemon(String name, String token) {
        String username = jwt.extractUsername(token);
        if (repo.existsByNameAndUsername(name, username)) {
            return ResponseEntity.ok(repo.findByNameAndUsername(name, username).getData());
        }

        try {
            URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + name);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
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

    public List<Pokemon> getAlbum(String token) {
        String username = jwt.extractUsername(token);
        return repo.findAllByUsername(username).stream()
                .filter(Pokemon::isInAlbum)
                .toList();
    }

    public ResponseEntity<String> addToAlbum(String name, String token) {
        String username = jwt.extractUsername(token);
        Pokemon p = repo.findByNameAndUsername(name, username);
        if (p != null && !p.isInAlbum()) {
            p.setInAlbum(true);
            repo.save(p);
        }
        return ResponseEntity.ok("Hinzugefügt");
    }

    public ResponseEntity<String> deleteFromAlbum(String name, String token) {
        String username = jwt.extractUsername(token);
        Pokemon p = repo.findByNameAndUsername(name, username);
        if (p != null && p.isInAlbum()) {
            repo.delete(p);
            return ResponseEntity.ok("Gelöscht");
        }
        return ResponseEntity.status(404).body("Nicht gefunden");
    }
}
