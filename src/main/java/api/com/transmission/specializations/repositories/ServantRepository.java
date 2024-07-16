package api.com.transmission.specializations.repositories;

import api.com.transmission.specializations.models.Servant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ServantRepository extends JpaRepository<Servant, UUID> {
}
