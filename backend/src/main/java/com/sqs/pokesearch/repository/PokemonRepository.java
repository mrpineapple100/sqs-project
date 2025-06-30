package com.sqs.pokesearch.repository;

import com.sqs.pokesearch.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    boolean existsByNameAndUsername(String name, String username);
    Pokemon findByNameAndUsername(String name, String username);
    List<Pokemon> findAllByUsername(String username);
}
