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
import tender.domain.HvalePonude;
import tender.repository.HvalePonudeRepository;

/**
 * Integration tests for the {@link HvalePonudeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HvalePonudeResourceIT {

    private static final Integer DEFAULT_SIFRA_POSTUPKA = 1;
    private static final Integer UPDATED_SIFRA_POSTUPKA = 2;

    private static final Integer DEFAULT_BROJ_PARTIJE = 1;
    private static final Integer UPDATED_BROJ_PARTIJE = 2;

    private static final String DEFAULT_INN = "AAAAAAAAAA";
    private static final String UPDATED_INN = "BBBBBBBBBB";

    private static final String DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA = "AAAAAAAAAA";
    private static final String UPDATED_FARMACEUTSKI_OBLIK_LIJEKA = "BBBBBBBBBB";

    private static final String DEFAULT_PAKOVANJE = "AAAAAAAAAA";
    private static final String UPDATED_PAKOVANJE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRAZENA_KOLICINA = 1;
    private static final Integer UPDATED_TRAZENA_KOLICINA = 2;

    private static final Double DEFAULT_PROCIJENJENA_VRIJEDNOST = 1D;
    private static final Double UPDATED_PROCIJENJENA_VRIJEDNOST = 2D;

    private static final String ENTITY_API_URL = "/api/hvale-ponudes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HvalePonudeRepository hvalePonudeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHvalePonudeMockMvc;

    private HvalePonude hvalePonude;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HvalePonude createEntity(EntityManager em) {
        HvalePonude hvalePonude = new HvalePonude()
            .sifraPostupka(DEFAULT_SIFRA_POSTUPKA)
            .brojPartije(DEFAULT_BROJ_PARTIJE)
            .inn(DEFAULT_INN)
            .farmaceutskiOblikLijeka(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA)
            .pakovanje(DEFAULT_PAKOVANJE)
            .trazenaKolicina(DEFAULT_TRAZENA_KOLICINA)
            .procijenjenaVrijednost(DEFAULT_PROCIJENJENA_VRIJEDNOST);
        return hvalePonude;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HvalePonude createUpdatedEntity(EntityManager em) {
        HvalePonude hvalePonude = new HvalePonude()
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .inn(UPDATED_INN)
            .farmaceutskiOblikLijeka(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA)
            .pakovanje(UPDATED_PAKOVANJE)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST);
        return hvalePonude;
    }

    @BeforeEach
    public void initTest() {
        hvalePonude = createEntity(em);
    }

    @Test
    @Transactional
    void createHvalePonude() throws Exception {
        int databaseSizeBeforeCreate = hvalePonudeRepository.findAll().size();
        // Create the HvalePonude
        restHvalePonudeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hvalePonude)))
            .andExpect(status().isCreated());

        // Validate the HvalePonude in the database
        List<HvalePonude> hvalePonudeList = hvalePonudeRepository.findAll();
        assertThat(hvalePonudeList).hasSize(databaseSizeBeforeCreate + 1);
        HvalePonude testHvalePonude = hvalePonudeList.get(hvalePonudeList.size() - 1);
        assertThat(testHvalePonude.getSifraPostupka()).isEqualTo(DEFAULT_SIFRA_POSTUPKA);
        assertThat(testHvalePonude.getBrojPartije()).isEqualTo(DEFAULT_BROJ_PARTIJE);
        assertThat(testHvalePonude.getInn()).isEqualTo(DEFAULT_INN);
        assertThat(testHvalePonude.getFarmaceutskiOblikLijeka()).isEqualTo(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA);
        assertThat(testHvalePonude.getPakovanje()).isEqualTo(DEFAULT_PAKOVANJE);
        assertThat(testHvalePonude.getTrazenaKolicina()).isEqualTo(DEFAULT_TRAZENA_KOLICINA);
        assertThat(testHvalePonude.getProcijenjenaVrijednost()).isEqualTo(DEFAULT_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void createHvalePonudeWithExistingId() throws Exception {
        // Create the HvalePonude with an existing ID
        hvalePonude.setId(1L);

        int databaseSizeBeforeCreate = hvalePonudeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHvalePonudeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hvalePonude)))
            .andExpect(status().isBadRequest());

        // Validate the HvalePonude in the database
        List<HvalePonude> hvalePonudeList = hvalePonudeRepository.findAll();
        assertThat(hvalePonudeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHvalePonudes() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get all the hvalePonudeList
        restHvalePonudeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hvalePonude.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)))
            .andExpect(jsonPath("$.[*].brojPartije").value(hasItem(DEFAULT_BROJ_PARTIJE)))
            .andExpect(jsonPath("$.[*].inn").value(hasItem(DEFAULT_INN)))
            .andExpect(jsonPath("$.[*].farmaceutskiOblikLijeka").value(hasItem(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA)))
            .andExpect(jsonPath("$.[*].pakovanje").value(hasItem(DEFAULT_PAKOVANJE)))
            .andExpect(jsonPath("$.[*].trazenaKolicina").value(hasItem(DEFAULT_TRAZENA_KOLICINA)))
            .andExpect(jsonPath("$.[*].procijenjenaVrijednost").value(hasItem(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue())));
    }

    @Test
    @Transactional
    void getHvalePonude() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        // Get the hvalePonude
        restHvalePonudeMockMvc
            .perform(get(ENTITY_API_URL_ID, hvalePonude.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hvalePonude.getId().intValue()))
            .andExpect(jsonPath("$.sifraPostupka").value(DEFAULT_SIFRA_POSTUPKA))
            .andExpect(jsonPath("$.brojPartije").value(DEFAULT_BROJ_PARTIJE))
            .andExpect(jsonPath("$.inn").value(DEFAULT_INN))
            .andExpect(jsonPath("$.farmaceutskiOblikLijeka").value(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA))
            .andExpect(jsonPath("$.pakovanje").value(DEFAULT_PAKOVANJE))
            .andExpect(jsonPath("$.trazenaKolicina").value(DEFAULT_TRAZENA_KOLICINA))
            .andExpect(jsonPath("$.procijenjenaVrijednost").value(DEFAULT_PROCIJENJENA_VRIJEDNOST.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingHvalePonude() throws Exception {
        // Get the hvalePonude
        restHvalePonudeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHvalePonude() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        int databaseSizeBeforeUpdate = hvalePonudeRepository.findAll().size();

        // Update the hvalePonude
        HvalePonude updatedHvalePonude = hvalePonudeRepository.findById(hvalePonude.getId()).get();
        // Disconnect from session so that the updates on updatedHvalePonude are not directly saved in db
        em.detach(updatedHvalePonude);
        updatedHvalePonude
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .inn(UPDATED_INN)
            .farmaceutskiOblikLijeka(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA)
            .pakovanje(UPDATED_PAKOVANJE)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST);

        restHvalePonudeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHvalePonude.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHvalePonude))
            )
            .andExpect(status().isOk());

        // Validate the HvalePonude in the database
        List<HvalePonude> hvalePonudeList = hvalePonudeRepository.findAll();
        assertThat(hvalePonudeList).hasSize(databaseSizeBeforeUpdate);
        HvalePonude testHvalePonude = hvalePonudeList.get(hvalePonudeList.size() - 1);
        assertThat(testHvalePonude.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
        assertThat(testHvalePonude.getBrojPartije()).isEqualTo(UPDATED_BROJ_PARTIJE);
        assertThat(testHvalePonude.getInn()).isEqualTo(UPDATED_INN);
        assertThat(testHvalePonude.getFarmaceutskiOblikLijeka()).isEqualTo(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA);
        assertThat(testHvalePonude.getPakovanje()).isEqualTo(UPDATED_PAKOVANJE);
        assertThat(testHvalePonude.getTrazenaKolicina()).isEqualTo(UPDATED_TRAZENA_KOLICINA);
        assertThat(testHvalePonude.getProcijenjenaVrijednost()).isEqualTo(UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void putNonExistingHvalePonude() throws Exception {
        int databaseSizeBeforeUpdate = hvalePonudeRepository.findAll().size();
        hvalePonude.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHvalePonudeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hvalePonude.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hvalePonude))
            )
            .andExpect(status().isBadRequest());

        // Validate the HvalePonude in the database
        List<HvalePonude> hvalePonudeList = hvalePonudeRepository.findAll();
        assertThat(hvalePonudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHvalePonude() throws Exception {
        int databaseSizeBeforeUpdate = hvalePonudeRepository.findAll().size();
        hvalePonude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHvalePonudeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hvalePonude))
            )
            .andExpect(status().isBadRequest());

        // Validate the HvalePonude in the database
        List<HvalePonude> hvalePonudeList = hvalePonudeRepository.findAll();
        assertThat(hvalePonudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHvalePonude() throws Exception {
        int databaseSizeBeforeUpdate = hvalePonudeRepository.findAll().size();
        hvalePonude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHvalePonudeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hvalePonude)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HvalePonude in the database
        List<HvalePonude> hvalePonudeList = hvalePonudeRepository.findAll();
        assertThat(hvalePonudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHvalePonudeWithPatch() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        int databaseSizeBeforeUpdate = hvalePonudeRepository.findAll().size();

        // Update the hvalePonude using partial update
        HvalePonude partialUpdatedHvalePonude = new HvalePonude();
        partialUpdatedHvalePonude.setId(hvalePonude.getId());

        partialUpdatedHvalePonude
            .pakovanje(UPDATED_PAKOVANJE)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST);

        restHvalePonudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHvalePonude.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHvalePonude))
            )
            .andExpect(status().isOk());

        // Validate the HvalePonude in the database
        List<HvalePonude> hvalePonudeList = hvalePonudeRepository.findAll();
        assertThat(hvalePonudeList).hasSize(databaseSizeBeforeUpdate);
        HvalePonude testHvalePonude = hvalePonudeList.get(hvalePonudeList.size() - 1);
        assertThat(testHvalePonude.getSifraPostupka()).isEqualTo(DEFAULT_SIFRA_POSTUPKA);
        assertThat(testHvalePonude.getBrojPartije()).isEqualTo(DEFAULT_BROJ_PARTIJE);
        assertThat(testHvalePonude.getInn()).isEqualTo(DEFAULT_INN);
        assertThat(testHvalePonude.getFarmaceutskiOblikLijeka()).isEqualTo(DEFAULT_FARMACEUTSKI_OBLIK_LIJEKA);
        assertThat(testHvalePonude.getPakovanje()).isEqualTo(UPDATED_PAKOVANJE);
        assertThat(testHvalePonude.getTrazenaKolicina()).isEqualTo(UPDATED_TRAZENA_KOLICINA);
        assertThat(testHvalePonude.getProcijenjenaVrijednost()).isEqualTo(UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void fullUpdateHvalePonudeWithPatch() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        int databaseSizeBeforeUpdate = hvalePonudeRepository.findAll().size();

        // Update the hvalePonude using partial update
        HvalePonude partialUpdatedHvalePonude = new HvalePonude();
        partialUpdatedHvalePonude.setId(hvalePonude.getId());

        partialUpdatedHvalePonude
            .sifraPostupka(UPDATED_SIFRA_POSTUPKA)
            .brojPartije(UPDATED_BROJ_PARTIJE)
            .inn(UPDATED_INN)
            .farmaceutskiOblikLijeka(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA)
            .pakovanje(UPDATED_PAKOVANJE)
            .trazenaKolicina(UPDATED_TRAZENA_KOLICINA)
            .procijenjenaVrijednost(UPDATED_PROCIJENJENA_VRIJEDNOST);

        restHvalePonudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHvalePonude.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHvalePonude))
            )
            .andExpect(status().isOk());

        // Validate the HvalePonude in the database
        List<HvalePonude> hvalePonudeList = hvalePonudeRepository.findAll();
        assertThat(hvalePonudeList).hasSize(databaseSizeBeforeUpdate);
        HvalePonude testHvalePonude = hvalePonudeList.get(hvalePonudeList.size() - 1);
        assertThat(testHvalePonude.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
        assertThat(testHvalePonude.getBrojPartije()).isEqualTo(UPDATED_BROJ_PARTIJE);
        assertThat(testHvalePonude.getInn()).isEqualTo(UPDATED_INN);
        assertThat(testHvalePonude.getFarmaceutskiOblikLijeka()).isEqualTo(UPDATED_FARMACEUTSKI_OBLIK_LIJEKA);
        assertThat(testHvalePonude.getPakovanje()).isEqualTo(UPDATED_PAKOVANJE);
        assertThat(testHvalePonude.getTrazenaKolicina()).isEqualTo(UPDATED_TRAZENA_KOLICINA);
        assertThat(testHvalePonude.getProcijenjenaVrijednost()).isEqualTo(UPDATED_PROCIJENJENA_VRIJEDNOST);
    }

    @Test
    @Transactional
    void patchNonExistingHvalePonude() throws Exception {
        int databaseSizeBeforeUpdate = hvalePonudeRepository.findAll().size();
        hvalePonude.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHvalePonudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hvalePonude.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hvalePonude))
            )
            .andExpect(status().isBadRequest());

        // Validate the HvalePonude in the database
        List<HvalePonude> hvalePonudeList = hvalePonudeRepository.findAll();
        assertThat(hvalePonudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHvalePonude() throws Exception {
        int databaseSizeBeforeUpdate = hvalePonudeRepository.findAll().size();
        hvalePonude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHvalePonudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hvalePonude))
            )
            .andExpect(status().isBadRequest());

        // Validate the HvalePonude in the database
        List<HvalePonude> hvalePonudeList = hvalePonudeRepository.findAll();
        assertThat(hvalePonudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHvalePonude() throws Exception {
        int databaseSizeBeforeUpdate = hvalePonudeRepository.findAll().size();
        hvalePonude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHvalePonudeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hvalePonude))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HvalePonude in the database
        List<HvalePonude> hvalePonudeList = hvalePonudeRepository.findAll();
        assertThat(hvalePonudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHvalePonude() throws Exception {
        // Initialize the database
        hvalePonudeRepository.saveAndFlush(hvalePonude);

        int databaseSizeBeforeDelete = hvalePonudeRepository.findAll().size();

        // Delete the hvalePonude
        restHvalePonudeMockMvc
            .perform(delete(ENTITY_API_URL_ID, hvalePonude.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HvalePonude> hvalePonudeList = hvalePonudeRepository.findAll();
        assertThat(hvalePonudeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
