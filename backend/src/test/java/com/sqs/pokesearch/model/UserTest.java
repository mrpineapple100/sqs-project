package com.sqs.pokesearch.model;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testBuilder() {
        User user = User.builder()
                .id(1L)
                .username("ash")
                .password("pikapass")
                .firstName("Ash")
                .lastName("Ketchum")
                .build();

        assertEquals(1L, user.getId());
        assertEquals("ash", user.getUsername());
        assertEquals("pikapass", user.getPassword());
        assertEquals("Ash", user.getFirstName());
        assertEquals("Ketchum", user.getLastName());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        User user = new User();
        user.setId(2L);
        user.setUsername("misty");
        user.setPassword("waterpass");
        user.setFirstName("Misty");
        user.setLastName("Cerulean");

        assertEquals(2L, user.getId());
        assertEquals("misty", user.getUsername());
        assertEquals("waterpass", user.getPassword());
        assertEquals("Misty", user.getFirstName());
        assertEquals("Cerulean", user.getLastName());
    }

    @Test
    void testAllArgsConstructor() {
        User user = new User(3L, "brock", "rockpass", "Brock", "Pewter");

        assertEquals(3L, user.getId());
        assertEquals("brock", user.getUsername());
        assertEquals("rockpass", user.getPassword());
        assertEquals("Brock", user.getFirstName());
        assertEquals("Pewter", user.getLastName());
    }

    @Test
    void testUserDetailsMethods() {
        User user = User.builder()
                .username("gary")
                .password("rivalpass")
                .build();

        assertTrue(user.getAuthorities().isEmpty());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
        assertEquals("gary", user.getUsername());
    }
}
