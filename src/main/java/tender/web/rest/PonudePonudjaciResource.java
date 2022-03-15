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
import tender.domain.PonudePonudjaci;
import tender.repository.PonudePonudjaciRepository;
import tender.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tender.domain.PonudePonudjaci}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PonudePonudjaciResource {

    private final Logger log = LoggerFactory.getLogger(PonudePonudjaciResource.class);

    private static final String ENTITY_NAME = "ponudePonudjaci";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PonudePonudjaciRepository ponudePonudjaciRepository;

    public PonudePonudjaciResource(PonudePonudjaciRepository ponudePonudjaciRepository) {
        this.ponudePonudjaciRepository = ponudePonudjaciRepository;
    }

    /**
     * {@code POST  /ponude-ponudjacis} : Create a new ponudePonudjaci.
     *
     * @param ponudePonudjaci the ponudePonudjaci to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ponudePonudjaci, or with status {@code 400 (Bad Request)} if the ponudePonudjaci has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ponude-ponudjacis")
    public ResponseEntity<PonudePonudjaci> createPonudePonudjaci(@RequestBody PonudePonudjaci ponudePonudjaci) throws URISyntaxException {
        log.debug("REST request to save PonudePonudjaci : {}", ponudePonudjaci);
        if (ponudePonudjaci.getId() != null) {
            throw new BadRequestAlertException("A new ponudePonudjaci cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PonudePonudjaci result = ponudePonudjaciRepository.save(ponudePonudjaci);
        return ResponseEntity
            .created(new URI("/api/ponude-ponudjacis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ponude-ponudjacis/:id} : Updates an existing ponudePonudjaci.
     *
     * @param id the id of the ponudePonudjaci to save.
     * @param ponudePonudjaci the ponudePonudjaci to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ponudePonudjaci,
     * or with status {@code 400 (Bad Request)} if the ponudePonudjaci is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ponudePonudjaci couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ponude-ponudjacis/{id}")
    public ResponseEntity<PonudePonudjaci> updatePonudePonudjaci(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PonudePonudjaci ponudePonudjaci
    ) throws URISyntaxException {
        log.debug("REST request to update PonudePonudjaci : {}, {}", id, ponudePonudjaci);
        if (ponudePonudjaci.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ponudePonudjaci.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ponudePonudjaciRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PonudePonudjaci result = ponudePonudjaciRepository.save(ponudePonudjaci);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ponudePonudjaci.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ponude-ponudjacis/:id} : Partial updates given fields of an existing ponudePonudjaci, field will ignore if it is null
     *
     * @param id the id of the ponudePonudjaci to save.
     * @param ponudePonudjaci the ponudePonudjaci to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ponudePonudjaci,
     * or with status {@code 400 (Bad Request)} if the ponudePonudjaci is not valid,
     * or with status {@code 404 (Not Found)} if the ponudePonudjaci is not found,
     * or with status {@code 500 (Internal Server Error)} if the ponudePonudjaci couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ponude-ponudjacis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PonudePonudjaci> partialUpdatePonudePonudjaci(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PonudePonudjaci ponudePonudjaci
    ) throws URISyntaxException {
        log.debug("REST request to partial update PonudePonudjaci partially : {}, {}", id, ponudePonudjaci);
        if (ponudePonudjaci.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ponudePonudjaci.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ponudePonudjaciRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PonudePonudjaci> result = ponudePonudjaciRepository
            .findById(ponudePonudjaci.getId())
            .map(existingPonudePonudjaci -> {
                if (ponudePonudjaci.getSifraPostupka() != null) {
                    existingPonudePonudjaci.setSifraPostupka(ponudePonudjaci.getSifraPostupka());
                }

                return existingPonudePonudjaci;
            })
            .map(ponudePonudjaciRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ponudePonudjaci.getId().toString())
        );
    }

    /**
     * {@code GET  /ponude-ponudjacis} : get all the ponudePonudjacis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ponudePonudjacis in body.
     */
    @GetMapping("/ponude-ponudjacis")
    public ResponseEntity<List<PonudePonudjaci>> getAllPonudePonudjacis(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PonudePonudjacis");
        Page<PonudePonudjaci> page = ponudePonudjaciRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ponude-ponudjacis/:id} : get the "id" ponudePonudjaci.
     *
     * @param id the id of the ponudePonudjaci to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ponudePonudjaci, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ponude-ponudjacis/{id}")
    public ResponseEntity<PonudePonudjaci> getPonudePonudjaci(@PathVariable Long id) {
        log.debug("REST request to get PonudePonudjaci : {}", id);
        Optional<PonudePonudjaci> ponudePonudjaci = ponudePonudjaciRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ponudePonudjaci);
    }

    /**
     * {@code DELETE  /ponude-ponudjacis/:id} : delete the "id" ponudePonudjaci.
     *
     * @param id the id of the ponudePonudjaci to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ponude-ponudjacis/{id}")
    public ResponseEntity<Void> deletePonudePonudjaci(@PathVariable Long id) {
        log.debug("REST request to delete PonudePonudjaci : {}", id);
        ponudePonudjaciRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/ponude_ponudjaci/{sifraPostupka}")
    public List<PonudePonudjaci> allPonudjaciPonudeSifraPostupka(@PathVariable Integer sifraPostupka) {
        return ponudePonudjaciRepository.findBySifraPostupka(sifraPostupka);
    }
}
