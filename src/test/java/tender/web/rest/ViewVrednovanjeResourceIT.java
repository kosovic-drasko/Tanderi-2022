package tender.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tender.IntegrationTest;
import tender.domain.ViewVrednovanje;
import tender.repository.ViewVrednovanjeRepository;

/**
 * Integration tests for the {@link ViewVrednovanjeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ViewVrednovanjeResourceIT {

    private static final Integer DEFAULT_SIFRA_POSTUPKA = 1;
    private static final Integer UPDATED_SIFRA_POSTUPKA = 2;

    private static final Integer DEFAULT_SIFRA_PONUDE = 1;
    private static final Integer UPDATED_SIFRA_PONUDE = 2;

    private static final Integer DEFAULT_BROJ_PARTIJE = 1;
    private static final Integer UPDATED_BROJ_PARTIJE = 2;

    private static final String DEFAULT_ATC = "AAAAAAAAAA";
    private static final String UPDATED_ATC = "BBBBBBBBBB";

    private static final String DEFAULT_INN = "AAAAAAAAAA";
    private static final String UPDATED_INN = "BBBBBBBBBB";

    private static final String DEFAULT_ZASTICENI_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_ZASTICENI_NAZIV = "BBBBBBBBBB";

    private static final String DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA = "AAAAAAAAAA";
    private static final String UPDATED_FARMACEUTSKI_OBLIK_LIJEKA = "BBBBBBBBBB";

    private static final String DEFAULT_JACINA_LIJEKA = "AAAAAAAAAA";
    private static final String UPDATED_JACINA_LIJEKA = "BBBBBBBBBB";

    private static final String DEFAULT_PAKOVANJE = "AAAAAAAAAA";
    private static final String UPDATED_PAKOVANJE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRAZENA_KOLICINA = 1;
    private static final Integer UPDATED_TRAZENA_KOLICINA = 2;

    private static final Double DEFAULT_PROCIJENJENA_VRIJEDNOST = 1D;
    private static final Double UPDATED_PROCIJENJENA_VRIJEDNOST = 2D;

    private static final Double DEFAULT_PONUDJENA_VRIJEDNOST = 1D;
    private static final Double UPDATED_PONUDJENA_VRIJEDNOST = 2D;

    private static final Integer DEFAULT_ROK_ISPORUKE = 1;
    private static final Integer UPDATED_ROK_ISPORUKE = 2;

    private static final String DEFAULT_NAZIV_PROIZVODJACA = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV_PROIZVODJACA = "BBBBBBBBBB";

    private static final String DEFAULT_NAZIV_PONUDJACA = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV_PONUDJACA = "BBBBBBBBBB";

    private static final Double DEFAULT_BOD_CIJENA = 1D;
    private static final Double UPDATED_BOD_CIJENA = 2D;

    private static final Double DEFAULT_BOD_ROK = 1D;
    private static final Double UPDATED_BOD_ROK = 2D;

    private static final Double DEFAULT_BOD_UKUPNO = 1D;
    private static final Double UPDATED_BOD_UKUPNO = 2D;

    private static final String ENTITY_API_URL = "/api/view-vrednovanjes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ViewVrednovanjeRepository viewVrednovanjeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restViewVrednovanjeMockMvc;

    private ViewVrednovanje viewVrednovanje;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ViewVrednovanje createEntity(EntityManager em) {
        ViewVrednovanje viewVrednovanje = new ViewVrednovanje()
            .sifraPostupka(DEFAULT_SIFRA_POSTUPKA)
            .sifraPonude(DEFAULT_SIFRA_PONUDE)
            .brojPartije(DEFAULT_BROJ_PARTIJE)
            .atc(DEFAULT_ATC)
            .inn(DEFAULT_INN)
            .zasticeniNaziv(DEFAULT_ZASTICENI_NAZIV)
            .farmaceutskiOblikLijeka(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA)
            .jacinaLijeka(DEFAULT_JACINA_LIJEKA)
            .pakovanje(DEFAULT_PAKOVANJE)
            .trazenaKolicina(DEFAULT_TRAZENA_KOLICINA)
            .procijenjenaVrijednost(DEFAULT_PROCIJENJENA_VRIJEDNOST)
            .ponudjenaVrijednost(DEFAULT_PONUDJENA_VRIJEDNOST)
            .rokIsporuke(DEFAULT_ROK_ISPORUKE)
            .nazivProizvodjaca(DEFAULT_NAZIV_PROIZVODJACA)
            .nazivPonudjaca(DEFAULT_NAZIV_PONUDJACA)
            .bodCijena(DEFAULT_BOD_CIJENA)
            .bodRok(DEFAULT_BOD_ROK)
            .bodUkupno(DEFAULT_BOD_UKUPNO);
        return viewVrednovanje;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ViewVrednovanje createUpdatedEntity(EntityManager em) {
        ViewVrednovanje viewVrednovanje = new ViewVrednovanje()
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .sifraPonude(UPDATED_SIFRA_PONUDE)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .atc(UPDATED_ATC)
            .inn(UPDATED_INN)
            .zasticeniNaziv(UPDATED_ZASTICENI_NAZIV)
            .farmaceutskiOblikLijeka(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA)
            .jacinaLijeka(UPDATED_JACINA_LIJEKA)
            .pakovanje(UPDATED_PAKOVANJE)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST)
            .ponudjenaVrijednost(UPDATED_PONUDJENA_VRIJEDNOST)
            .rokIsporuke(UPDATED_ROK_ISPORUKE)
            .nazivProizvodjaca(UPDATED_NAZIV_PROIZVODJACA)
            .nazivPonudjaca(UPDATED_NAZIV_PONUDJACA)
            .bodCijena(UPDATED_BOD_CIJENA)
            .bodRok(UPDATED_BOD_ROK)
            .bodUkupno(UPDATED_BOD_UKUPNO);
        return viewVrednovanje;
    }

    @BeforeEach
    public void initTest() {
        viewVrednovanje = createEntity(em);
    }

    @Test
    @Transactional
    void createViewVrednovanje() throws Exception {
        int databaseSizeBeforeCreate = viewVrednovanjeRepository.findAll().size();
        // Create the ViewVrednovanje
        restViewVrednovanjeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(viewVrednovanje))
            )
            .andExpect(status().isCreated());

        // Validate the ViewVrednovanje in the database
        List<ViewVrednovanje> viewVrednovanjeList = viewVrednovanjeRepository.findAll();
        assertThat(viewVrednovanjeList).hasSize(databaseSizeBeforeCreate + 1);
        ViewVrednovanje testViewVrednovanje = viewVrednovanjeList.get(viewVrednovanjeList.size() - 1);
        assertThat(testViewVrednovanje.getSifraPostupka()).isEqualTo(DEFAULT_SIFRA_POSTUPKA);
        assertThat(testViewVrednovanje.getSifraPonude()).isEqualTo(DEFAULT_SIFRA_PONUDE);
        assertThat(testViewVrednovanje.getBrojPartije()).isEqualTo(DEFAULT_BROJ_PARTIJE);
        assertThat(testViewVrednovanje.getAtc()).isEqualTo(DEFAULT_ATC);
        assertThat(testViewVrednovanje.getInn()).isEqualTo(DEFAULT_INN);
        assertThat(testViewVrednovanje.getZasticeniNaziv()).isEqualTo(DEFAULT_ZASTICENI_NAZIV);
        assertThat(testViewVrednovanje.getFarmaceutskiOblikLijeka()).isEqualTo(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA);
        assertThat(testViewVrednovanje.getJacinaLijeka()).isEqualTo(DEFAULT_JACINA_LIJEKA);
        assertThat(testViewVrednovanje.getPakovanje()).isEqualTo(DEFAULT_PAKOVANJE);
        assertThat(testViewVrednovanje.getTrazenaKolicina()).isEqualTo(DEFAULT_TRAZENA_KOLICINA);
        assertThat(testViewVrednovanje.getProcijenjenaVrijednost()).isEqualTo(DEFAULT_PROCIJENJENA_VRIJEDNOST);
        assertThat(testViewVrednovanje.getPonudjenaVrijednost()).isEqualTo(DEFAULT_PONUDJENA_VRIJEDNOST);
        assertThat(testViewVrednovanje.getRokIsporuke()).isEqualTo(DEFAULT_ROK_ISPORUKE);
        assertThat(testViewVrednovanje.getNazivProizvodjaca()).isEqualTo(DEFAULT_NAZIV_PROIZVODJACA);
        assertThat(testViewVrednovanje.getNazivPonudjaca()).isEqualTo(DEFAULT_NAZIV_PONUDJACA);
        assertThat(testViewVrednovanje.getBodCijena()).isEqualTo(DEFAULT_BOD_CIJENA);
        assertThat(testViewVrednovanje.getBodRok()).isEqualTo(DEFAULT_BOD_ROK);
        assertThat(testViewVrednovanje.getBodUkupno()).isEqualTo(DEFAULT_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void createViewVrednovanjeWithExistingId() throws Exception {
        // Create the ViewVrednovanje with an existing ID
        viewVrednovanje.setId(1L);

        int databaseSizeBeforeCreate = viewVrednovanjeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restViewVrednovanjeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(viewVrednovanje))
            )
            .andExpect(status().isBadRequest());

        // Validate the ViewVrednovanje in the database
        List<ViewVrednovanje> viewVrednovanjeList = viewVrednovanjeRepository.findAll();
        assertThat(viewVrednovanjeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllViewVrednovanjes() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get all the viewVrednovanjeList
        restViewVrednovanjeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viewVrednovanje.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].sifraPonude").value(hasItem(DEFAULT_SIFRA_PONUDE)))
            .andExpect(jsonPath("$.[*].brojPartije").value(hasItem(DEFAULT_BROJ_PARTIJE)))
            .andExpect(jsonPath("$.[*].atc").value(hasItem(DEFAULT_ATC)))
            .andExpect(jsonPath("$.[*].inn").value(hasItem(DEFAULT_INN)))
            .andExpect(jsonPath("$.[*].zasticeniNaziv").value(hasItem(DEFAULT_ZASTICENI_NAZIV)))
            .andExpect(jsonPath("$.[*].farmaceutskiOblikLijeka").value(hasItem(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA)))
            .andExpect(jsonPath("$.[*].jacinaLijeka").value(hasItem(DEFAULT_JACINA_LIJEKA)))
            .andExpect(jsonPath("$.[*].pakovanje").value(hasItem(DEFAULT_PAKOVANJE)))
            .andExpect(jsonPath("$.[*].trazenaKolicina").value(hasItem(DEFAULT_TRAZENA_KOLICINA)))
            .andExpect(jsonPath("$.[*].procijenjenaVrijednost").value(hasItem(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].ponudjenaVrijednost").value(hasItem(DEFAULT_PONUDJENA_VRIJEDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].rokIsporuke").value(hasItem(DEFAULT_ROK_ISPORUKE)))
            .andExpect(jsonPath("$.[*].nazivProizvodjaca").value(hasItem(DEFAULT_NAZIV_PROIZVODJACA)))
            .andExpect(jsonPath("$.[*].nazivPonudjaca").value(hasItem(DEFAULT_NAZIV_PONUDJACA)))
            .andExpect(jsonPath("$.[*].bodCijena").value(hasItem(DEFAULT_BOD_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].bodRok").value(hasItem(DEFAULT_BOD_ROK.doubleValue())))
            .andExpect(jsonPath("$.[*].bodUkupno").value(hasItem(DEFAULT_BOD_UKUPNO.doubleValue())));
    }

    @Test
    @Transactional
    void getViewVrednovanje() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        // Get the viewVrednovanje
        restViewVrednovanjeMockMvc
            .perform(get(ENTITY_API_URL_ID, viewVrednovanje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(viewVrednovanje.getId().intValue()))
            .andExpect(jsonPath("$.sifraPostupka").value(DEFAULT_SIFRA_POSTUPKA))
            .andExpect(jsonPath("$.sifraPonude").value(DEFAULT_SIFRA_PONUDE))
            .andExpect(jsonPath("$.brojPartije").value(DEFAULT_BROJ_PARTIJE))
            .andExpect(jsonPath("$.atc").value(DEFAULT_ATC))
            .andExpect(jsonPath("$.inn").value(DEFAULT_INN))
            .andExpect(jsonPath("$.zasticeniNaziv").value(DEFAULT_ZASTICENI_NAZIV))
            .andExpect(jsonPath("$.farmaceutskiOblikLijeka").value(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA))
            .andExpect(jsonPath("$.jacinaLijeka").value(DEFAULT_JACINA_LIJEKA))
            .andExpect(jsonPath("$.pakovanje").value(DEFAULT_PAKOVANJE))
            .andExpect(jsonPath("$.trazenaKolicina").value(DEFAULT_TRAZENA_KOLICINA))
            .andExpect(jsonPath("$.procijenjenaVrijednost").value(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue()))
            .andExpect(jsonPath("$.ponudjenaVrijednost").value(DEFAULT_PONUDJENA_VRIJEDNOST.doubleValue()))
            .andExpect(jsonPath("$.rokIsporuke").value(DEFAULT_ROK_ISPORUKE))
            .andExpect(jsonPath("$.nazivProizvodjaca").value(DEFAULT_NAZIV_PROIZVODJACA))
            .andExpect(jsonPath("$.nazivPonudjaca").value(DEFAULT_NAZIV_PONUDJACA))
            .andExpect(jsonPath("$.bodCijena").value(DEFAULT_BOD_CIJENA.doubleValue()))
            .andExpect(jsonPath("$.bodRok").value(DEFAULT_BOD_ROK.doubleValue()))
            .andExpect(jsonPath("$.bodUkupno").value(DEFAULT_BOD_UKUPNO.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingViewVrednovanje() throws Exception {
        // Get the viewVrednovanje
        restViewVrednovanjeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewViewVrednovanje() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        int databaseSizeBeforeUpdate = viewVrednovanjeRepository.findAll().size();

        // Update the viewVrednovanje
        ViewVrednovanje updatedViewVrednovanje = viewVrednovanjeRepository.findById(viewVrednovanje.getId()).get();
        // Disconnect from session so that the updates on updatedViewVrednovanje are not directly saved in db
        em.detach(updatedViewVrednovanje);
        updatedViewVrednovanje
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .sifraPonude(UPDATED_SIFRA_PONUDE)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .atc(UPDATED_ATC)
            .inn(UPDATED_INN)
            .zasticeniNaziv(UPDATED_ZASTICENI_NAZIV)
            .farmaceutskiOblikLijeka(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA)
            .jacinaLijeka(UPDATED_JACINA_LIJEKA)
            .pakovanje(UPDATED_PAKOVANJE)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST)
            .ponudjenaVrijednost(UPDATED_PONUDJENA_VRIJEDNOST)
            .rokIsporuke(UPDATED_ROK_ISPORUKE)
            .nazivProizvodjaca(UPDATED_NAZIV_PROIZVODJACA)
            .nazivPonudjaca(UPDATED_NAZIV_PONUDJACA)
            .bodCijena(UPDATED_BOD_CIJENA)
            .bodRok(UPDATED_BOD_ROK)
            .bodUkupno(UPDATED_BOD_UKUPNO);

        restViewVrednovanjeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedViewVrednovanje.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedViewVrednovanje))
            )
            .andExpect(status().isOk());

        // Validate the ViewVrednovanje in the database
        List<ViewVrednovanje> viewVrednovanjeList = viewVrednovanjeRepository.findAll();
        assertThat(viewVrednovanjeList).hasSize(databaseSizeBeforeUpdate);
        ViewVrednovanje testViewVrednovanje = viewVrednovanjeList.get(viewVrednovanjeList.size() - 1);
        assertThat(testViewVrednovanje.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
        assertThat(testViewVrednovanje.getSifraPonude()).isEqualTo(UPDATED_SIFRA_PONUDE);
        assertThat(testViewVrednovanje.getBrojPartije()).isEqualTo(UPDATED_BROJ_PARTIJE);
        assertThat(testViewVrednovanje.getAtc()).isEqualTo(UPDATED_ATC);
        assertThat(testViewVrednovanje.getInn()).isEqualTo(UPDATED_INN);
        assertThat(testViewVrednovanje.getZasticeniNaziv()).isEqualTo(UPDATED_ZASTICENI_NAZIV);
        assertThat(testViewVrednovanje.getFarmaceutskiOblikLijeka()).isEqualTo(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA);
        assertThat(testViewVrednovanje.getJacinaLijeka()).isEqualTo(UPDATED_JACINA_LIJEKA);
        assertThat(testViewVrednovanje.getPakovanje()).isEqualTo(UPDATED_PAKOVANJE);
        assertThat(testViewVrednovanje.getTrazenaKolicina()).isEqualTo(UPDATED_TRAZENA_KOLICINA);
        assertThat(testViewVrednovanje.getProcijenjenaVrijednost()).isEqualTo(UPDATED_PROCIJENJENA_VRIJEDNOST);
        assertThat(testViewVrednovanje.getPonudjenaVrijednost()).isEqualTo(UPDATED_PONUDJENA_VRIJEDNOST);
        assertThat(testViewVrednovanje.getRokIsporuke()).isEqualTo(UPDATED_ROK_ISPORUKE);
        assertThat(testViewVrednovanje.getNazivProizvodjaca()).isEqualTo(UPDATED_NAZIV_PROIZVODJACA);
        assertThat(testViewVrednovanje.getNazivPonudjaca()).isEqualTo(UPDATED_NAZIV_PONUDJACA);
        assertThat(testViewVrednovanje.getBodCijena()).isEqualTo(UPDATED_BOD_CIJENA);
        assertThat(testViewVrednovanje.getBodRok()).isEqualTo(UPDATED_BOD_ROK);
        assertThat(testViewVrednovanje.getBodUkupno()).isEqualTo(UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void putNonExistingViewVrednovanje() throws Exception {
        int databaseSizeBeforeUpdate = viewVrednovanjeRepository.findAll().size();
        viewVrednovanje.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restViewVrednovanjeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, viewVrednovanje.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(viewVrednovanje))
            )
            .andExpect(status().isBadRequest());

        // Validate the ViewVrednovanje in the database
        List<ViewVrednovanje> viewVrednovanjeList = viewVrednovanjeRepository.findAll();
        assertThat(viewVrednovanjeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchViewVrednovanje() throws Exception {
        int databaseSizeBeforeUpdate = viewVrednovanjeRepository.findAll().size();
        viewVrednovanje.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViewVrednovanjeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(viewVrednovanje))
            )
            .andExpect(status().isBadRequest());

        // Validate the ViewVrednovanje in the database
        List<ViewVrednovanje> viewVrednovanjeList = viewVrednovanjeRepository.findAll();
        assertThat(viewVrednovanjeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamViewVrednovanje() throws Exception {
        int databaseSizeBeforeUpdate = viewVrednovanjeRepository.findAll().size();
        viewVrednovanje.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViewVrednovanjeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(viewVrednovanje))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ViewVrednovanje in the database
        List<ViewVrednovanje> viewVrednovanjeList = viewVrednovanjeRepository.findAll();
        assertThat(viewVrednovanjeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateViewVrednovanjeWithPatch() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        int databaseSizeBeforeUpdate = viewVrednovanjeRepository.findAll().size();

        // Update the viewVrednovanje using partial update
        ViewVrednovanje partialUpdatedViewVrednovanje = new ViewVrednovanje();
        partialUpdatedViewVrednovanje.setId(viewVrednovanje.getId());

        partialUpdatedViewVrednovanje
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .atc(UPDATED_ATC)
            .inn(UPDATED_INN)
            .zasticeniNaziv(UPDATED_ZASTICENI_NAZIV)
            .farmaceutskiOblikLijeka(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA)
            .pakovanje(UPDATED_PAKOVANJE)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST)
            .rokIsporuke(UPDATED_ROK_ISPORUKE)
            .nazivProizvodjaca(UPDATED_NAZIV_PROIZVODJACA)
            .bodRok(UPDATED_BOD_ROK)
            .bodUkupno(UPDATED_BOD_UKUPNO);

        restViewVrednovanjeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedViewVrednovanje.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedViewVrednovanje))
            )
            .andExpect(status().isOk());

        // Validate the ViewVrednovanje in the database
        List<ViewVrednovanje> viewVrednovanjeList = viewVrednovanjeRepository.findAll();
        assertThat(viewVrednovanjeList).hasSize(databaseSizeBeforeUpdate);
        ViewVrednovanje testViewVrednovanje = viewVrednovanjeList.get(viewVrednovanjeList.size() - 1);
        assertThat(testViewVrednovanje.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
        assertThat(testViewVrednovanje.getSifraPonude()).isEqualTo(DEFAULT_SIFRA_PONUDE);
        assertThat(testViewVrednovanje.getBrojPartije()).isEqualTo(DEFAULT_BROJ_PARTIJE);
        assertThat(testViewVrednovanje.getAtc()).isEqualTo(UPDATED_ATC);
        assertThat(testViewVrednovanje.getInn()).isEqualTo(UPDATED_INN);
        assertThat(testViewVrednovanje.getZasticeniNaziv()).isEqualTo(UPDATED_ZASTICENI_NAZIV);
        assertThat(testViewVrednovanje.getFarmaceutskiOblikLijeka()).isEqualTo(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA);
        assertThat(testViewVrednovanje.getJacinaLijeka()).isEqualTo(DEFAULT_JACINA_LIJEKA);
        assertThat(testViewVrednovanje.getPakovanje()).isEqualTo(UPDATED_PAKOVANJE);
        assertThat(testViewVrednovanje.getTrazenaKolicina()).isEqualTo(UPDATED_TRAZENA_KOLICINA);
        assertThat(testViewVrednovanje.getProcijenjenaVrijednost()).isEqualTo(UPDATED_PROCIJENJENA_VRIJEDNOST);
        assertThat(testViewVrednovanje.getPonudjenaVrijednost()).isEqualTo(DEFAULT_PONUDJENA_VRIJEDNOST);
        assertThat(testViewVrednovanje.getRokIsporuke()).isEqualTo(UPDATED_ROK_ISPORUKE);
        assertThat(testViewVrednovanje.getNazivProizvodjaca()).isEqualTo(UPDATED_NAZIV_PROIZVODJACA);
        assertThat(testViewVrednovanje.getNazivPonudjaca()).isEqualTo(DEFAULT_NAZIV_PONUDJACA);
        assertThat(testViewVrednovanje.getBodCijena()).isEqualTo(DEFAULT_BOD_CIJENA);
        assertThat(testViewVrednovanje.getBodRok()).isEqualTo(UPDATED_BOD_ROK);
        assertThat(testViewVrednovanje.getBodUkupno()).isEqualTo(UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void fullUpdateViewVrednovanjeWithPatch() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        int databaseSizeBeforeUpdate = viewVrednovanjeRepository.findAll().size();

        // Update the viewVrednovanje using partial update
        ViewVrednovanje partialUpdatedViewVrednovanje = new ViewVrednovanje();
        partialUpdatedViewVrednovanje.setId(viewVrednovanje.getId());

        partialUpdatedViewVrednovanje
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .sifraPonude(UPDATED_SIFRA_PONUDE)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .atc(UPDATED_ATC)
            .inn(UPDATED_INN)
            .zasticeniNaziv(UPDATED_ZASTICENI_NAZIV)
            .farmaceutskiOblikLijeka(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA)
            .jacinaLijeka(UPDATED_JACINA_LIJEKA)
            .pakovanje(UPDATED_PAKOVANJE)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST)
            .ponudjenaVrijednost(UPDATED_PONUDJENA_VRIJEDNOST)
            .rokIsporuke(UPDATED_ROK_ISPORUKE)
            .nazivProizvodjaca(UPDATED_NAZIV_PROIZVODJACA)
            .nazivPonudjaca(UPDATED_NAZIV_PONUDJACA)
            .bodCijena(UPDATED_BOD_CIJENA)
            .bodRok(UPDATED_BOD_ROK)
            .bodUkupno(UPDATED_BOD_UKUPNO);

        restViewVrednovanjeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedViewVrednovanje.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedViewVrednovanje))
            )
            .andExpect(status().isOk());

        // Validate the ViewVrednovanje in the database
        List<ViewVrednovanje> viewVrednovanjeList = viewVrednovanjeRepository.findAll();
        assertThat(viewVrednovanjeList).hasSize(databaseSizeBeforeUpdate);
        ViewVrednovanje testViewVrednovanje = viewVrednovanjeList.get(viewVrednovanjeList.size() - 1);
        assertThat(testViewVrednovanje.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
        assertThat(testViewVrednovanje.getSifraPonude()).isEqualTo(UPDATED_SIFRA_PONUDE);
        assertThat(testViewVrednovanje.getBrojPartije()).isEqualTo(UPDATED_BROJ_PARTIJE);
        assertThat(testViewVrednovanje.getAtc()).isEqualTo(UPDATED_ATC);
        assertThat(testViewVrednovanje.getInn()).isEqualTo(UPDATED_INN);
        assertThat(testViewVrednovanje.getZasticeniNaziv()).isEqualTo(UPDATED_ZASTICENI_NAZIV);
        assertThat(testViewVrednovanje.getFarmaceutskiOblikLijeka()).isEqualTo(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA);
        assertThat(testViewVrednovanje.getJacinaLijeka()).isEqualTo(UPDATED_JACINA_LIJEKA);
        assertThat(testViewVrednovanje.getPakovanje()).isEqualTo(UPDATED_PAKOVANJE);
        assertThat(testViewVrednovanje.getTrazenaKolicina()).isEqualTo(UPDATED_TRAZENA_KOLICINA);
        assertThat(testViewVrednovanje.getProcijenjenaVrijednost()).isEqualTo(UPDATED_PROCIJENJENA_VRIJEDNOST);
        assertThat(testViewVrednovanje.getPonudjenaVrijednost()).isEqualTo(UPDATED_PONUDJENA_VRIJEDNOST);
        assertThat(testViewVrednovanje.getRokIsporuke()).isEqualTo(UPDATED_ROK_ISPORUKE);
        assertThat(testViewVrednovanje.getNazivProizvodjaca()).isEqualTo(UPDATED_NAZIV_PROIZVODJACA);
        assertThat(testViewVrednovanje.getNazivPonudjaca()).isEqualTo(UPDATED_NAZIV_PONUDJACA);
        assertThat(testViewVrednovanje.getBodCijena()).isEqualTo(UPDATED_BOD_CIJENA);
        assertThat(testViewVrednovanje.getBodRok()).isEqualTo(UPDATED_BOD_ROK);
        assertThat(testViewVrednovanje.getBodUkupno()).isEqualTo(UPDATED_BOD_UKUPNO);
    }

    @Test
    @Transactional
    void patchNonExistingViewVrednovanje() throws Exception {
        int databaseSizeBeforeUpdate = viewVrednovanjeRepository.findAll().size();
        viewVrednovanje.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restViewVrednovanjeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, viewVrednovanje.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(viewVrednovanje))
            )
            .andExpect(status().isBadRequest());

        // Validate the ViewVrednovanje in the database
        List<ViewVrednovanje> viewVrednovanjeList = viewVrednovanjeRepository.findAll();
        assertThat(viewVrednovanjeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchViewVrednovanje() throws Exception {
        int databaseSizeBeforeUpdate = viewVrednovanjeRepository.findAll().size();
        viewVrednovanje.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViewVrednovanjeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(viewVrednovanje))
            )
            .andExpect(status().isBadRequest());

        // Validate the ViewVrednovanje in the database
        List<ViewVrednovanje> viewVrednovanjeList = viewVrednovanjeRepository.findAll();
        assertThat(viewVrednovanjeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamViewVrednovanje() throws Exception {
        int databaseSizeBeforeUpdate = viewVrednovanjeRepository.findAll().size();
        viewVrednovanje.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restViewVrednovanjeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(viewVrednovanje))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ViewVrednovanje in the database
        List<ViewVrednovanje> viewVrednovanjeList = viewVrednovanjeRepository.findAll();
        assertThat(viewVrednovanjeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteViewVrednovanje() throws Exception {
        // Initialize the database
        viewVrednovanjeRepository.saveAndFlush(viewVrednovanje);

        int databaseSizeBeforeDelete = viewVrednovanjeRepository.findAll().size();

        // Delete the viewVrednovanje
        restViewVrednovanjeMockMvc
            .perform(delete(ENTITY_API_URL_ID, viewVrednovanje.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ViewVrednovanje> viewVrednovanjeList = viewVrednovanjeRepository.findAll();
        assertThat(viewVrednovanjeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
