package api.com.transmission.specializations.services;

import api.com.transmission.specializations.dtos.CreateUserRequestDto;
import api.com.transmission.specializations.dtos.UpdateUserDto;
import api.com.transmission.specializations.dtos.UserResponseDto;
import api.com.transmission.specializations.models.User;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.UUID;

public interface UserService {

    public List<UserResponseDto> list();

    public UserResponseDto findById(UUID id) throws ChangeSetPersister.NotFoundException;

    UserResponseDto save(CreateUserRequestDto createUserRequestDTO);

    public UserResponseDto update(UUID id, UpdateUserDto updateUserDTO);

    public void delete(UUID id) throws ChangeSetPersister.NotFoundException;

    public User convert(CreateUserRequestDto dto);

}