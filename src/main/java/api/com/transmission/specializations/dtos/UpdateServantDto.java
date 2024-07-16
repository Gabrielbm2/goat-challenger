package api.com.transmission.specializations.dtos;

import api.com.transmission.specializations.models.ServantType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateServantDto {

    private String name;

    private String email;

    private String cpf;

    @JsonProperty("registration_number")
    private String registrationNumber;

    private String gender;

    private ServantType type;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("birth_date")
    private Date birthDate;
}
