package api.com.transmission.specializations.services.impl;

import api.com.transmission.specializations.dtos.*;
import api.com.transmission.specializations.models.Servant;
import api.com.transmission.specializations.repositories.ServantRepository;
import api.com.transmission.specializations.services.ServantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class ServantServiceImpl implements ServantService {

    @Autowired
    private ServantRepository repository;

    @Override
    @Cacheable(value = "servants")
    public List<ServantResponseDto> list() {
        List<Servant> requestTaskList = repository.findAll();
        List<ServantResponseDto> responseList = new ArrayList<>();
        for(Servant requestTask: requestTaskList) {
            responseList.add(new ServantResponseDto(requestTask));
        }
        return responseList;
    }

    @Override
    @Cacheable(value = "servant", key = "#id")
    public ServantResponseDto findById(UUID id) throws ChangeSetPersister.NotFoundException {
        Servant servant = repository
                .findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        return new ServantResponseDto(servant);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ServantResponseDto save(CreateServantRequestDto createServantRequestDto) {
        Servant servant = convert(createServantRequestDto);
        Servant savedServant = repository.save(servant);
        return new ServantResponseDto(savedServant);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ServantResponseDto update(UUID id, UpdateServantDto updateServantDto) {
        try {
            Servant found = repository.findById(id)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);
            Servant servant = convert(found, updateServantDto);

            repository.save(servant);
            return new ServantResponseDto(servant);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(ex.getMessage());
        } catch (Exception ex) {
            throw new Error();
        }
    }

    @Override
    public void delete(UUID id) throws ChangeSetPersister.NotFoundException {
        Servant servant = repository
                .findById(UUID.fromString(String.valueOf(id)))
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        try {
            repository.delete(servant);
        } catch (DataIntegrityViolationException ex) {
            throw new Error("Registro não pode ser excluído, pois o mesmo tem registros relacionados.");
        }
    }

    public Servant convert(Servant servant, UpdateServantDto dto) {

        if(dto.getEmail() != null) {
            servant.setEmail(dto.getEmail());
        }

        if(dto.getName() != null) {
            servant.setName(dto.getName());
        }

        if(dto.getCpf() != null) {
            servant.setCpf(dto.getCpf());
        }

        if(dto.getBirthDate() != null) {
            servant.setBirthDate(dto.getBirthDate());
        }

        if(dto.getType() != null) {
            servant.setType(dto.getType());
        }

        if(dto.getGender() != null) {
            servant.setGender(dto.getGender());
        }

        if(dto.getRegistrationNumber() != null) {
            servant.setRegistrationNumber(dto.getRegistrationNumber());
        }

        return servant;
    }

    public Servant convert(CreateServantRequestDto dto) {
        Servant servant = new Servant();

        servant.setName(dto.getName());
        servant.setEmail(dto.getEmail());
        servant.setGender(dto.getGender());
        servant.setCpf(dto.getCpf());
        servant.setType(dto.getType());
        servant.setRegistrationNumber(dto.getRegistrationNumber());
        servant.setBirthDate(dto.getBirthDate());

        return servant;
    }
}