package api.com.transmission.specializations.dtos;

import api.com.transmission.specializations.models.Specialization;
import api.com.transmission.specializations.models.SpecializationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.util.UUID;




@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Jacksonized
public class SpecializationResponseDto implements Serializable{
    public SpecializationResponseDto (Specialization specialization) {
        setId(specialization.getId());
        setArea(specialization.getArea());
        setType(specialization.getType());
        setTotalCoast(specialization.getTotalCoast());
        setTotalHours(specialization.getTotalHours());
    }

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("area")
    private String area;

    @JsonProperty("type")
    private SpecializationType type;

    @JsonProperty("total_hours")
    private Float totalHours;

    @JsonProperty("total_coast")
    private Float totalCoast;

}
