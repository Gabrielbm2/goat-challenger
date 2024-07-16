package api.com.transmission.specializations.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDto {

    @NotNull(message = "O campo nome é obrigatório")
    private String name;

    private String email;

    @NotNull(message = "O campo senha é obrigatório")
    private String password;

    public CreateUserRequestDto(String newUser, String mail) {
        this.name = newUser;
        this.email = mail;
    }
}
