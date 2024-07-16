package api.com.transmission.specializations.repositories;

import api.com.transmission.specializations.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ServantSpecializationRejectionRepository extends JpaRepository<ServantSpecializationRejection, UUID> {
    boolean existsByServantIdAndSpecializationId(UUID servantId, UUID specializationId);
}
