package tender.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ponudjaci.
 */
@Entity
@Table(name = "ponudjaci")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ponudjaci implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naziv_ponudjaca")
    private String nazivPonudjaca;

    @Column(name = "odgovorno_lice")
    private String odgovorno_lice;

    @Column(name = "adresa_ponudjaca")
    private String adresa_ponudjaca;

    @Column(name = "banka_racun")
    private String banka_racun;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ponudjaci id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazivPonudjaca() {
        return this.nazivPonudjaca;
    }

    public Ponudjaci nazivPonudjaca(String nazivPonudjaca) {
        this.setNazivPonudjaca(nazivPonudjaca);
        return this;
    }

    public void setNazivPonudjaca(String nazivPonudjaca) {
        this.nazivPonudjaca = nazivPonudjaca;
    }

    public String getOdgovorno_lice() {
        return this.odgovorno_lice;
    }

    public Ponudjaci odgovorno_lice(String odgovorno_lice) {
        this.setOdgovorno_lice(odgovorno_lice);
        return this;
    }

    public void setOdgovorno_lice(String odgovorno_lice) {
        this.odgovorno_lice = odgovorno_lice;
    }

    public String getAdresa_ponudjaca() {
        return this.adresa_ponudjaca;
    }

    public Ponudjaci adresa_ponudjaca(String adresa_ponudjaca) {
        this.setAdresa_ponudjaca(adresa_ponudjaca);
        return this;
    }

    public void setAdresa_ponudjaca(String adresa_ponudjaca) {
        this.adresa_ponudjaca = adresa_ponudjaca;
    }

    public String getBanka_racun() {
        return this.banka_racun;
    }

    public Ponudjaci banka_racun(String banka_racun) {
        this.setBanka_racun(banka_racun);
        return this;
    }

    public void setBanka_racun(String banka_racun) {
        this.banka_racun = banka_racun;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ponudjaci)) {
            return false;
        }
        return id != null && id.equals(((Ponudjaci) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ponudjaci{" +
            "id=" + getId() +
            ", nazivPonudjaca='" + getNazivPonudjaca() + "'" +
            ", odgovorno_lice='" + getOdgovorno_lice() + "'" +
            ", adresa_ponudjaca='" + getAdresa_ponudjaca() + "'" +
            ", banka_racun='" + getBanka_racun() + "'" +
            "}";
    }
}
