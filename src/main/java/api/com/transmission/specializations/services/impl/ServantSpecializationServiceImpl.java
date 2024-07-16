package api.com.transmission.specializations.services.impl;

import api.com.transmission.specializations.dtos.CreateServantSpecializationApprovalRequestDto;
import api.com.transmission.specializations.dtos.CreateServantSpecializationRejectionRequestDto;
import api.com.transmission.specializations.models.Servant;
import api.com.transmission.specializations.models.Specialization;
import api.com.transmission.specializations.models.ServantSpecializationApproval;
import api.com.transmission.specializations.models.ServantSpecializationRejection;
import api.com.transmission.specializations.repositories.ServantRepository;
import api.com.transmission.specializations.repositories.SpecializationRepository;
import api.com.transmission.specializations.repositories.ServantSpecializationApprovalRepository;
import api.com.transmission.specializations.repositories.ServantSpecializationRejectionRepository;
import api.com.transmission.specializations.services.EmailService;
import api.com.transmission.specializations.services.ServantSpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class ServantSpecializationServiceImpl implements ServantSpecializationService {

    @Autowired
    private ServantRepository servantRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private ServantSpecializationApprovalRepository approvalRepository;

    @Autowired
    private ServantSpecializationRejectionRepository rejectionRepository;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public void approve(CreateServantSpecializationApprovalRequestDto dto) {
        UUID servantId = dto.getServantId();
        UUID specializationId = dto.getSpecializationId();

        if (approvalRepository.existsByServantIdAndSpecializationId(servantId, specializationId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This specialization has already been approved by the servant.");
        }

        Servant servant = getServantById(servantId);
        Specialization specialization = getSpecializationById(specializationId);

        ServantSpecializationApproval approval = ServantSpecializationApproval.builder()
                .servant(servant)
                .specialization(specialization)
                .build();

        approvalRepository.save(approval);

        String servantEmail = servant.getEmail();
        emailService.sendApprovalEmail(servantEmail, specialization);
    }

    @Override
    @Transactional
    public void reject(CreateServantSpecializationRejectionRequestDto dto) {
        UUID servantId = dto.getServantId();
        UUID specializationId = dto.getSpecializationId();

        if (rejectionRepository.existsByServantIdAndSpecializationId(servantId, specializationId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This specialization has already been rejected by the servant.");
        }

        String content = dto.getContent();
        Servant servant = getServantById(servantId);
        Specialization specialization = getSpecializationById(specializationId);

        ServantSpecializationRejection rejection = ServantSpecializationRejection.builder()
                .servant(servant)
                .specialization(specialization)
                .content(content)
                .build();

        rejectionRepository.save(rejection);

        String servantEmail = servant.getEmail();
        emailService.sendRejectionEmail(servantEmail, specialization, content);
    }

    public Servant getServantById(UUID servantId) {
        return servantRepository.findById(servantId)
                .orElseThrow(() -> new IllegalArgumentException("Servant not found with ID: " + servantId));
    }

    public Specialization getSpecializationById(UUID specializationId) {
        return specializationRepository.findById(specializationId)
                .orElseThrow(() -> new IllegalArgumentException("Specialization not found with ID: " + specializationId));
    }
}
