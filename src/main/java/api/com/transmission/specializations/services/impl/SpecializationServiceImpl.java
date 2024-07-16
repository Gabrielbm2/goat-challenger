package api.com.transmission.specializations.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import api.com.transmission.specializations.dtos.CreateSpecializationRequestDto;
import api.com.transmission.specializations.dtos.SpecializationResponseDto;
import api.com.transmission.specializations.dtos.UpdateSpecializationDto;
import api.com.transmission.specializations.models.Specialization;
import api.com.transmission.specializations.repositories.SpecializationRepository;
import api.com.transmission.specializations.services.SpecializationService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpecializationServiceImpl implements SpecializationService {

    @Autowired
    private SpecializationRepository repository;

    @Override
    @Cacheable(value = "specialization")
    public List<SpecializationResponseDto> list() {
        List<Specialization> specializationList = repository.findAll();
        List<SpecializationResponseDto> responseList = new ArrayList<>();
        for(Specialization specialization: specializationList) {
            responseList.add(new SpecializationResponseDto(specialization));
        }
        return responseList;
    }

    @Override
    @Cacheable(value = "specialization", key = "#id")
    public SpecializationResponseDto findById(UUID id) throws ChangeSetPersister.NotFoundException {
        Specialization specialization = repository
                .findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        return new SpecializationResponseDto(specialization);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public SpecializationResponseDto save(CreateSpecializationRequestDto createSpecializationRequestDto) {
        try {
            Specialization specialization = repository.save(convert(createSpecializationRequestDto));
            return new SpecializationResponseDto(specialization);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(ex.getMessage());
        } catch (ConstraintViolationException ex) {
            throw new Error();
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public SpecializationResponseDto update(UUID id, UpdateSpecializationDto updateSpecializationDto) {
        try {
            Specialization found = repository.findById(id)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);
            Specialization specialization = convert(found, updateSpecializationDto);

            repository.save(specialization);
            return new SpecializationResponseDto(specialization);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(ex.getMessage());
        } catch (Exception ex) {
            throw new Error();
        }
    }

    @Override
    public void delete(UUID id) throws ChangeSetPersister.NotFoundException {
        Specialization paymentMethod = repository
                .findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        try {
            repository.delete(paymentMethod);
        } catch (DataIntegrityViolationException ex) {
            throw new Error("Registro não pode ser excluído, pois o mesmo tem registros relacionados.");
        }
    }

    public Specialization convert(Specialization specialization, UpdateSpecializationDto dto) {

        if(dto.getArea() != null) {
            specialization.setArea(dto.getArea());
        }

        if(dto.getType() != null) {
            specialization.setType(dto.getType());
        }

        if(dto.getTotalHours() != null) {
            specialization.setTotalHours(dto.getTotalHours());
        }

        if(dto.getTotalCoast() != null) {
            specialization.setTotalCoast(dto.getTotalCoast());
        }

        return specialization;
    }

    public Specialization convert(CreateSpecializationRequestDto dto) {
        Specialization specialization = new Specialization();

        specialization.setType(dto.getType());
        specialization.setArea(dto.getArea());
        specialization.setTotalCoast(dto.getTotalCoast());
        specialization.setTotalHours(dto.getTotalHours());

        return specialization;
    }
}