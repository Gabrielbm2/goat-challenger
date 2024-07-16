package api.com.transmission.specializations.controllers;

import api.com.transmission.specializations.dtos.AuthResponseDto;
import api.com.transmission.specializations.dtos.LoginRequestDto;
import api.com.transmission.specializations.dtos.RegisterRequestDto;
import api.com.transmission.specializations.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto body){
        return authService.login(body);
    }

    @PostMapping("/register")
    @Operation(summary = "Register Users JWT")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto body){
        return authService.register(body);
    }
}
