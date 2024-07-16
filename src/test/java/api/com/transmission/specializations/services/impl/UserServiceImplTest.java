package api.com.transmission.specializations.services.impl;

import api.com.transmission.specializations.dtos.CreateUserRequestDto;
import api.com.transmission.specializations.dtos.UpdateUserDto;
import api.com.transmission.specializations.dtos.UserResponseDto;
import api.com.transmission.specializations.models.User;
import api.com.transmission.specializations.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void list() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(UUID.randomUUID(), "gabriel 1", "gabriel1@example.com", "hashedPassword"));
        userList.add(new User(UUID.randomUUID(), "gabriel 2", "gabriel2@example.com", "hashedPassword"));

        when(userRepository.findAll()).thenReturn(userList);

        List<UserResponseDto> result = userService.list();

        assertEquals(userList.size(), result.size());
        for (int i = 0; i < userList.size(); i++) {
            assertEquals(userList.get(i).getId(), result.get(i).getId());
            assertEquals(userList.get(i).getName(), result.get(i).getName());
            assertEquals(userList.get(i).getEmail(), result.get(i).getEmail());
        }

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findById() throws ChangeSetPersister.NotFoundException {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "gabriel", "gabriel@example.com", "hashedPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserResponseDto result = userService.findById(userId);

        assertEquals(userId, result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findById(userId);
    }


    @Test
    void save() {
        UUID userId = UUID.randomUUID();
        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto("gabriel", "gabriel@exemplo", "123456");
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("hashedPassword");
        User savedUser = new User(userId, createUserRequestDto.getName(), createUserRequestDto.getEmail(), "hashedPassword");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        UserResponseDto result = userService.save(createUserRequestDto);

        assertEquals(userId, result.getId());
        assertEquals(createUserRequestDto.getName(), result.getName());
        assertEquals(createUserRequestDto.getEmail(), result.getEmail());

        verify(passwordEncoder, times(1)).encode(any(CharSequence.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void update() {
        UUID userId = UUID.randomUUID();
        String newName = "Novo Nome";
        String newEmail = "novoemail@example.com";
        UpdateUserDto updateUserDto = new UpdateUserDto(newName, newEmail);

        User existingUser = new User(userId, "Nome Antigo", "emailantigo@example.com", "senhaantiga");
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserResponseDto result = userService.update(userId, updateUserDto);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(newName, result.getName());
        assertEquals(newEmail, result.getEmail());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void delete() throws ChangeSetPersister.NotFoundException {
        UUID userId = UUID.randomUUID();
        String userIdString = userId.toString();
        User user = new User(userIdString, "gabriel", "gabriel@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        userService.delete(userId);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void updateUserNotFound() {
        UUID userId = UUID.randomUUID();
        UpdateUserDto updateUserDto = new UpdateUserDto("bernardo", "bernardo@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.update(userId, updateUserDto));

        Throwable cause = exception.getCause();
        assertNotNull(cause);
        assertInstanceOf(ChangeSetPersister.NotFoundException.class, cause);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any());
    }

    @Test
    void saveFieldsRequired() {
        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto("gabriel", "gabriel@exemplo.com", "123456");

        when(userRepository.save(any(User.class))).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            userService.save(createUserRequestDto);
        });
    }

    @Test
    void saveNullUserReturned() {
        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto("gabriel", "gabriel@exemplo.com", "123456");

        when(userRepository.save(any(User.class))).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            userService.save(createUserRequestDto);
        });
    }
}