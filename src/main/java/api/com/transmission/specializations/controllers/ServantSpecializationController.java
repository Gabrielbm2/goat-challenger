package api.com.transmission.specializations.controllers;

import api.com.transmission.specializations.dtos.CreateServantSpecializationApprovalRequestDto;
import api.com.transmission.specializations.dtos.CreateServantSpecializationRejectionRequestDto;
import api.com.transmission.specializations.services.ServantSpecializationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servant-specializations")
@Validated
@Tag(name = "ServantSpecializations", description = "Endpoints for Users")
public class ServantSpecializationController {

    private final ServantSpecializationService specializationService;

    @Autowired
    public ServantSpecializationController(ServantSpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @PostMapping("/approve")
    @Operation(summary = "Aprova a Specializations do servidor", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> approveSpecialization(
            @Valid @RequestBody CreateServantSpecializationApprovalRequestDto dto) {
        specializationService.approve(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/reject")
    @Operation(summary = "Rejeita a Specializations do servidor", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> rejectSpecialization(
            @Valid @RequestBody CreateServantSpecializationRejectionRequestDto dto) {
        specializationService.reject(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
