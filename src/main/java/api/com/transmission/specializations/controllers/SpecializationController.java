package api.com.transmission.specializations.controllers;


import api.com.transmission.specializations.dtos.*;
import api.com.transmission.specializations.models.Specialization;
import api.com.transmission.specializations.services.SpecializationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/specializations")
@Tag(name = "Specializations", description = "Endpoints for Servidores")
@RequiredArgsConstructor
public class SpecializationController {

    private final SpecializationService specializationService;

    @GetMapping()
    @Operation(summary = "Retorna Specializations", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<SpecializationResponseDto>> getAllSpecializations() {
        List<SpecializationResponseDto> specializations = specializationService.list();
        return ResponseEntity.ok(specializations);
    }

    @PostMapping
    @Operation(summary = "Create Specialization", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<SpecializationResponseDto> createSpecialization(@RequestBody CreateSpecializationRequestDto createSpecializationRequestDTO) {
        SpecializationResponseDto newSpecialization = specializationService.save(createSpecializationRequestDTO);
        return ResponseEntity.ok(newSpecialization);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pesquisa o Specialization pelo ID", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<SpecializationResponseDto> getSpecializationById(@PathVariable UUID id) {
        try {
            SpecializationResponseDto specialization = specializationService.findById(id);
            return ResponseEntity.ok(specialization);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Specialization", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<SpecializationResponseDto> updateSpecialization(@PathVariable UUID id, @RequestBody UpdateSpecializationDto updateSpecializationDTO) {
        try {
            SpecializationResponseDto updatedSpecialization = specializationService.update(id, updateSpecializationDTO);
            return ResponseEntity.ok(updatedSpecialization);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Servant", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Specialization> deleteSpecialization(@PathVariable UUID id) {
        try {
            specializationService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
