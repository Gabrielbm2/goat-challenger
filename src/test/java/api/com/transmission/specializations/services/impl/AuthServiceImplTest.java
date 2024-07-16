package api.com.transmission.specializations.services.impl;

import api.com.transmission.specializations.dtos.AuthResponseDto;
import api.com.transmission.specializations.dtos.LoginRequestDto;
import api.com.transmission.specializations.dtos.RegisterRequestDto;
import api.com.transmission.specializations.infra.config.TokenConfig;
import api.com.transmission.specializations.models.User;
import api.com.transmission.specializations.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AuthServiceImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenConfig tokenConfig;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginSuccess() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setName("Test User");

        LoginRequestDto loginRequestDto = new LoginRequestDto("test@example.com", "password");

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(tokenConfig.generateToken(any(User.class))).thenReturn("token");

        ResponseEntity<AuthResponseDto> response = authService.login(loginRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Test User", response.getBody().name());
        assertEquals("token", response.getBody().token());
    }

    @Test
    public void testLoginFailure() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("test@example.com", "password");

        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.login(loginRequestDto);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testLoginInvalidPassword() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        LoginRequestDto loginRequestDto = new LoginRequestDto("test@example.com", "password");

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        ResponseEntity<AuthResponseDto> response = authService.login(loginRequestDto);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testRegisterSuccess() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("gabriel", "gabriel@example", "123456");

        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        when(tokenConfig.generateToken(any(User.class))).thenReturn("token");

        ResponseEntity<AuthResponseDto> response = authService.register(registerRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("gabriel", response.getBody().name());
        assertEquals("token", response.getBody().token());
    }

    @Test
    public void testRegisterFailure() {
        // Given
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("existinguser@example.com", "password", "Existing User");

        User existingUser = new User();
        existingUser.setEmail("existinguser@example.com");

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(existingUser));

        // When
        ResponseEntity<AuthResponseDto> response = authService.register(registerRequestDto);

        // Then
        assertEquals(400, response.getStatusCodeValue());
    }
}
