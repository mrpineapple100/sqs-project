package com.sqs.pokesearch;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataSource dataSource;

    private static final String USERNAME = "matthias";
    private static final String SECRET = "sehrgeheimesjwtgeheimnismitsehrvielzeichen1234567890";

    private String generateTestToken() {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(USERNAME)
                .claim("username", USERNAME)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 3600000)) // 1h
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }

    @BeforeEach
    void insertTestUser() throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO users (username, first_name, last_name, password) " +
                    "VALUES ('matthias', 'Test', 'User', 'irrelevant') " +
                    "ON CONFLICT (username) DO NOTHING");
        }
    }

    @AfterEach
    void deleteTestUser() throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM users WHERE username = 'matthias'");
        }
    }

    @Test
    void integrationTest() throws Exception {
        String token = generateTestToken();
        String name = "pikachu";

        mockMvc.perform(get("/api/pokemon/search")
                        .param("name", name)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResponse().getStatus() < 400));

        mockMvc.perform(post("/api/pokemon//album/add")
                        .param("name", name)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResponse().getStatus() < 400));

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT * FROM pokemon WHERE name = 'pikachu' AND username = 'matthias'"
             )) {

            assertTrue(resultSet.next(), "Pokémon sollte in der DB existieren");
            String data = resultSet.getString("data");
            boolean inAlbum = resultSet.getBoolean("in_album");

            assertTrue(data.contains("pikachu"), "Datenfeld enthält 'pikachu'");
            assertTrue(inAlbum, "Pokémon sollte im Album sein");
        }
    }
}
