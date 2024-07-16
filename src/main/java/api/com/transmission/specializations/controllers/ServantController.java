package api.com.transmission.specializations.controllers;

import api.com.transmission.specializations.dtos.*;
import api.com.transmission.specializations.services.ServantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/servants")
@Tag(name = "Servants", description = "Endpoints for Servidores")
@RequiredArgsConstructor
public class ServantController {

    private final ServantService servantService;

    @GetMapping()
    @Operation(summary = "Retorna Servidores", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<ServantResponseDto>> list() {
        List<ServantResponseDto> list = servantService.list();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pesquisa o Servidor pelo ID", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ServantResponseDto> getServantById(@PathVariable UUID id) {
        try {
            ServantResponseDto servant = servantService.findById(id);
            return ResponseEntity.ok(servant);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create Servant", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ServantResponseDto> createServant(@RequestBody CreateServantRequestDto createServantRequestDTO) {
        ServantResponseDto newServant = servantService.save(createServantRequestDTO);
        return ResponseEntity.ok(newServant);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update Servant", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ServantResponseDto> updateServant(@PathVariable UUID id, @RequestBody UpdateServantDto updateServantDTO) {
        try {
            ServantResponseDto updatedServant = servantService.update(id, updateServantDTO);
            return ResponseEntity.ok(updatedServant);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Servant", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> deleteServant(@PathVariable UUID id) {
        try {
            servantService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
