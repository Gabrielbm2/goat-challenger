package api.com.transmission.specializations.services.impl;

import api.com.transmission.specializations.dtos.CreateUserRequestDto;
import api.com.transmission.specializations.dtos.UpdateUserDto;
import api.com.transmission.specializations.dtos.UserResponseDto;
import api.com.transmission.specializations.models.User;
import api.com.transmission.specializations.repositories.UserRepository;
import api.com.transmission.specializations.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Cacheable(value = "user")
    public List<UserResponseDto> list() {
        List<User> userList = repository.findAll();
        List<UserResponseDto> responseList = new ArrayList<>();
        for(User user: userList) {
            responseList.add(new UserResponseDto(user));
        }
        return responseList;
    }

    @Override
    @Cacheable(value = "user", key = "#id")
    public UserResponseDto findById(UUID id) throws ChangeSetPersister.NotFoundException {
        User user = repository
                .findById(UUID.fromString(String.valueOf(id)))
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        return new UserResponseDto(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserResponseDto save(CreateUserRequestDto createUserRequestDTO) {
        try {
            User user = convert(createUserRequestDTO);
            user.setPassword(passwordEncoder.encode(createUserRequestDTO.getPassword()));
            user = repository.save(user);

            if (user == null) {
                throw new RuntimeException("Failed to save user");
            }

            return new UserResponseDto(user);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data integrity violation: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserResponseDto update(UUID id, UpdateUserDto updateUserDto) {
        try {
            User found = repository.findById(id)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);

            User user = convert(found, updateUserDto);

            repository.save(user);
            return new UserResponseDto(user);
        } catch (ChangeSetPersister.NotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(UUID id) throws ChangeSetPersister.NotFoundException {
        User user = repository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        try {
            repository.delete(user);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("Registro não pode ser excluído devido a registros relacionados.", ex);
        }
    }

    public User convert(User user, UpdateUserDto dto) {

        if(dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }

        if(dto.getName() != null) {
            user.setName(dto.getName());
        }

        return user;
    }

    public User convert(CreateUserRequestDto dto) {
        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
    }
}