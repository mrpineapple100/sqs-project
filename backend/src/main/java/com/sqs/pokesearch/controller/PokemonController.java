package com.sqs.pokesearch.controller;

import com.sqs.pokesearch.model.Pokemon;
import com.sqs.pokesearch.service.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pokemon")
@RequiredArgsConstructor
public class PokemonController {

    private final PokemonService service;

    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestParam String name,
                                         @RequestHeader("Authorization") String token) {
        return service.searchPokemon(name.toLowerCase(), token);
    }

    @GetMapping("/album")
    public ResponseEntity<List<Pokemon>> getAlbum(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(service.getAlbum(token));
    }

    @PostMapping("/album/add")
    public ResponseEntity<String> add(@RequestParam String name,
                                      @RequestHeader("Authorization") String token) {
        return service.addToAlbum(name.toLowerCase(), token);
    }

    @DeleteMapping("/album/delete")
    public ResponseEntity<String> delete(@RequestParam String name,
                                         @RequestHeader("Authorization") String token) {
        return service.deleteFromAlbum(name.toLowerCase(), token);
    }
}
