package tender.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tender.domain.ViewVrednovanje;

/**
 * Spring Data SQL repository for the ViewVrednovanje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ViewVrednovanjeRepository extends JpaRepository<ViewVrednovanje, Long> {}
