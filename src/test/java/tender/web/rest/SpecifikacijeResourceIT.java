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
import tender.domain.Specifikacije;
import tender.repository.SpecifikacijeRepository;

/**
 * Integration tests for the {@link SpecifikacijeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SpecifikacijeResourceIT {

    private static final Integer DEFAULT_SIFRA_POSTUPKA = 1;
    private static final Integer UPDATED_SIFRA_POSTUPKA = 2;

    private static final Integer DEFAULT_BROJ_PARTIJE = 1;
    private static final Integer UPDATED_BROJ_PARTIJE = 2;

    private static final String DEFAULT_ATC = "AAAAAAAAAA";
    private static final String UPDATED_ATC = "BBBBBBBBBB";

    private static final String DEFAULT_INN = "AAAAAAAAAA";
    private static final String UPDATED_INN = "BBBBBBBBBB";

    private static final String DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA = "AAAAAAAAAA";
    private static final String UPDATED_FARMACEUTSKI_OBLIK_LIJEKA = "BBBBBBBBBB";

    private static final String DEFAULT_JACINA_LIJEKA = "AAAAAAAAAA";
    private static final String UPDATED_JACINA_LIJEKA = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRAZENA_KOLICINA = 1;
    private static final Integer UPDATED_TRAZENA_KOLICINA = 2;

    private static final String DEFAULT_PAKOVANJE = "AAAAAAAAAA";
    private static final String UPDATED_PAKOVANJE = "BBBBBBBBBB";

    private static final String DEFAULT_JEDINICA_MJERE = "AAAAAAAAAA";
    private static final String UPDATED_JEDINICA_MJERE = "BBBBBBBBBB";

    private static final Double DEFAULT_PROCIJENJENA_VRIJEDNOST = 1D;
    private static final Double UPDATED_PROCIJENJENA_VRIJEDNOST = 2D;

    private static final String ENTITY_API_URL = "/api/specifikacijes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SpecifikacijeRepository specifikacijeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpecifikacijeMockMvc;

    private Specifikacije specifikacije;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specifikacije createEntity(EntityManager em) {
        Specifikacije specifikacije = new Specifikacije()
            .sifraPostupka(DEFAULT_SIFRA_POSTUPKA)
            .brojPartije(DEFAULT_BROJ_PARTIJE)
            .atc(DEFAULT_ATC)
            .inn(DEFAULT_INN)
            .farmaceutskiOblikLijeka(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA)
            .jacinaLijeka(DEFAULT_JACINA_LIJEKA)
            .trazenaKolicina(DEFAULT_TRAZENA_KOLICINA)
            .pakovanje(DEFAULT_PAKOVANJE)
            .jedinicaMjere(DEFAULT_JEDINICA_MJERE)
            .procijenjenaVrijednost(DEFAULT_PROCIJENJENA_VRIJEDNOST);
        return specifikacije;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specifikacije createUpdatedEntity(EntityManager em) {
        Specifikacije specifikacije = new Specifikacije()
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .atc(UPDATED_ATC)
            .inn(UPDATED_INN)
            .farmaceutskiOblikLijeka(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA)
            .jacinaLijeka(UPDATED_JACINA_LIJEKA)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .pakovanje(UPDATED_PAKOVANJE)
            .jedinicaMjere(UPDATED_JEDINICA_MJERE)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST);
        return specifikacije;
    }

    @BeforeEach
    public void initTest() {
        specifikacije = createEntity(em);
    }

    @Test
    @Transactional
    void createSpecifikacije() throws Exception {
        int databaseSizeBeforeCreate = specifikacijeRepository.findAll().size();
        // Create the Specifikacije
        restSpecifikacijeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specifikacije)))
            .andExpect(status().isCreated());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeCreate + 1);
        Specifikacije testSpecifikacije = specifikacijeList.get(specifikacijeList.size() - 1);
        assertThat(testSpecifikacije.getSifraPostupka()).isEqualTo(DEFAULT_SIFRA_POSTUPKA);
        assertThat(testSpecifikacije.getBrojPartije()).isEqualTo(DEFAULT_BROJ_PARTIJE);
        assertThat(testSpecifikacije.getAtc()).isEqualTo(DEFAULT_ATC);
        assertThat(testSpecifikacije.getInn()).isEqualTo(DEFAULT_INN);
        assertThat(testSpecifikacije.getFarmaceutskiOblikLijeka()).isEqualTo(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA);
        assertThat(testSpecifikacije.getJacinaLijeka()).isEqualTo(DEFAULT_JACINA_LIJEKA);
        assertThat(testSpecifikacije.getTrazenaKolicina()).isEqualTo(DEFAULT_TRAZENA_KOLICINA);
        assertThat(testSpecifikacije.getPakovanje()).isEqualTo(DEFAULT_PAKOVANJE);
        assertThat(testSpecifikacije.getJedinicaMjere()).isEqualTo(DEFAULT_JEDINICA_MJERE);
        assertThat(testSpecifikacije.getProcijenjenaVrijednost()).isEqualTo(DEFAULT_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void createSpecifikacijeWithExistingId() throws Exception {
        // Create the Specifikacije with an existing ID
        specifikacije.setId(1L);

        int databaseSizeBeforeCreate = specifikacijeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecifikacijeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specifikacije)))
            .andExpect(status().isBadRequest());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSifraPostupkaIsRequired() throws Exception {
        int databaseSizeBeforeTest = specifikacijeRepository.findAll().size();
        // set the field null
        specifikacije.setSifraPostupka(null);

        // Create the Specifikacije, which fails.

        restSpecifikacijeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specifikacije)))
            .andExpect(status().isBadRequest());

        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBrojPartijeIsRequired() throws Exception {
        int databaseSizeBeforeTest = specifikacijeRepository.findAll().size();
        // set the field null
        specifikacije.setBrojPartije(null);

        // Create the Specifikacije, which fails.

        restSpecifikacijeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specifikacije)))
            .andExpect(status().isBadRequest());

        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProcijenjenaVrijednostIsRequired() throws Exception {
        int databaseSizeBeforeTest = specifikacijeRepository.findAll().size();
        // set the field null
        specifikacije.setProcijenjenaVrijednost(null);

        // Create the Specifikacije, which fails.

        restSpecifikacijeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specifikacije)))
            .andExpect(status().isBadRequest());

        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSpecifikacijes() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get all the specifikacijeList
        restSpecifikacijeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specifikacije.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].brojPartije").value(hasItem(DEFAULT_BROJ_PARTIJE)))
            .andExpect(jsonPath("$.[*].atc").value(hasItem(DEFAULT_ATC)))
            .andExpect(jsonPath("$.[*].inn").value(hasItem(DEFAULT_INN)))
            .andExpect(jsonPath("$.[*].farmaceutskiOblikLijeka").value(hasItem(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA)))
            .andExpect(jsonPath("$.[*].jacinaLijeka").value(hasItem(DEFAULT_JACINA_LIJEKA)))
            .andExpect(jsonPath("$.[*].trazenaKolicina").value(hasItem(DEFAULT_TRAZENA_KOLICINA)))
            .andExpect(jsonPath("$.[*].pakovanje").value(hasItem(DEFAULT_PAKOVANJE)))
            .andExpect(jsonPath("$.[*].jedinicaMjere").value(hasItem(DEFAULT_JEDINICA_MJERE)))
            .andExpect(jsonPath("$.[*].procijenjenaVrijednost").value(hasItem(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue())));
    }

    @Test
    @Transactional
    void getSpecifikacije() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        // Get the specifikacije
        restSpecifikacijeMockMvc
            .perform(get(ENTITY_API_URL_ID, specifikacije.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(specifikacije.getId().intValue()))
            .andExpect(jsonPath("$.sifraPostupka").value(DEFAULT_SIFRA_POSTUPKA))
            .andExpect(jsonPath("$.brojPartije").value(DEFAULT_BROJ_PARTIJE))
            .andExpect(jsonPath("$.atc").value(DEFAULT_ATC))
            .andExpect(jsonPath("$.inn").value(DEFAULT_INN))
            .andExpect(jsonPath("$.farmaceutskiOblikLijeka").value(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA))
            .andExpect(jsonPath("$.jacinaLijeka").value(DEFAULT_JACINA_LIJEKA))
            .andExpect(jsonPath("$.trazenaKolicina").value(DEFAULT_TRAZENA_KOLICINA))
            .andExpect(jsonPath("$.pakovanje").value(DEFAULT_PAKOVANJE))
            .andExpect(jsonPath("$.jedinicaMjere").value(DEFAULT_JEDINICA_MJERE))
            .andExpect(jsonPath("$.procijenjenaVrijednost").value(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingSpecifikacije() throws Exception {
        // Get the specifikacije
        restSpecifikacijeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSpecifikacije() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        int databaseSizeBeforeUpdate = specifikacijeRepository.findAll().size();

        // Update the specifikacije
        Specifikacije updatedSpecifikacije = specifikacijeRepository.findById(specifikacije.getId()).get();
        // Disconnect from session so that the updates on updatedSpecifikacije are not directly saved in db
        em.detach(updatedSpecifikacije);
        updatedSpecifikacije
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .atc(UPDATED_ATC)
            .inn(UPDATED_INN)
            .farmaceutskiOblikLijeka(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA)
            .jacinaLijeka(UPDATED_JACINA_LIJEKA)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .pakovanje(UPDATED_PAKOVANJE)
            .jedinicaMjere(UPDATED_JEDINICA_MJERE)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST);

        restSpecifikacijeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSpecifikacije.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSpecifikacije))
            )
            .andExpect(status().isOk());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeUpdate);
        Specifikacije testSpecifikacije = specifikacijeList.get(specifikacijeList.size() - 1);
        assertThat(testSpecifikacije.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
        assertThat(testSpecifikacije.getBrojPartije()).isEqualTo(UPDATED_BROJ_PARTIJE);
        assertThat(testSpecifikacije.getAtc()).isEqualTo(UPDATED_ATC);
        assertThat(testSpecifikacije.getInn()).isEqualTo(UPDATED_INN);
        assertThat(testSpecifikacije.getFarmaceutskiOblikLijeka()).isEqualTo(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA);
        assertThat(testSpecifikacije.getJacinaLijeka()).isEqualTo(UPDATED_JACINA_LIJEKA);
        assertThat(testSpecifikacije.getTrazenaKolicina()).isEqualTo(UPDATED_TRAZENA_KOLICINA);
        assertThat(testSpecifikacije.getPakovanje()).isEqualTo(UPDATED_PAKOVANJE);
        assertThat(testSpecifikacije.getJedinicaMjere()).isEqualTo(UPDATED_JEDINICA_MJERE);
        assertThat(testSpecifikacije.getProcijenjenaVrijednost()).isEqualTo(UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void putNonExistingSpecifikacije() throws Exception {
        int databaseSizeBeforeUpdate = specifikacijeRepository.findAll().size();
        specifikacije.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecifikacijeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, specifikacije.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specifikacije))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSpecifikacije() throws Exception {
        int databaseSizeBeforeUpdate = specifikacijeRepository.findAll().size();
        specifikacije.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecifikacijeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specifikacije))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSpecifikacije() throws Exception {
        int databaseSizeBeforeUpdate = specifikacijeRepository.findAll().size();
        specifikacije.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecifikacijeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specifikacije)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSpecifikacijeWithPatch() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        int databaseSizeBeforeUpdate = specifikacijeRepository.findAll().size();

        // Update the specifikacije using partial update
        Specifikacije partialUpdatedSpecifikacije = new Specifikacije();
        partialUpdatedSpecifikacije.setId(specifikacije.getId());

        partialUpdatedSpecifikacije
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .inn(UPDATED_INN)
            .jacinaLijeka(UPDATED_JACINA_LIJEKA)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .pakovanje(UPDATED_PAKOVANJE)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST);

        restSpecifikacijeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpecifikacije.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpecifikacije))
            )
            .andExpect(status().isOk());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeUpdate);
        Specifikacije testSpecifikacije = specifikacijeList.get(specifikacijeList.size() - 1);
        assertThat(testSpecifikacije.getSifraPostupka()).isEqualTo(DEFAULT_SIFRA_POSTUPKA);
        assertThat(testSpecifikacije.getBrojPartije()).isEqualTo(UPDATED_BROJ_PARTIJE);
        assertThat(testSpecifikacije.getAtc()).isEqualTo(DEFAULT_ATC);
        assertThat(testSpecifikacije.getInn()).isEqualTo(UPDATED_INN);
        assertThat(testSpecifikacije.getFarmaceutskiOblikLijeka()).isEqualTo(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA);
        assertThat(testSpecifikacije.getJacinaLijeka()).isEqualTo(UPDATED_JACINA_LIJEKA);
        assertThat(testSpecifikacije.getTrazenaKolicina()).isEqualTo(UPDATED_TRAZENA_KOLICINA);
        assertThat(testSpecifikacije.getPakovanje()).isEqualTo(UPDATED_PAKOVANJE);
        assertThat(testSpecifikacije.getJedinicaMjere()).isEqualTo(DEFAULT_JEDINICA_MJERE);
        assertThat(testSpecifikacije.getProcijenjenaVrijednost()).isEqualTo(UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void fullUpdateSpecifikacijeWithPatch() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        int databaseSizeBeforeUpdate = specifikacijeRepository.findAll().size();

        // Update the specifikacije using partial update
        Specifikacije partialUpdatedSpecifikacije = new Specifikacije();
        partialUpdatedSpecifikacije.setId(specifikacije.getId());

        partialUpdatedSpecifikacije
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .atc(UPDATED_ATC)
            .inn(UPDATED_INN)
            .farmaceutskiOblikLijeka(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA)
            .jacinaLijeka(UPDATED_JACINA_LIJEKA)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .pakovanje(UPDATED_PAKOVANJE)
            .jedinicaMjere(UPDATED_JEDINICA_MJERE)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST);

        restSpecifikacijeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpecifikacije.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpecifikacije))
            )
            .andExpect(status().isOk());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeUpdate);
        Specifikacije testSpecifikacije = specifikacijeList.get(specifikacijeList.size() - 1);
        assertThat(testSpecifikacije.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
        assertThat(testSpecifikacije.getBrojPartije()).isEqualTo(UPDATED_BROJ_PARTIJE);
        assertThat(testSpecifikacije.getAtc()).isEqualTo(UPDATED_ATC);
        assertThat(testSpecifikacije.getInn()).isEqualTo(UPDATED_INN);
        assertThat(testSpecifikacije.getFarmaceutskiOblikLijeka()).isEqualTo(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA);
        assertThat(testSpecifikacije.getJacinaLijeka()).isEqualTo(UPDATED_JACINA_LIJEKA);
        assertThat(testSpecifikacije.getTrazenaKolicina()).isEqualTo(UPDATED_TRAZENA_KOLICINA);
        assertThat(testSpecifikacije.getPakovanje()).isEqualTo(UPDATED_PAKOVANJE);
        assertThat(testSpecifikacije.getJedinicaMjere()).isEqualTo(UPDATED_JEDINICA_MJERE);
        assertThat(testSpecifikacije.getProcijenjenaVrijednost()).isEqualTo(UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void patchNonExistingSpecifikacije() throws Exception {
        int databaseSizeBeforeUpdate = specifikacijeRepository.findAll().size();
        specifikacije.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecifikacijeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, specifikacije.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(specifikacije))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSpecifikacije() throws Exception {
        int databaseSizeBeforeUpdate = specifikacijeRepository.findAll().size();
        specifikacije.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecifikacijeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(specifikacije))
            )
            .andExpect(status().isBadRequest());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSpecifikacije() throws Exception {
        int databaseSizeBeforeUpdate = specifikacijeRepository.findAll().size();
        specifikacije.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecifikacijeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(specifikacije))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Specifikacije in the database
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSpecifikacije() throws Exception {
        // Initialize the database
        specifikacijeRepository.saveAndFlush(specifikacije);

        int databaseSizeBeforeDelete = specifikacijeRepository.findAll().size();

        // Delete the specifikacije
        restSpecifikacijeMockMvc
            .perform(delete(ENTITY_API_URL_ID, specifikacije.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Specifikacije> specifikacijeList = specifikacijeRepository.findAll();
        assertThat(specifikacijeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
