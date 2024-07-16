package api.com.transmission.specializations.services;

import java.util.List;
import java.util.UUID;
import api.com.transmission.specializations.dtos.CreateSpecializationRequestDto;
import api.com.transmission.specializations.dtos.SpecializationResponseDto;
import api.com.transmission.specializations.dtos.UpdateSpecializationDto;
import api.com.transmission.specializations.models.Specialization;
import org.springframework.data.crossstore.ChangeSetPersister;

public interface SpecializationService {

    public List<SpecializationResponseDto> list();

    public SpecializationResponseDto findById(UUID id) throws ChangeSetPersister.NotFoundException;

    public SpecializationResponseDto save(CreateSpecializationRequestDto createSpecializationRequestDto);

    public SpecializationResponseDto update(UUID id, UpdateSpecializationDto updateSpecializationDto);

    public void delete(UUID id) throws ChangeSetPersister.NotFoundException;

    public Specialization convert(CreateSpecializationRequestDto dto);
}