package api.com.transmission.specializations.dtos;

import api.com.transmission.specializations.models.Servant;
import api.com.transmission.specializations.models.ServantType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServantResponseDto {

    private UUID id;
    private String cpf;
    private String name;
    private String email;
    private String gender;

    @JsonProperty("registration_number")
    private String registrationNumber;

    private ServantType type;

    @JsonProperty("birth_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    public ServantResponseDto(Servant servant) {
        this.id = servant.getId();
        this.cpf = servant.getCpf();
        this.type = servant.getType();
        this.name = servant.getName();
        this.email = servant.getEmail();
        this.registrationNumber = servant.getRegistrationNumber();
        this.gender = servant.getGender();
        this.birthDate = servant.getBirthDate();
    }
}
