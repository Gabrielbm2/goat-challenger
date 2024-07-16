package api.com.transmission.specializations.services.impl;

import api.com.transmission.specializations.dtos.AuthResponseDto;
import api.com.transmission.specializations.dtos.LoginRequestDto;
import api.com.transmission.specializations.dtos.RegisterRequestDto;
import api.com.transmission.specializations.infra.config.TokenConfig;
import api.com.transmission.specializations.models.User;
import api.com.transmission.specializations.repositories.UserRepository;
import api.com.transmission.specializations.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final TokenConfig tokenConfig;

    @Override
    public ResponseEntity<AuthResponseDto> login(LoginRequestDto body) {
        User user = repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = tokenConfig.generateToken(user);
            return ResponseEntity.ok(new AuthResponseDto(user.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<AuthResponseDto> register(RegisterRequestDto body) {
        Optional<User> user = repository.findByEmail(body.getEmail());

        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.getPassword()));
            newUser.setEmail(body.getEmail());
            newUser.setName(body.getName());
            repository.save(newUser);

            String token = tokenConfig.generateToken(newUser);
            return ResponseEntity.ok(new AuthResponseDto(newUser.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
