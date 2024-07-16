package api.com.transmission.specializations.dtos;

import api.com.transmission.specializations.models.SpecializationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSpecializationRequestDto {

    @NotNull(message = "O campo Área é obrigatório")
    private String area;

    @NotNull(message = "O campo tipo é obrigatório")
    private SpecializationType type;

    @NotNull(message = "O campo carga horária é obrigatório")
    @JsonProperty("total_hours")
    private Float totalHours;

    @NotNull(message = "O campo valor total do custo da especialização é obrigatório")
    @JsonProperty("total_coast")
    private Float totalCoast;
}
