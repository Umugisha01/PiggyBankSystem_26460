package com.piggybank.service;

import com.piggybank.dto.RegisterRequest;
import com.piggybank.model.*;
import com.piggybank.repository.*;
import com.piggybank.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private SavingsAccountRepository savingsAccountRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private JwtTokenProvider tokenProvider;
    
    @Mock
    private RefreshTokenService refreshTokenService;
    
    @Mock
    private EmailService emailService;
    
    @Mock
    private Authentication authentication;
    
    @InjectMocks
    private AuthService authService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void register_ShouldCreateUserSuccessfully() {
        // Given
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setFirstName("John");
        request.setLastName("Doe");
        
        User savedUser = new User();
        savedUser.setEmail("test@example.com");
        savedUser.setFirstName("John");
        savedUser.setRole(User.UserRole.USER);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refresh-token");
        
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(tokenProvider.generateToken(authentication)).thenReturn("jwt-token");
        when(refreshTokenService.createRefreshToken(any(User.class))).thenReturn(refreshToken);
        doNothing().when(emailService).sendWelcomeEmail(anyString(), anyString());
        
        // When & Then
        assertDoesNotThrow(() -> authService.register(request));
        verify(userRepository).save(any(User.class));
        verify(savingsAccountRepository).save(any());
        verify(emailService).sendWelcomeEmail(anyString(), anyString());
    }
    
    @Test
    void register_ShouldThrowException_WhenEmailExists() {
        // Given
        RegisterRequest request = new RegisterRequest();
        request.setEmail("existing@example.com");
        
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);
        
        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.register(request));
        assertEquals("Email already exists", exception.getMessage());
    }
}