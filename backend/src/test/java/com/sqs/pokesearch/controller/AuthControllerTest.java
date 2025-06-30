package com.sqs.pokesearch.controller;

import com.sqs.pokesearch.service.AuthResponse;
import com.sqs.pokesearch.service.AuthService;
import com.sqs.pokesearch.service.LoginRequest;
import com.sqs.pokesearch.service.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {

    private AuthService authService;
    private AuthController authController;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        authController = new AuthController(authService);
    }

    @Test
    void register_returnsAuthResponse() {
        // Arrange
        RegisterRequest request = new RegisterRequest("Ash", "Ketchum", "ash@poke.com", "pikachu123");
        AuthResponse expected = new AuthResponse("token123");

        when(authService.register(request)).thenReturn(expected);

        // Act
        AuthResponse actual = authController.register(request);

        // Assert
        assertEquals(expected.token(), actual.token());
    }

    @Test
    void login_returnsAuthResponse() {
        // Arrange
        LoginRequest request = new LoginRequest("ash@poke.com", "pikachu123");
        AuthResponse expected = new AuthResponse("logintoken456");

        when(authService.login(request)).thenReturn(expected);

        // Act
        AuthResponse actual = authController.login(request);

        // Assert
        assertEquals(expected.token(), actual.token());
    }
}
