package tender.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tender.domain.Ponude;

/**
 * Spring Data SQL repository for the Ponude entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PonudeRepository extends JpaRepository<Ponude, Long> {
    List<Ponude> findBySifraPostupka(Integer sifra_postupka);
    List<Ponude> findBySifraPonude(Integer sifra_ponude);
    List<Ponude> findPonudeByBrojPartije(Integer sifra_postupka);
}
