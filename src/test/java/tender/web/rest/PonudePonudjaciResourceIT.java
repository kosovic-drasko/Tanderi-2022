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
import tender.domain.PonudePonudjaci;
import tender.repository.PonudePonudjaciRepository;

/**
 * Integration tests for the {@link PonudePonudjaciResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PonudePonudjaciResourceIT {

    private static final Integer DEFAULT_SIFRA_POSTUPKA = 1;
    private static final Integer UPDATED_SIFRA_POSTUPKA = 2;

    private static final String ENTITY_API_URL = "/api/ponude-ponudjacis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PonudePonudjaciRepository ponudePonudjaciRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPonudePonudjaciMockMvc;

    private PonudePonudjaci ponudePonudjaci;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PonudePonudjaci createEntity(EntityManager em) {
        PonudePonudjaci ponudePonudjaci = new PonudePonudjaci().sifraPostupka(DEFAULT_SIFRA_POSTUPKA);
        return ponudePonudjaci;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PonudePonudjaci createUpdatedEntity(EntityManager em) {
        PonudePonudjaci ponudePonudjaci = new PonudePonudjaci().sifraPostupka(UPDATED_SIFRA_POSTUPKA);
        return ponudePonudjaci;
    }

    @BeforeEach
    public void initTest() {
        ponudePonudjaci = createEntity(em);
    }

    @Test
    @Transactional
    void createPonudePonudjaci() throws Exception {
        int databaseSizeBeforeCreate = ponudePonudjaciRepository.findAll().size();
        // Create the PonudePonudjaci
        restPonudePonudjaciMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isCreated());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeCreate + 1);
        PonudePonudjaci testPonudePonudjaci = ponudePonudjaciList.get(ponudePonudjaciList.size() - 1);
        assertThat(testPonudePonudjaci.getSifraPostupka()).isEqualTo(DEFAULT_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void createPonudePonudjaciWithExistingId() throws Exception {
        // Create the PonudePonudjaci with an existing ID
        ponudePonudjaci.setId(1L);

        int databaseSizeBeforeCreate = ponudePonudjaciRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPonudePonudjaciMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isBadRequest());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPonudePonudjacis() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get all the ponudePonudjaciList
        restPonudePonudjaciMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ponudePonudjaci.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifraPostupka").value(hasItem(DEFAULT_SIFRA_POSTUPKA)));
    }

    @Test
    @Transactional
    void getPonudePonudjaci() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        // Get the ponudePonudjaci
        restPonudePonudjaciMockMvc
            .perform(get(ENTITY_API_URL_ID, ponudePonudjaci.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ponudePonudjaci.getId().intValue()))
            .andExpect(jsonPath("$.sifraPostupka").value(DEFAULT_SIFRA_POSTUPKA));
    }

    @Test
    @Transactional
    void getNonExistingPonudePonudjaci() throws Exception {
        // Get the ponudePonudjaci
        restPonudePonudjaciMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPonudePonudjaci() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        int databaseSizeBeforeUpdate = ponudePonudjaciRepository.findAll().size();

        // Update the ponudePonudjaci
        PonudePonudjaci updatedPonudePonudjaci = ponudePonudjaciRepository.findById(ponudePonudjaci.getId()).get();
        // Disconnect from session so that the updates on updatedPonudePonudjaci are not directly saved in db
        em.detach(updatedPonudePonudjaci);
        updatedPonudePonudjaci.sifraPostupka(UPDATED_SIFRA_POSTUPKA);

        restPonudePonudjaciMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPonudePonudjaci.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPonudePonudjaci))
            )
            .andExpect(status().isOk());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeUpdate);
        PonudePonudjaci testPonudePonudjaci = ponudePonudjaciList.get(ponudePonudjaciList.size() - 1);
        assertThat(testPonudePonudjaci.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void putNonExistingPonudePonudjaci() throws Exception {
        int databaseSizeBeforeUpdate = ponudePonudjaciRepository.findAll().size();
        ponudePonudjaci.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPonudePonudjaciMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ponudePonudjaci.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isBadRequest());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPonudePonudjaci() throws Exception {
        int databaseSizeBeforeUpdate = ponudePonudjaciRepository.findAll().size();
        ponudePonudjaci.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPonudePonudjaciMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isBadRequest());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPonudePonudjaci() throws Exception {
        int databaseSizeBeforeUpdate = ponudePonudjaciRepository.findAll().size();
        ponudePonudjaci.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPonudePonudjaciMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePonudePonudjaciWithPatch() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        int databaseSizeBeforeUpdate = ponudePonudjaciRepository.findAll().size();

        // Update the ponudePonudjaci using partial update
        PonudePonudjaci partialUpdatedPonudePonudjaci = new PonudePonudjaci();
        partialUpdatedPonudePonudjaci.setId(ponudePonudjaci.getId());

        restPonudePonudjaciMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPonudePonudjaci.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPonudePonudjaci))
            )
            .andExpect(status().isOk());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeUpdate);
        PonudePonudjaci testPonudePonudjaci = ponudePonudjaciList.get(ponudePonudjaciList.size() - 1);
        assertThat(testPonudePonudjaci.getSifraPostupka()).isEqualTo(DEFAULT_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void fullUpdatePonudePonudjaciWithPatch() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        int databaseSizeBeforeUpdate = ponudePonudjaciRepository.findAll().size();

        // Update the ponudePonudjaci using partial update
        PonudePonudjaci partialUpdatedPonudePonudjaci = new PonudePonudjaci();
        partialUpdatedPonudePonudjaci.setId(ponudePonudjaci.getId());

        partialUpdatedPonudePonudjaci.sifraPostupka(UPDATED_SIFRA_POSTUPKA);

        restPonudePonudjaciMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPonudePonudjaci.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPonudePonudjaci))
            )
            .andExpect(status().isOk());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeUpdate);
        PonudePonudjaci testPonudePonudjaci = ponudePonudjaciList.get(ponudePonudjaciList.size() - 1);
        assertThat(testPonudePonudjaci.getSifraPostupka()).isEqualTo(UPDATED_SIFRA_POSTUPKA);
    }

    @Test
    @Transactional
    void patchNonExistingPonudePonudjaci() throws Exception {
        int databaseSizeBeforeUpdate = ponudePonudjaciRepository.findAll().size();
        ponudePonudjaci.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPonudePonudjaciMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ponudePonudjaci.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isBadRequest());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPonudePonudjaci() throws Exception {
        int databaseSizeBeforeUpdate = ponudePonudjaciRepository.findAll().size();
        ponudePonudjaci.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPonudePonudjaciMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isBadRequest());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPonudePonudjaci() throws Exception {
        int databaseSizeBeforeUpdate = ponudePonudjaciRepository.findAll().size();
        ponudePonudjaci.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPonudePonudjaciMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ponudePonudjaci))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PonudePonudjaci in the database
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePonudePonudjaci() throws Exception {
        // Initialize the database
        ponudePonudjaciRepository.saveAndFlush(ponudePonudjaci);

        int databaseSizeBeforeDelete = ponudePonudjaciRepository.findAll().size();

        // Delete the ponudePonudjaci
        restPonudePonudjaciMockMvc
            .perform(delete(ENTITY_API_URL_ID, ponudePonudjaci.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PonudePonudjaci> ponudePonudjaciList = ponudePonudjaciRepository.findAll();
        assertThat(ponudePonudjaciList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
