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
import tender.domain.Ponude;
import tender.domain.Specifikacije;
import tender.repository.PonudeRepository;
import tender.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tender.domain.Ponude}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PonudeResource {

    private final Logger log = LoggerFactory.getLogger(PonudeResource.class);

    private static final String ENTITY_NAME = "ponude";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PonudeRepository ponudeRepository;

    public PonudeResource(PonudeRepository ponudeRepository) {
        this.ponudeRepository = ponudeRepository;
    }

    @PostMapping("/ponudes")
    public ResponseEntity<Ponude> createPonude(@Valid @RequestBody Ponude ponude) throws URISyntaxException {
        log.debug("REST request to save Ponude : {}", ponude);
        if (ponude.getId() != null) {
            throw new BadRequestAlertException("A new ponude cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ponude result = ponudeRepository.save(ponude);
        return ResponseEntity
            .created(new URI("/api/ponudes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ponudes/:id} : Updates an existing ponude.
     *
     * @param id     the id of the ponude to save.
     * @param ponude the ponude to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ponude,
     * or with status {@code 400 (Bad Request)} if the ponude is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ponude couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ponudes/{id}")
    public ResponseEntity<Ponude> updatePonude(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Ponude ponude
    ) throws URISyntaxException {
        log.debug("REST request to update Ponude : {}, {}", id, ponude);
        if (ponude.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ponude.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ponudeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ponude result = ponudeRepository.save(ponude);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ponude.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ponudes/:id} : Partial updates given fields of an existing ponude, field will ignore if it is null
     *
     * @param id     the id of the ponude to save.
     * @param ponude the ponude to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ponude,
     * or with status {@code 400 (Bad Request)} if the ponude is not valid,
     * or with status {@code 404 (Not Found)} if the ponude is not found,
     * or with status {@code 500 (Internal Server Error)} if the ponude couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ponudes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ponude> partialUpdatePonude(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Ponude ponude
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ponude partially : {}, {}", id, ponude);
        if (ponude.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ponude.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ponudeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ponude> result = ponudeRepository
            .findById(ponude.getId())
            .map(existingPonude -> {
                if (ponude.getSifraPostupka() != null) {
                    existingPonude.setSifraPostupka(ponude.getSifraPostupka());
                }
                if (ponude.getSifraPonude() != null) {
                    existingPonude.setSifraPonude(ponude.getSifraPonude());
                }
                if (ponude.getBrojPartije() != null) {
                    existingPonude.setBrojPartije(ponude.getBrojPartije());
                }
                if (ponude.getNazivProizvodjaca() != null) {
                    existingPonude.setNazivProizvodjaca(ponude.getNazivProizvodjaca());
                }
                if (ponude.getZasticeniNaziv() != null) {
                    existingPonude.setZasticeniNaziv(ponude.getZasticeniNaziv());
                }
                if (ponude.getPonudjenaVrijednost() != null) {
                    existingPonude.setPonudjenaVrijednost(ponude.getPonudjenaVrijednost());
                }
                if (ponude.getRokIsporuke() != null) {
                    existingPonude.setRokIsporuke(ponude.getRokIsporuke());
                }
                if (ponude.getSifraPonudjaca() != null) {
                    existingPonude.setSifraPonudjaca(ponude.getSifraPonudjaca());
                }
                if (ponude.getSelected() != null) {
                    existingPonude.setSelected(ponude.getSelected());
                }
                if (ponude.getJedinicnaCijena() != null) {
                    existingPonude.setJedinicnaCijena(ponude.getJedinicnaCijena());
                }

                return existingPonude;
            })
            .map(ponudeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ponude.getId().toString())
        );
    }

    /**
     * {@code GET  /ponudes} : get all the ponudes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ponudes in body.
     */
    @GetMapping("/ponudes")
    public ResponseEntity<List<Ponude>> getAllPonudes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Ponudes");
        Page<Ponude> page = ponudeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ponudes/:id} : get the "id" ponude.
     *
     * @param id the id of the ponude to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ponude, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ponudes/{id}")
    public ResponseEntity<Ponude> getPonude(@PathVariable Long id) {
        log.debug("REST request to get Ponude : {}", id);
        Optional<Ponude> ponude = ponudeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ponude);
    }

    /**
     * {@code DELETE  /ponudes/:id} : delete the "id" ponude.
     *
     * @param id the id of the ponude to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ponudes/{id}")
    public ResponseEntity<Void> deletePonude(@PathVariable Long id) {
        log.debug("REST request to delete Ponude : {}", id);
        ponudeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/ponude/{sifra_postupka}")
    public List<Ponude> getPonude(@PathVariable Integer sifra_postupka) {
        return ponudeRepository.findBySifraPostupka(sifra_postupka);
    }

    @GetMapping("/ponude-sifra-ponude/{sifra_ponude}")
    public List<Ponude> getSifraPonude(@PathVariable Integer sifra_ponude) {
        return ponudeRepository.findBySifraPonude(sifra_ponude);
    }
}
