package api.com.transmission.specializations.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateServantSpecializationApprovalRequestDto {

    @NotNull(message = "O campo servidor é obrigatório")
    @JsonProperty("servant_id")
    private UUID servantId;

    @NotNull(message = "O campo especialização é obrigatório")
    @JsonProperty("specialization_id")
    private UUID specializationId;
}
