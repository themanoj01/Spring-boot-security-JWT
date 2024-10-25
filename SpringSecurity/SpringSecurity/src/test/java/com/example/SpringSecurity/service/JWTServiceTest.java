package com.example.SpringSecurity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JWTServiceTest {
    private JWTService jwtService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JWTService();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateToken() {
        String username = "test";
        String token = jwtService.generateToken(username);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractUsername() {
        String username = "test";
        String token = jwtService.generateToken(username);

        String extractedUsername = jwtService.extractUsername(token);
        assertNotNull(extractedUsername);
        assertEquals(username, extractedUsername);
    }

    @Test
    void validateToken() {
        String username = "test";
        String token = jwtService.generateToken(username);
        when(userDetails.getUsername()).thenReturn(username);
        boolean isValid = jwtService.validateToken(token, userDetails);
        assertTrue(isValid);
    }


}