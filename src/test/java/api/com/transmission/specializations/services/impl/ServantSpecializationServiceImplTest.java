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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServantSpecializationServiceImplTest {

    @Mock
    private ServantRepository servantRepository;

    @Mock
    private SpecializationRepository specializationRepository;

    @Mock
    private ServantSpecializationApprovalRepository approvalRepository;

    @Mock
    private ServantSpecializationRejectionRepository rejectionRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ServantSpecializationServiceImpl specializationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testApprove() {
        UUID servantId = UUID.randomUUID();
        UUID specializationId = UUID.randomUUID();
        CreateServantSpecializationApprovalRequestDto dto = new CreateServantSpecializationApprovalRequestDto(servantId, specializationId);

        Servant servant = new Servant();
        servant.setId(servantId);
        servant.setEmail("servant@example.com");

        Specialization specialization = new Specialization();
        specialization.setId(specializationId);

        when(servantRepository.findById(servantId)).thenReturn(Optional.of(servant));
        when(specializationRepository.findById(specializationId)).thenReturn(Optional.of(specialization));

        specializationService.approve(dto);

        verify(approvalRepository, times(1)).save(any(ServantSpecializationApproval.class));
        verify(emailService, times(1)).sendApprovalEmail(eq("servant@example.com"), any(Specialization.class));
    }

    @Test
    public void testReject() {
        UUID servantId = UUID.randomUUID();
        UUID specializationId = UUID.randomUUID();
        String content = "Rejection reason";
        CreateServantSpecializationRejectionRequestDto dto = new CreateServantSpecializationRejectionRequestDto(servantId, specializationId, content);

        Servant servant = new Servant();
        servant.setId(servantId);
        servant.setEmail("servant@example.com");

        Specialization specialization = new Specialization();
        specialization.setId(specializationId);

        when(servantRepository.findById(servantId)).thenReturn(Optional.of(servant));
        when(specializationRepository.findById(specializationId)).thenReturn(Optional.of(specialization));

        specializationService.reject(dto);

        verify(rejectionRepository, times(1)).save(any(ServantSpecializationRejection.class));
        verify(emailService, times(1)).sendRejectionEmail(eq("servant@example.com"), eq(specialization), eq(content));
    }

}
