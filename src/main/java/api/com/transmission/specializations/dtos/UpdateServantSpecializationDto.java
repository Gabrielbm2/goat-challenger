package api.com.transmission.specializations.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateServantSpecializationDto {

    @JsonProperty("servant_id")
    private UUID servantId;

    @JsonProperty("specialization_id")
    private UUID specializationId;
}
