package com.example.SpringSecurity.service;

import com.example.SpringSecurity.model.User;
import com.example.SpringSecurity.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MyUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private MyUserDetailsService myUserDetailsService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getUserByUsername_UserFound() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setRoles(new HashSet<>());

        when(userRepository.findByUsername("username")).thenReturn(user);
        UserDetails userDetails = myUserDetailsService.loadUserByUsername("username");
        assertNotNull(userDetails);
        assertEquals("username", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
    }
    @Test
    void getUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("username")).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> myUserDetailsService.loadUserByUsername("username"));
    }


}