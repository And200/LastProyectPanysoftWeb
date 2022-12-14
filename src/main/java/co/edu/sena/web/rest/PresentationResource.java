package co.edu.sena.web.rest;

import co.edu.sena.domain.Person;
import co.edu.sena.domain.Presentation;
import co.edu.sena.repository.PresentationRepository;
import co.edu.sena.security.AuthoritiesConstants;
import co.edu.sena.service.PresentationService;
import co.edu.sena.service.dto.PresentationDTO;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.edu.sena.domain.Presentation}.
 */
@RestController
@RequestMapping("/api")
public class PresentationResource {

    private final Logger log = LoggerFactory.getLogger(PresentationResource.class);

    private static final String ENTITY_NAME = "presentation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PresentationService presentationService;

    private final PresentationRepository presentationRepository;

    public PresentationResource(PresentationService presentationService, PresentationRepository presentationRepository) {
        this.presentationService = presentationService;
        this.presentationRepository = presentationRepository;
    }

    /**
     * {@code POST  /presentations} : Create a new presentation.
     *
     * @param presentationDTO the presentationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new presentationDTO, or with status {@code 400 (Bad Request)} if the presentation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/presentations")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<PresentationDTO> createPresentation(@Valid @RequestBody PresentationDTO presentationDTO)
        throws URISyntaxException {
        log.debug("REST request to save Presentation : {}", presentationDTO);
        if (presentationDTO.getId() != null) {
            throw new BadRequestAlertException("A new presentation cannot already have an ID", ENTITY_NAME, "idexists");
        } else if (presentationRepository.findByPresentation(presentationDTO.getPresentation()).isPresent()) {
            throw new BadRequestAlertException(
                "A new category cannot have an already existing name Category",
                ENTITY_NAME,
                "categoryNameExists"
            );
        }
        PresentationDTO result = presentationService.save(presentationDTO);
        return ResponseEntity
            .created(new URI("/api/presentations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /presentations/:id} : Updates an existing presentation.
     *
     * @param id the id of the presentationDTO to save.
     * @param presentationDTO the presentationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated presentationDTO,
     * or with status {@code 400 (Bad Request)} if the presentationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the presentationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/presentations/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<PresentationDTO> updatePresentation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PresentationDTO presentationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Presentation : {}, {}", id, presentationDTO);
        Optional<Presentation> presentationOptional = presentationRepository.findByPresentation(presentationDTO.getPresentation());
        if (presentationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        } else if (presentationOptional.isPresent()) {
            throw new BadRequestAlertException(
                "A new category cannot have an already existing name Category",
                ENTITY_NAME,
                "categoryNameExists"
            );
        }
        if (!Objects.equals(id, presentationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!presentationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PresentationDTO result = presentationService.update(presentationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, presentationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /presentations/:id} : Partial updates given fields of an existing presentation, field will ignore if it is null
     *
     * @param id the id of the presentationDTO to save.
     * @param presentationDTO the presentationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated presentationDTO,
     * or with status {@code 400 (Bad Request)} if the presentationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the presentationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the presentationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/presentations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<PresentationDTO> partialUpdatePresentation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PresentationDTO presentationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Presentation partially : {}, {}", id, presentationDTO);
        Optional<Presentation> presentationOptional = presentationRepository.findByPresentation(presentationDTO.getPresentation());
        if (presentationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        } else if (presentationOptional.isPresent()) {
            throw new BadRequestAlertException(
                "A new category cannot have an already existing name Category",
                ENTITY_NAME,
                "categoryNameExists"
            );
        }
        if (!Objects.equals(id, presentationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!presentationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PresentationDTO> result = presentationService.partialUpdate(presentationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, presentationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /presentations} : get all the presentations.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of presentations in body.
     */
    @GetMapping("/presentations")
    @PreAuthorize(
        "hasAuthority('" +
        AuthoritiesConstants.ADMIN +
        "')or hasAuthority('" +
        AuthoritiesConstants.BAKER +
        "')or hasAuthority('" +
        AuthoritiesConstants.CASHIER +
        "')"
    )
    public ResponseEntity<List<PresentationDTO>> getAllPresentations(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Presentations");
        Page<PresentationDTO> page;
        if (eagerload) {
            page = presentationService.findAllWithEagerRelationships(pageable);
        } else {
            page = presentationService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /presentations/:id} : get the "id" presentation.
     *
     * @param id the id of the presentationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the presentationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/presentations/{id}")
    @PreAuthorize(
        "hasAuthority('" +
        AuthoritiesConstants.ADMIN +
        "')or hasAuthority('" +
        AuthoritiesConstants.BAKER +
        "')or hasAuthority('" +
        AuthoritiesConstants.CASHIER +
        "')"
    )
    public ResponseEntity<PresentationDTO> getPresentation(@PathVariable Long id) {
        log.debug("REST request to get Presentation : {}", id);
        Optional<PresentationDTO> presentationDTO = presentationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(presentationDTO);
    }

    /**
     * {@code DELETE  /presentations/:id} : delete the "id" presentation.
     *
     * @param id the id of the presentationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/presentations/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<Void> deletePresentation(@PathVariable Long id) {
        log.debug("REST request to delete Presentation : {}", id);
        try {
            presentationService.delete(id);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestAlertException("This register depends of  other entity", ENTITY_NAME, "entityDepends");
        }
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
