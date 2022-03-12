package tender.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tender.domain.Specifikacije;

/**
 * Spring Data SQL repository for the Specifikacije entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecifikacijeRepository extends JpaRepository<Specifikacije, Long> {
    List<Specifikacije> findBySifraPostupka(Integer sifra_postupka);
}
