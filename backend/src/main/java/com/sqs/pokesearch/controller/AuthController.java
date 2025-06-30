package com.sqs.pokesearch.controller;

import com.sqs.pokesearch.service.AuthResponse;
import com.sqs.pokesearch.service.AuthService;
import com.sqs.pokesearch.service.LoginRequest;
import com.sqs.pokesearch.service.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
