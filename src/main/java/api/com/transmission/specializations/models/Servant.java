package api.com.transmission.specializations.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "servants")
public class Servant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    @NotNull(message = "O campo CPF é obrigatório")
    private String cpf;

    @Column(unique = true, nullable = false)
    @NotNull(message = "O campo e-mail é obrigatório")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "O campo matrícula é obrigatório")
    @JsonProperty("registration_number")
    private String registrationNumber;

    @Column(nullable = false)
    @NotNull(message = "O campo nome é obrigatório")
    private String name;

    @Column(nullable = false)
    @JsonProperty("birth_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;

    @Column(nullable = false)
    @NotNull(message = "O campo sexo é obrigatório")
    private String gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "O campo tipo é obrigatório")
    private ServantType type;


    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;

    public Servant(UUID servantId, String name, String email, String cpf, String registrationNumber, String gender, ServantType type, Date birthDate) {
        this.id = servantId;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.gender = gender;
        this.type = type;
        this.registrationNumber = registrationNumber;
    }
}
