package api.com.transmission.specializations.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "specializations")
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    @NotNull(message = "O campo Área é obrigatório")
    private String area;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "O campo tipo é obrigatório")
    private SpecializationType type;

    @Column(nullable = false)
    @NotNull(message = "O campo carga horária é obrigatório")
    @JsonProperty("total_hours")
    private Float totalHours;

    @Column(nullable = false)
    @NotNull(message = "O campo valor total do custo da\n" +
            "especialização é obrigatório")
    @JsonProperty("total_coast")
    private Float totalCoast;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;

    public Specialization(UUID servantId, String area, Float totalHours, Float totalCoast, SpecializationType type) {
        this.id = servantId;
        this.area = area;
        this.type = type;
        this.totalHours = totalHours;
        this.totalCoast = totalCoast;
    }
}
