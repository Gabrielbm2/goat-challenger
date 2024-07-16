package api.com.transmission.specializations.services;

import api.com.transmission.specializations.dtos.*;
import api.com.transmission.specializations.models.Servant;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.UUID;

public interface ServantService {

    public List<ServantResponseDto> list();

    public ServantResponseDto findById(UUID id) throws ChangeSetPersister.NotFoundException;

    public ServantResponseDto save(CreateServantRequestDto createServantRequestDTO);

    public ServantResponseDto update(UUID id, UpdateServantDto updateServantDTO);

    public void delete(UUID id) throws ChangeSetPersister.NotFoundException;

    public Servant convert(CreateServantRequestDto dto);
}