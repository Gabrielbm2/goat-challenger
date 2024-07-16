package api.com.transmission.specializations.dtos;

import api.com.transmission.specializations.models.ServantType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateServantRequestDto {

    @NotNull(message = "O campo nome é obrigatório")
    private String name;

    @NotNull(message = "O campo email é obrigatório")
    private String email;

    @NotNull(message = "O campo cpf é obrigatório")
    private String cpf;

    @NotNull(message = "O campo matrícula é obrigatório")
    @JsonProperty("registration_number")
    private String registrationNumber;

    @NotNull(message = "O campo sexo é obrigatório")
    private String gender;

    @NotNull(message = "O campo tipo é obrigatório")
    private ServantType type;

    @NotNull(message = "O campo data de nascimento é obrigatório")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("birth_date")
    private Date birthDate;
}
