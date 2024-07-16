package api.com.transmission.specializations.controllers;

import api.com.transmission.specializations.dtos.AuthResponseDto;
import api.com.transmission.specializations.dtos.LoginRequestDto;
import api.com.transmission.specializations.dtos.RegisterRequestDto;
import api.com.transmission.specializations.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin_Success() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("gabriel@example.com", "password");
        AuthResponseDto authResponseDto = new AuthResponseDto("gabriel", "token");
        when(authService.login(any(LoginRequestDto.class))).thenReturn(ResponseEntity.ok(authResponseDto));

        ResponseEntity<AuthResponseDto> response = authController.login(loginRequestDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("gabriel", Objects.requireNonNull(response.getBody()).name());
        assertEquals("token", response.getBody().token());
    }

    @Test
    public void testRegister_Success() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto("password", "gabriel@example.com", "123456");

        AuthResponseDto authResponseDto = new AuthResponseDto("gabriel", "token");
        when(authService.register(any(RegisterRequestDto.class))).thenReturn(ResponseEntity.ok(authResponseDto));

        ResponseEntity<AuthResponseDto> response = authController.register(registerRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("gabriel", Objects.requireNonNull(response.getBody()).name());
        assertEquals("token", response.getBody().token());
    }
}
