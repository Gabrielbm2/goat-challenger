package api.com.transmission.specializations.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDto {

    @NotNull(message = "O campo nome é obrigatório")
    private String name;

    @NotNull(message = "O campo email é obrigatório")
    private String email;

    @NotNull(message = "O campo password é obrigatório")
    private String password;
}
