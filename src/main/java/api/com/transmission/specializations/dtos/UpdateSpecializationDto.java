package api.com.transmission.specializations.dtos;

import api.com.transmission.specializations.models.SpecializationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateSpecializationDto {

    private String area;

    private SpecializationType type;

    @JsonProperty("total_hours")
    private Float totalHours;

    @JsonProperty("total_coast")
    private Float totalCoast;
}
