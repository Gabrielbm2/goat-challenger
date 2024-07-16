package api.com.transmission.specializations.services.impl;

import api.com.transmission.specializations.dtos.CreateSpecializationRequestDto;
import api.com.transmission.specializations.dtos.SpecializationResponseDto;
import api.com.transmission.specializations.dtos.UpdateSpecializationDto;
import api.com.transmission.specializations.models.Specialization;
import api.com.transmission.specializations.repositories.SpecializationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SpecializationServiceImplTest {

    @Mock
    private SpecializationRepository repository;

    @InjectMocks
    private SpecializationServiceImpl service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void listSpecializations() {
        Specialization specialization = new Specialization();
        when(repository.findAll()).thenReturn(Arrays.asList(specialization));

        List<SpecializationResponseDto> result = service.list();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void getSpecializationById() throws ChangeSetPersister.NotFoundException {
        UUID id = UUID.randomUUID();
        Specialization specialization = new Specialization();
        when(repository.findById(id)).thenReturn(Optional.of(specialization));

        SpecializationResponseDto result = service.findById(id);

        assertNotNull(result);
        verify(repository, times(1)).findById(id);
    }

    @Test
    public void createSpecialization() {
        CreateSpecializationRequestDto dto = new CreateSpecializationRequestDto();
        Specialization specialization = new Specialization();
        when(repository.save(any(Specialization.class))).thenReturn(specialization);

        SpecializationResponseDto result = service.save(dto);

        assertNotNull(result);
        verify(repository, times(1)).save(any(Specialization.class));
    }

    @Test
    public void testSaveThrowsDataIntegrityViolationException() {
        CreateSpecializationRequestDto dto = new CreateSpecializationRequestDto();
        when(repository.save(any(Specialization.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> service.save(dto));
        verify(repository, times(1)).save(any(Specialization.class));
    }

    @Test
    public void updateSpecialization() {
        UUID id = UUID.randomUUID();
        UpdateSpecializationDto dto = new UpdateSpecializationDto();
        Specialization specialization = new Specialization();
        when(repository.findById(id)).thenReturn(Optional.of(specialization));
        when(repository.save(any(Specialization.class))).thenReturn(specialization);

        SpecializationResponseDto result = service.update(id, dto);

        assertNotNull(result);
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(any(Specialization.class));
    }

    @Test
    public void deleteSpecialization() throws ChangeSetPersister.NotFoundException {
        UUID id = UUID.randomUUID();
        Specialization specialization = new Specialization();
        when(repository.findById(id)).thenReturn(Optional.of(specialization));

        service.delete(id);

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).delete(specialization);
    }

    @Test
    public void testDeleteThrowsNotFoundException() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ChangeSetPersister.NotFoundException.class, () -> service.delete(id));
        verify(repository, times(1)).findById(id);
    }
}
