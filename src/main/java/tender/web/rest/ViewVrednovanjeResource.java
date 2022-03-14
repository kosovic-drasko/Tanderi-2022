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
import tender.domain.ViewVrednovanje;
import tender.repository.ViewVrednovanjeRepository;
import tender.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tender.domain.ViewVrednovanje}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ViewVrednovanjeResource {

    private final Logger log = LoggerFactory.getLogger(ViewVrednovanjeResource.class);

    private static final String ENTITY_NAME = "viewVrednovanje";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ViewVrednovanjeRepository viewVrednovanjeRepository;

    public ViewVrednovanjeResource(ViewVrednovanjeRepository viewVrednovanjeRepository) {
        this.viewVrednovanjeRepository = viewVrednovanjeRepository;
    }

    /**
     * {@code POST  /view-vrednovanjes} : Create a new viewVrednovanje.
     *
     * @param viewVrednovanje the viewVrednovanje to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new viewVrednovanje, or with status {@code 400 (Bad Request)} if the viewVrednovanje has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/view-vrednovanjes")
    public ResponseEntity<ViewVrednovanje> createViewVrednovanje(@RequestBody ViewVrednovanje viewVrednovanje) throws URISyntaxException {
        log.debug("REST request to save ViewVrednovanje : {}", viewVrednovanje);
        if (viewVrednovanje.getId() != null) {
            throw new BadRequestAlertException("A new viewVrednovanje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ViewVrednovanje result = viewVrednovanjeRepository.save(viewVrednovanje);
        return ResponseEntity
            .created(new URI("/api/view-vrednovanjes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /view-vrednovanjes/:id} : Updates an existing viewVrednovanje.
     *
     * @param id              the id of the viewVrednovanje to save.
     * @param viewVrednovanje the viewVrednovanje to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viewVrednovanje,
     * or with status {@code 400 (Bad Request)} if the viewVrednovanje is not valid,
     * or with status {@code 500 (Internal Server Error)} if the viewVrednovanje couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/view-vrednovanjes/{id}")
    public ResponseEntity<ViewVrednovanje> updateViewVrednovanje(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ViewVrednovanje viewVrednovanje
    ) throws URISyntaxException {
        log.debug("REST request to update ViewVrednovanje : {}, {}", id, viewVrednovanje);
        if (viewVrednovanje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, viewVrednovanje.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!viewVrednovanjeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ViewVrednovanje result = viewVrednovanjeRepository.save(viewVrednovanje);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, viewVrednovanje.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /view-vrednovanjes/:id} : Partial updates given fields of an existing viewVrednovanje, field will ignore if it is null
     *
     * @param id              the id of the viewVrednovanje to save.
     * @param viewVrednovanje the viewVrednovanje to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viewVrednovanje,
     * or with status {@code 400 (Bad Request)} if the viewVrednovanje is not valid,
     * or with status {@code 404 (Not Found)} if the viewVrednovanje is not found,
     * or with status {@code 500 (Internal Server Error)} if the viewVrednovanje couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/view-vrednovanjes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ViewVrednovanje> partialUpdateViewVrednovanje(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ViewVrednovanje viewVrednovanje
    ) throws URISyntaxException {
        log.debug("REST request to partial update ViewVrednovanje partially : {}, {}", id, viewVrednovanje);
        if (viewVrednovanje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, viewVrednovanje.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!viewVrednovanjeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ViewVrednovanje> result = viewVrednovanjeRepository
            .findById(viewVrednovanje.getId())
            .map(existingViewVrednovanje -> {
                if (viewVrednovanje.getSifraPostupka() != null) {
                    existingViewVrednovanje.setSifraPostupka(viewVrednovanje.getSifraPostupka());
                }
                if (viewVrednovanje.getSifraPonude() != null) {
                    existingViewVrednovanje.setSifraPonude(viewVrednovanje.getSifraPonude());
                }
                if (viewVrednovanje.getBrojPartije() != null) {
                    existingViewVrednovanje.setBrojPartije(viewVrednovanje.getBrojPartije());
                }
                if (viewVrednovanje.getAtc() != null) {
                    existingViewVrednovanje.setAtc(viewVrednovanje.getAtc());
                }
                if (viewVrednovanje.getInn() != null) {
                    existingViewVrednovanje.setInn(viewVrednovanje.getInn());
                }
                if (viewVrednovanje.getZasticeniNaziv() != null) {
                    existingViewVrednovanje.setZasticeniNaziv(viewVrednovanje.getZasticeniNaziv());
                }
                if (viewVrednovanje.getFarmaceutskiOblikLijeka() != null) {
                    existingViewVrednovanje.setFarmaceutskiOblikLijeka(viewVrednovanje.getFarmaceutskiOblikLijeka());
                }
                if (viewVrednovanje.getJacinaLijeka() != null) {
                    existingViewVrednovanje.setJacinaLijeka(viewVrednovanje.getJacinaLijeka());
                }
                if (viewVrednovanje.getPakovanje() != null) {
                    existingViewVrednovanje.setPakovanje(viewVrednovanje.getPakovanje());
                }
                if (viewVrednovanje.getTrazenaKolicina() != null) {
                    existingViewVrednovanje.setTrazenaKolicina(viewVrednovanje.getTrazenaKolicina());
                }
                if (viewVrednovanje.getProcijenjenaVrijednost() != null) {
                    existingViewVrednovanje.setProcijenjenaVrijednost(viewVrednovanje.getProcijenjenaVrijednost());
                }
                if (viewVrednovanje.getPonudjenaVrijednost() != null) {
                    existingViewVrednovanje.setPonudjenaVrijednost(viewVrednovanje.getPonudjenaVrijednost());
                }
                if (viewVrednovanje.getRokIsporuke() != null) {
                    existingViewVrednovanje.setRokIsporuke(viewVrednovanje.getRokIsporuke());
                }
                if (viewVrednovanje.getNazivProizvodjaca() != null) {
                    existingViewVrednovanje.setNazivProizvodjaca(viewVrednovanje.getNazivProizvodjaca());
                }
                if (viewVrednovanje.getNazivPonudjaca() != null) {
                    existingViewVrednovanje.setNazivPonudjaca(viewVrednovanje.getNazivPonudjaca());
                }
                if (viewVrednovanje.getBodCijena() != null) {
                    existingViewVrednovanje.setBodCijena(viewVrednovanje.getBodCijena());
                }
                if (viewVrednovanje.getBodRok() != null) {
                    existingViewVrednovanje.setBodRok(viewVrednovanje.getBodRok());
                }
                if (viewVrednovanje.getBodUkupno() != null) {
                    existingViewVrednovanje.setBodUkupno(viewVrednovanje.getBodUkupno());
                }

                return existingViewVrednovanje;
            })
            .map(viewVrednovanjeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, viewVrednovanje.getId().toString())
        );
    }

    /**
     * {@code GET  /view-vrednovanjes} : get all the viewVrednovanjes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of viewVrednovanjes in body.
     */
    @GetMapping("/view-vrednovanjes")
    public ResponseEntity<List<ViewVrednovanje>> getAllViewVrednovanjes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ViewVrednovanjes");
        Page<ViewVrednovanje> page = viewVrednovanjeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /view-vrednovanjes/:id} : get the "id" viewVrednovanje.
     *
     * @param id the id of the viewVrednovanje to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the viewVrednovanje, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/view-vrednovanjes/{id}")
    public ResponseEntity<ViewVrednovanje> getViewVrednovanje(@PathVariable Long id) {
        log.debug("REST request to get ViewVrednovanje : {}", id);
        Optional<ViewVrednovanje> viewVrednovanje = viewVrednovanjeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(viewVrednovanje);
    }

    /**
     * {@code DELETE  /view-vrednovanjes/:id} : delete the "id" viewVrednovanje.
     *
     * @param id the id of the viewVrednovanje to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/view-vrednovanjes/{id}")
    public ResponseEntity<Void> deleteViewVrednovanje(@PathVariable Long id) {
        log.debug("REST request to delete ViewVrednovanje : {}", id);
        viewVrednovanjeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/vrednovanje/{sifraPostupka}")
    public List<ViewVrednovanje> getViewVrednovanje(@PathVariable Integer sifraPostupka) {
        return viewVrednovanjeRepository.findBySifraPostupka(sifraPostupka);
    }
}
