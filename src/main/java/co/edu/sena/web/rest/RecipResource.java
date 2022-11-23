package co.edu.sena.web.rest;

import co.edu.sena.repository.RecipRepository;
import co.edu.sena.service.RecipService;
import co.edu.sena.service.dto.RecipDTO;
import co.edu.sena.web.rest.errors.BadRequestAlertException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.edu.sena.domain.Recip}.
 */
@RestController
@RequestMapping("/api")
public class RecipResource {

    private final Logger log = LoggerFactory.getLogger(RecipResource.class);

    private static final String ENTITY_NAME = "recip";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecipService recipService;

    private final RecipRepository recipRepository;

    public RecipResource(RecipService recipService, RecipRepository recipRepository) {
        this.recipService = recipService;
        this.recipRepository = recipRepository;
    }

    /**
     * {@code POST  /recips} : Create a new recip.
     *
     * @param recipDTO the recipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recipDTO, or with status {@code 400 (Bad Request)} if the recip has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recips")
    public ResponseEntity<RecipDTO> createRecip(@Valid @RequestBody RecipDTO recipDTO) throws URISyntaxException {
        log.debug("REST request to save Recip : {}", recipDTO);
        if (recipDTO.getId() != null) {
            throw new BadRequestAlertException("A new recip cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecipDTO result = recipService.save(recipDTO);
        return ResponseEntity
            .created(new URI("/api/recips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /recips/:id} : Updates an existing recip.
     *
     * @param id the id of the recipDTO to save.
     * @param recipDTO the recipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipDTO,
     * or with status {@code 400 (Bad Request)} if the recipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recips/{id}")
    public ResponseEntity<RecipDTO> updateRecip(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RecipDTO recipDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Recip : {}, {}", id, recipDTO);
        if (recipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RecipDTO result = recipService.update(recipDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /recips/:id} : Partial updates given fields of an existing recip, field will ignore if it is null
     *
     * @param id the id of the recipDTO to save.
     * @param recipDTO the recipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recipDTO,
     * or with status {@code 400 (Bad Request)} if the recipDTO is not valid,
     * or with status {@code 404 (Not Found)} if the recipDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the recipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/recips/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecipDTO> partialUpdateRecip(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RecipDTO recipDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Recip partially : {}, {}", id, recipDTO);
        if (recipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecipDTO> result = recipService.partialUpdate(recipDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recipDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /recips} : get all the recips.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recips in body.
     */
    @GetMapping("/recips")
    public ResponseEntity<List<RecipDTO>> getAllRecips(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Recips");
        Page<RecipDTO> page = recipService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recips/:id} : get the "id" recip.
     *
     * @param id the id of the recipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recips/{id}")
    public ResponseEntity<RecipDTO> getRecip(@PathVariable Long id) {
        log.debug("REST request to get Recip : {}", id);
        Optional<RecipDTO> recipDTO = recipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipDTO);
    }

    /**
     * {@code DELETE  /recips/:id} : delete the "id" recip.
     *
     * @param id the id of the recipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recips/{id}")
    public ResponseEntity<Void> deleteRecip(@PathVariable Long id) {
        log.debug("REST request to delete Recip : {}", id);
        recipService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
