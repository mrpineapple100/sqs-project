package com.sqs.pokesearch.controller;

import com.sqs.pokesearch.repository.UserRepository;
import com.sqs.pokesearch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") // ✅ Nur Admin darf alle sehen
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/me")
    public String me(Authentication authentication) {
        return "Angemeldet als: " + authentication.getName();
    }
}
