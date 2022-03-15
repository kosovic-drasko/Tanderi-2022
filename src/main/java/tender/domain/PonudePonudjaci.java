package tender.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PonudePonudjaci.
 */
@Entity
@Table(name = "ponude_ponudjaci")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PonudePonudjaci implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "sifra_postupka")
    private Integer sifraPostupka;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PonudePonudjaci id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSifraPostupka() {
        return this.sifraPostupka;
    }

    public PonudePonudjaci sifraPostupka(Integer sifraPostupka) {
        this.setSifraPostupka(sifraPostupka);
        return this;
    }

    public void setSifraPostupka(Integer sifraPostupka) {
        this.sifraPostupka = sifraPostupka;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PonudePonudjaci)) {
            return false;
        }
        return id != null && id.equals(((PonudePonudjaci) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PonudePonudjaci{" +
            "id=" + getId() +
            ", sifraPostupka=" + getSifraPostupka() +
            "}";
    }
}
