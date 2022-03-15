package tender.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tender.domain.PonudePonudjaci;

/**
 * Spring Data SQL repository for the PonudePonudjaci entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PonudePonudjaciRepository extends JpaRepository<PonudePonudjaci, Long> {
    List<PonudePonudjaci> findBySifraPostupka(Integer sifraPostupka);
}
