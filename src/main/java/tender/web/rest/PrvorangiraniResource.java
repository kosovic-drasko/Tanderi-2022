package tender.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import tender.domain.Prvorangirani;
import tender.repository.PrvorangiraniRepository;
import tender.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tender.domain.Prvorangirani}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PrvorangiraniResource {

    private final Logger log = LoggerFactory.getLogger(PrvorangiraniResource.class);

    private static final String ENTITY_NAME = "prvorangirani";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrvorangiraniRepository prvorangiraniRepository;

    public PrvorangiraniResource(PrvorangiraniRepository prvorangiraniRepository) {
        this.prvorangiraniRepository = prvorangiraniRepository;
    }

    /**
     * {@code POST  /prvorangiranis} : Create a new prvorangirani.
     *
     * @param prvorangirani the prvorangirani to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prvorangirani, or with status {@code 400 (Bad Request)} if the prvorangirani has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prvorangiranis")
    public ResponseEntity<Prvorangirani> createPrvorangirani(@RequestBody Prvorangirani prvorangirani) throws URISyntaxException {
        log.debug("REST request to save Prvorangirani : {}", prvorangirani);
        if (prvorangirani.getId() != null) {
            throw new BadRequestAlertException("A new prvorangirani cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prvorangirani result = prvorangiraniRepository.save(prvorangirani);
        return ResponseEntity
            .created(new URI("/api/prvorangiranis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prvorangiranis/:id} : Updates an existing prvorangirani.
     *
     * @param id the id of the prvorangirani to save.
     * @param prvorangirani the prvorangirani to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prvorangirani,
     * or with status {@code 400 (Bad Request)} if the prvorangirani is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prvorangirani couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prvorangiranis/{id}")
    public ResponseEntity<Prvorangirani> updatePrvorangirani(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Prvorangirani prvorangirani
    ) throws URISyntaxException {
        log.debug("REST request to update Prvorangirani : {}, {}", id, prvorangirani);
        if (prvorangirani.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prvorangirani.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prvorangiraniRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Prvorangirani result = prvorangiraniRepository.save(prvorangirani);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prvorangirani.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /prvorangiranis/:id} : Partial updates given fields of an existing prvorangirani, field will ignore if it is null
     *
     * @param id the id of the prvorangirani to save.
     * @param prvorangirani the prvorangirani to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prvorangirani,
     * or with status {@code 400 (Bad Request)} if the prvorangirani is not valid,
     * or with status {@code 404 (Not Found)} if the prvorangirani is not found,
     * or with status {@code 500 (Internal Server Error)} if the prvorangirani couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/prvorangiranis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Prvorangirani> partialUpdatePrvorangirani(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Prvorangirani prvorangirani
    ) throws URISyntaxException {
        log.debug("REST request to partial update Prvorangirani partially : {}, {}", id, prvorangirani);
        if (prvorangirani.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prvorangirani.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prvorangiraniRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Prvorangirani> result = prvorangiraniRepository
            .findById(prvorangirani.getId())
            .map(existingPrvorangirani -> {
                if (prvorangirani.getSifraPostupka() != null) {
                    existingPrvorangirani.setSifraPostupka(prvorangirani.getSifraPostupka());
                }
                if (prvorangirani.getSifraPonude() != null) {
                    existingPrvorangirani.setSifraPonude(prvorangirani.getSifraPonude());
                }
                if (prvorangirani.getBrojPartije() != null) {
                    existingPrvorangirani.setBrojPartije(prvorangirani.getBrojPartije());
                }
                if (prvorangirani.getAtc() != null) {
                    existingPrvorangirani.setAtc(prvorangirani.getAtc());
                }
                if (prvorangirani.getInn() != null) {
                    existingPrvorangirani.setInn(prvorangirani.getInn());
                }
                if (prvorangirani.getZasticeniNaziv() != null) {
                    existingPrvorangirani.setZasticeniNaziv(prvorangirani.getZasticeniNaziv());
                }
                if (prvorangirani.getFarmaceutskiOblikLijeka() != null) {
                    existingPrvorangirani.setFarmaceutskiOblikLijeka(prvorangirani.getFarmaceutskiOblikLijeka());
                }
                if (prvorangirani.getJacinaLijeka() != null) {
                    existingPrvorangirani.setJacinaLijeka(prvorangirani.getJacinaLijeka());
                }
                if (prvorangirani.getPakovanje() != null) {
                    existingPrvorangirani.setPakovanje(prvorangirani.getPakovanje());
                }
                if (prvorangirani.getTrazenaKolicina() != null) {
                    existingPrvorangirani.setTrazenaKolicina(prvorangirani.getTrazenaKolicina());
                }
                if (prvorangirani.getProcijenjenaVrijednost() != null) {
                    existingPrvorangirani.setProcijenjenaVrijednost(prvorangirani.getProcijenjenaVrijednost());
                }
                if (prvorangirani.getPonudjenaVrijednost() != null) {
                    existingPrvorangirani.setPonudjenaVrijednost(prvorangirani.getPonudjenaVrijednost());
                }
                if (prvorangirani.getRokIsporuke() != null) {
                    existingPrvorangirani.setRokIsporuke(prvorangirani.getRokIsporuke());
                }
                if (prvorangirani.getNazivPonudjaca() != null) {
                    existingPrvorangirani.setNazivPonudjaca(prvorangirani.getNazivPonudjaca());
                }
                if (prvorangirani.getBodCijena() != null) {
                    existingPrvorangirani.setBodCijena(prvorangirani.getBodCijena());
                }
                if (prvorangirani.getBodRok() != null) {
                    existingPrvorangirani.setBodRok(prvorangirani.getBodRok());
                }
                if (prvorangirani.getBodUkupno() != null) {
                    existingPrvorangirani.setBodUkupno(prvorangirani.getBodUkupno());
                }

                return existingPrvorangirani;
            })
            .map(prvorangiraniRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prvorangirani.getId().toString())
        );
    }

    /**
     * {@code GET  /prvorangiranis} : get all the prvorangiranis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prvorangiranis in body.
     */
    @GetMapping("/prvorangiranis")
    public ResponseEntity<List<Prvorangirani>> getAllPrvorangiranis(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Prvorangiranis");
        Page<Prvorangirani> page = prvorangiraniRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prvorangiranis/:id} : get the "id" prvorangirani.
     *
     * @param id the id of the prvorangirani to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prvorangirani, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prvorangiranis/{id}")
    public ResponseEntity<Prvorangirani> getPrvorangirani(@PathVariable Long id) {
        log.debug("REST request to get Prvorangirani : {}", id);
        Optional<Prvorangirani> prvorangirani = prvorangiraniRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(prvorangirani);
    }

    /**
     * {@code DELETE  /prvorangiranis/:id} : delete the "id" prvorangirani.
     *
     * @param id the id of the prvorangirani to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prvorangiranis/{id}")
    public ResponseEntity<Void> deletePrvorangirani(@PathVariable Long id) {
        log.debug("REST request to delete Prvorangirani : {}", id);
        prvorangiraniRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/prvorangirani/{sifraPostupka}")
    public List<Prvorangirani> findByPostupakPrvorangirani(@PathVariable Integer sifraPostupka) {
        return prvorangiraniRepository.findBySifraPostupka(sifraPostupka);
    }
}
