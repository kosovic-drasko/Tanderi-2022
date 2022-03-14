package tender.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tender.domain.Prvorangirani;

/**
 * Spring Data SQL repository for the Prvorangirani entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrvorangiraniRepository extends JpaRepository<Prvorangirani, Long> {
    List<Prvorangirani> findBySifraPostupka(@Param("sifraPostupka") Integer sifra);
}
