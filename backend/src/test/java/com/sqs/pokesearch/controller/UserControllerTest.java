package com.sqs.pokesearch.controller;

import com.sqs.pokesearch.model.User;
import com.sqs.pokesearch.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserController userController = new UserController(userRepository);

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        // Arrange
        User user1 = User.builder()
                .id(1L)
                .username("ash")
                .password("secret")
                .firstName("Ash")
                .lastName("Ketchum")
                .build();

        when(userRepository.findAll()).thenReturn(List.of(user1));

        // Act
        ResponseEntity<List<User>> response = userController.getAllUsers();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("ash", response.getBody().get(0).getUsername());
    }

    @Test
    void me_shouldReturnAuthenticatedUsername() {
        // Arrange
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("ash");

        // Act
        String result = userController.me(auth);

        // Assert
        assertEquals("Angemeldet als: ash", result);
    }
}
