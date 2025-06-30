package com.sqs.pokesearch.service;

import com.sqs.pokesearch.model.User;
import com.sqs.pokesearch.repository.UserRepository;
import com.sqs.pokesearch.infrastructure.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtService.class);
        authService = new AuthService(userRepository, passwordEncoder, jwtService);
    }

    @Test
    void register_shouldCreateUserAndReturnJwt() {
        // Arrange
        RegisterRequest request = new RegisterRequest("testuser", "pass123", "Max", "Mustermann");
        when(passwordEncoder.encode("pass123")).thenReturn("hashedpass");
        when(jwtService.generateToken(any(User.class))).thenReturn("test.jwt.token");

        // Act
        AuthResponse response = authService.register(request);

        // Assert
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User savedUser = captor.getValue();

        assertEquals("testuser", savedUser.getUsername());
        assertEquals("hashedpass", savedUser.getPassword());
        assertEquals("Max", savedUser.getFirstName());
        assertEquals("Mustermann", savedUser.getLastName());
        assertEquals("test.jwt.token", response.token());
    }

    @Test
    void login_shouldReturnJwtIfCredentialsValid() {
        // Arrange
        User user = User.builder()
                .username("testuser")
                .password("hashedpass")
                .build();
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("pass123", "hashedpass")).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("valid.jwt.token");

        // Act
        AuthResponse response = authService.login(new LoginRequest("testuser", "pass123"));

        // Assert
        assertEquals("valid.jwt.token", response.token());
    }

    @Test
    void login_shouldThrowIfUserNotFound() {
        when(userRepository.findByUsername("notfound")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                authService.login(new LoginRequest("notfound", "123")));

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void login_shouldThrowIfPasswordInvalid() {
        User user = User.builder().username("test").password("hashedpass").build();
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "hashedpass")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                authService.login(new LoginRequest("test", "wrong")));

        assertEquals("Invalid password", ex.getMessage());
    }
}
