package api.com.transmission.specializations.services;

import api.com.transmission.specializations.dtos.CreateServantSpecializationApprovalRequestDto;
import api.com.transmission.specializations.dtos.CreateServantSpecializationRejectionRequestDto;

public interface ServantSpecializationService {


    void approve(CreateServantSpecializationApprovalRequestDto dto);

    void reject(CreateServantSpecializationRejectionRequestDto dto);
}
