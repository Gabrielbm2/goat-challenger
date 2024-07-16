package api.com.transmission.specializations.models;

import api.com.transmission.specializations.dtos.RegisterRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    @NotNull(message = "O campo nome é obrigatório")
    private String name;

    @Column(unique = true)
    @NotNull(message = "O campo e-mail é obrigatório")
    private String email;

    @Column
    @NotNull(message = "O campo senha é obrigatório")
    @JsonIgnore
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;

    public User(RegisterRequestDto data) {
        this.name = data.getName();
        this.email = data.getEmail();
        this.password = data.getPassword();
    }

    public User(UUID userId, String example, String email, String password) {
        this.id = userId;
        this.name = example;
        this.email = email;
    }

    public User(String name, String email, String hashedPassword) {
        this.name = name;
        this.email = email;
        this.password = hashedPassword;
    }
}
