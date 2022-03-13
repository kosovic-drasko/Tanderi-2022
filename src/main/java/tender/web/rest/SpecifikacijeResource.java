package tender.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
import tender.domain.Specifikacije;
import tender.repository.SpecifikacijeRepository;
import tender.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tender.domain.Specifikacije}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SpecifikacijeResource {

    private final Logger log = LoggerFactory.getLogger(SpecifikacijeResource.class);

    private static final String ENTITY_NAME = "specifikacije";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecifikacijeRepository specifikacijeRepository;

    public SpecifikacijeResource(SpecifikacijeRepository specifikacijeRepository) {
        this.specifikacijeRepository = specifikacijeRepository;
    }

    /**
     * {@code POST  /specifikacijes} : Create a new specifikacije.
     *
     * @param specifikacije the specifikacije to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new specifikacije, or with status {@code 400 (Bad Request)} if the specifikacije has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @GetMapping("/specifikacija/{sifra_postupka}")
    public List<Specifikacije> getSpecifikacijePostupak(@PathVariable Integer sifra_postupka) {
        return specifikacijeRepository.findBySifraPostupka(sifra_postupka);
    }

    @PostMapping("/specifikacijes")
    public ResponseEntity<Specifikacije> createSpecifikacije(@Valid @RequestBody Specifikacije specifikacije) throws URISyntaxException {
        log.debug("REST request to save Specifikacije : {}", specifikacije);
        if (specifikacije.getId() != null) {
            throw new BadRequestAlertException("A new specifikacije cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Specifikacije result = specifikacijeRepository.save(specifikacije);
        return ResponseEntity
            .created(new URI("/api/specifikacijes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /specifikacijes/:id} : Updates an existing specifikacije.
     *
     * @param id the id of the specifikacije to save.
     * @param specifikacije the specifikacije to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specifikacije,
     * or with status {@code 400 (Bad Request)} if the specifikacije is not valid,
     * or with status {@code 500 (Internal Server Error)} if the specifikacije couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/specifikacijes/{id}")
    public ResponseEntity<Specifikacije> updateSpecifikacije(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Specifikacije specifikacije
    ) throws URISyntaxException {
        log.debug("REST request to update Specifikacije : {}, {}", id, specifikacije);
        if (specifikacije.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specifikacije.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specifikacijeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Specifikacije result = specifikacijeRepository.save(specifikacije);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specifikacije.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /specifikacijes/:id} : Partial updates given fields of an existing specifikacije, field will ignore if it is null
     *
     * @param id the id of the specifikacije to save.
     * @param specifikacije the specifikacije to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specifikacije,
     * or with status {@code 400 (Bad Request)} if the specifikacije is not valid,
     * or with status {@code 404 (Not Found)} if the specifikacije is not found,
     * or with status {@code 500 (Internal Server Error)} if the specifikacije couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/specifikacijes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Specifikacije> partialUpdateSpecifikacije(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Specifikacije specifikacije
    ) throws URISyntaxException {
        log.debug("REST request to partial update Specifikacije partially : {}, {}", id, specifikacije);
        if (specifikacije.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specifikacije.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specifikacijeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Specifikacije> result = specifikacijeRepository
            .findById(specifikacije.getId())
            .map(existingSpecifikacije -> {
                if (specifikacije.getSifraPostupka() != null) {
                    existingSpecifikacije.setSifraPostupka(specifikacije.getSifraPostupka());
                }
                if (specifikacije.getBrojPartije() != null) {
                    existingSpecifikacije.setBrojPartije(specifikacije.getBrojPartije());
                }
                if (specifikacije.getAtc() != null) {
                    existingSpecifikacije.setAtc(specifikacije.getAtc());
                }
                if (specifikacije.getInn() != null) {
                    existingSpecifikacije.setInn(specifikacije.getInn());
                }
                if (specifikacije.getFarmaceutskiOblikLijeka() != null) {
                    existingSpecifikacije.setFarmaceutskiOblikLijeka(specifikacije.getFarmaceutskiOblikLijeka());
                }
                if (specifikacije.getJacinaLijeka() != null) {
                    existingSpecifikacije.setJacinaLijeka(specifikacije.getJacinaLijeka());
                }
                if (specifikacije.getTrazenaKolicina() != null) {
                    existingSpecifikacije.setTrazenaKolicina(specifikacije.getTrazenaKolicina());
                }
                if (specifikacije.getPakovanje() != null) {
                    existingSpecifikacije.setPakovanje(specifikacije.getPakovanje());
                }
                if (specifikacije.getJedinicaMjere() != null) {
                    existingSpecifikacije.setJedinicaMjere(specifikacije.getJedinicaMjere());
                }
                if (specifikacije.getProcijenjenaVrijednost() != null) {
                    existingSpecifikacije.setProcijenjenaVrijednost(specifikacije.getProcijenjenaVrijednost());
                }

                return existingSpecifikacije;
            })
            .map(specifikacijeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specifikacije.getId().toString())
        );
    }

    /**
     * {@code GET  /specifikacijes} : get all the specifikacijes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specifikacijes in body.
     */
    @GetMapping("/specifikacijes")
    public ResponseEntity<List<Specifikacije>> getAllSpecifikacijes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Specifikacijes");
        Page<Specifikacije> page = specifikacijeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /specifikacijes/:id} : get the "id" specifikacije.
     *
     * @param id the id of the specifikacije to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the specifikacije, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/specifikacijes/{id}")
    public ResponseEntity<Specifikacije> getSpecifikacije(@PathVariable Long id) {
        log.debug("REST request to get Specifikacije : {}", id);
        Optional<Specifikacije> specifikacije = specifikacijeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(specifikacije);
    }

    /**
     * {@code DELETE  /specifikacijes/:id} : delete the "id" specifikacije.
     *
     * @param id the id of the specifikacije to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/specifikacijes/{id}")
    public ResponseEntity<Void> deleteSpecifikacije(@PathVariable Long id) {
        log.debug("REST request to delete Specifikacije : {}", id);
        specifikacijeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
