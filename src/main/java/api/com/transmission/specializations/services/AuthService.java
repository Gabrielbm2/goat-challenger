package api.com.transmission.specializations.services;

import api.com.transmission.specializations.dtos.AuthResponseDto;
import api.com.transmission.specializations.dtos.LoginRequestDto;
import api.com.transmission.specializations.dtos.RegisterRequestDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<AuthResponseDto> login(LoginRequestDto body);
    ResponseEntity<AuthResponseDto> register(RegisterRequestDto body);
}