package api.com.transmission.specializations.dtos;

import api.com.transmission.specializations.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    public UserResponseDto(User user) {
        setId(user.getId());
        setName(user.getName());
        setEmail(user.getEmail());

    }

    private UUID id;

    private String name;

    private String email;
}
