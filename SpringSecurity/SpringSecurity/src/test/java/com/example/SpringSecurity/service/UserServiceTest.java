package com.example.SpringSecurity.service;

import com.example.SpringSecurity.model.Role;
import com.example.SpringSecurity.model.User;
import com.example.SpringSecurity.repository.RoleRepository;
import com.example.SpringSecurity.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_shouldRegisterUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        Role role = new Role();
        role.setName("ROLE_USER");

        when(roleRepository.findByName("ROLE_USER")).thenReturn(role);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.register(user);
        assertNotNull(registeredUser);
        assertEquals("username", registeredUser.getUsername());
    }
    @Test
    void registerAdmin_shouldRegisterAdmin() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        Role role = new Role();
        role.setName("ROLE_ADMIN");

        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(role);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerAdmin(user);
        assertNotNull(registeredUser);
        assertEquals("admin", registeredUser.getUsername());

    }

    @Test
    void verify_Success() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(user.getUsername())).thenReturn("valid_token");

        String token = userService.verify(user);
        assertNotNull(token);
    }

    @Test
    void verify_Fail() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);        when(authentication.isAuthenticated()).thenReturn(false);

        String result = userService.verify(user);
        assertEquals("Failed", result);
    }
}