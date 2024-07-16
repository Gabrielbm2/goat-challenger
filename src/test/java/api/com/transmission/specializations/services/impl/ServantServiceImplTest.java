package api.com.transmission.specializations.services.impl;

import api.com.transmission.specializations.dtos.CreateServantRequestDto;
import api.com.transmission.specializations.dtos.ServantResponseDto;
import api.com.transmission.specializations.dtos.UpdateServantDto;
import api.com.transmission.specializations.models.Servant;
import api.com.transmission.specializations.repositories.ServantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServantServiceImplTest {

    @Mock
    private ServantRepository repository;

    @InjectMocks
    private ServantServiceImpl servantService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void ListServants() {
        List<Servant> servantList = new ArrayList<>();
        servantList.add(createMockServant(UUID.randomUUID(), "Gabriel"));
        servantList.add(createMockServant(UUID.randomUUID(), "Bernardo"));
        when(repository.findAll()).thenReturn(servantList);

        List<ServantResponseDto> response = servantService.list();

        assertEquals(2, response.size());
        assertEquals("Gabriel", response.get(0).getName());
        assertEquals("Bernardo", response.get(1).getName());
    }

    @Test
    public void getServantById() throws ChangeSetPersister.NotFoundException {
        UUID validId = UUID.randomUUID();
        Servant mockServant = createMockServant(validId, "gabriel");

        when(repository.findById(validId)).thenReturn(Optional.of(mockServant));

        ServantResponseDto response = servantService.findById(validId);

        verify(repository, times(1)).findById(validId);

        assertEquals("gabriel", response.getName());
    }

    @Test
    public void testFindByIdNotFound() {
        UUID invalidId = UUID.randomUUID();
        when(repository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ChangeSetPersister.NotFoundException.class, () -> servantService.findById(invalidId));

        verify(repository, times(1)).findById(invalidId);
    }

    @Test
    public void createServant() {
        CreateServantRequestDto createDto = new CreateServantRequestDto();
        createDto.setName("gabriel");
        createDto.setEmail("gabriel@example.com");

        Servant savedServant = createMockServant(UUID.randomUUID(), "gabriel");
        when(repository.save(any(Servant.class))).thenReturn(savedServant);

        ServantResponseDto response = servantService.save(createDto);

        assertEquals("gabriel", response.getName());
        assertEquals("gabriel@example.com", response.getEmail());
    }

    @Test
    public void createDataIntegrityViolation() {
        CreateServantRequestDto createDto = new CreateServantRequestDto();
        createDto.setName("gabriel");
        createDto.setEmail("gabriel@example.com");

        when(repository.save(any(Servant.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolationException.class, () -> servantService.save(createDto));
    }

    @Test
    public void UpdateServant() {
        UUID servantId = UUID.randomUUID();

        UpdateServantDto updateDto = new UpdateServantDto();
        updateDto.setName("bernardo");

        Servant existingServant = new Servant();
        existingServant.setId(servantId);
        existingServant.setName("gabriel");

        when(repository.findById(servantId)).thenReturn(Optional.of(existingServant));

        ServantResponseDto updatedServant = servantService.update(servantId, updateDto);

        assertEquals("bernardo", updatedServant.getName());
    }

    @Test
    public void deleteServant() {
        UUID servantId = UUID.randomUUID();
        Servant foundServant = createMockServant(servantId, "gabriel");
        when(repository.findById(servantId)).thenReturn(Optional.of(foundServant));

        assertDoesNotThrow(() -> servantService.delete(servantId));

        verify(repository, times(1)).delete(foundServant);
    }

    @Test
    public void deleteNotFound() {
        UUID invalidId = UUID.randomUUID();
        when(repository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ChangeSetPersister.NotFoundException.class, () -> servantService.delete(invalidId));

        verify(repository, never()).delete(any(Servant.class));
    }

    private Servant createMockServant(UUID id, String name) {
        Servant servant = new Servant();
        servant.setId(id);
        servant.setName(name);
        servant.setEmail(name.toLowerCase().replace(" ", ".") + "@example.com");
        return servant;
    }
}
