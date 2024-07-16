package api.com.transmission.specializations.controllers;

import api.com.transmission.specializations.dtos.CreateUserRequestDto;
import api.com.transmission.specializations.dtos.UpdateUserDto;
import api.com.transmission.specializations.dtos.UserResponseDto;
import api.com.transmission.specializations.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Endpoints for Users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    @Operation(summary = "Return Users", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.list();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User by ID", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        try {
            UserResponseDto user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Create User", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserResponseDto> createUser(@RequestBody CreateUserRequestDto createUserRequestDTO) {
        UserResponseDto newUser = userService.save(createUserRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update User", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable UUID id, @RequestBody UpdateUserDto updateUserDTO) {
        try {
            UserResponseDto updatedUser = userService.update(id, updateUserDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
