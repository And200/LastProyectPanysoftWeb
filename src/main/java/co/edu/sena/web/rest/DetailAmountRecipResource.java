package co.edu.sena.web.rest;

import co.edu.sena.domain.Product;
import co.edu.sena.domain.Recip;
import co.edu.sena.repository.DetailAmountRecipRepository;
import co.edu.sena.repository.ProductRepository;
import co.edu.sena.repository.RecipRepository;
import co.edu.sena.security.AuthoritiesConstants;
import co.edu.sena.service.DetailAmountRecipService;
import co.edu.sena.service.dto.DetailAmountRecipDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.edu.sena.domain.DetailAmountRecip}.
 */
@RestController
@RequestMapping("/api")
public class DetailAmountRecipResource {

    private final Logger log = LoggerFactory.getLogger(DetailAmountRecipResource.class);

    private static final String ENTITY_NAME = "detailAmountRecip";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailAmountRecipService detailAmountRecipService;

    private final DetailAmountRecipRepository detailAmountRecipRepository;

    private final ProductRepository productRepository;

    private final RecipRepository recipRepository;

    public DetailAmountRecipResource(
        DetailAmountRecipService detailAmountRecipService,
        DetailAmountRecipRepository detailAmountRecipRepository,
        ProductRepository productRepository,
        RecipRepository recipRepository
    ) {
        this.detailAmountRecipService = detailAmountRecipService;
        this.detailAmountRecipRepository = detailAmountRecipRepository;
        this.productRepository = productRepository;
        this.recipRepository = recipRepository;
    }

    /**
     * {@code POST  /detail-amount-recips} : Create a new detailAmountRecip.
     *
     * @param detailAmountRecipDTO the detailAmountRecipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailAmountRecipDTO, or with status {@code 400 (Bad Request)} if the detailAmountRecip has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detail-amount-recips")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<DetailAmountRecipDTO> createDetailAmountRecip(@Valid @RequestBody DetailAmountRecipDTO detailAmountRecipDTO)
        throws URISyntaxException {
        log.debug("REST request to save DetailAmountRecip : {}", detailAmountRecipDTO);
        Optional<Recip> recipOptional = recipRepository.findById(detailAmountRecipDTO.getRecip().getId());

        Optional<Product> productOptional = productRepository.findById(detailAmountRecipDTO.getProduct().getId());

        if (detailAmountRecipDTO.getId() != null) {
            throw new BadRequestAlertException("A new detailAmountRecip cannot already have an ID", ENTITY_NAME, "idexists");
        } else if (recipOptional.isEmpty()) {
            throw new BadRequestAlertException("The Recip doesn't exist", ENTITY_NAME, "recipNotExist");
        } else if (productOptional.isEmpty()) {
            throw new BadRequestAlertException("The Product doesn't exist", ENTITY_NAME, "productNotExist");
        } else if (detailAmountRecipRepository.findByProductAndRecip(productOptional.get(), recipOptional.get()).isPresent()) {
            throw new BadRequestAlertException("There is already a product with these characteristics", ENTITY_NAME, "detailAlreadyExists");
        }

        DetailAmountRecipDTO result = detailAmountRecipService.save(detailAmountRecipDTO);
        return ResponseEntity
            .created(new URI("/api/detail-amount-recips/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detail-amount-recips/:id} : Updates an existing detailAmountRecip.
     *
     * @param id the id of the detailAmountRecipDTO to save.
     * @param detailAmountRecipDTO the detailAmountRecipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailAmountRecipDTO,
     * or with status {@code 400 (Bad Request)} if the detailAmountRecipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailAmountRecipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detail-amount-recips/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<DetailAmountRecipDTO> updateDetailAmountRecip(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DetailAmountRecipDTO detailAmountRecipDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DetailAmountRecip : {}, {}", id, detailAmountRecipDTO);
        if (detailAmountRecipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailAmountRecipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailAmountRecipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetailAmountRecipDTO result = detailAmountRecipService.update(detailAmountRecipDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailAmountRecipDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /detail-amount-recips/:id} : Partial updates given fields of an existing detailAmountRecip, field will ignore if it is null
     *
     * @param id the id of the detailAmountRecipDTO to save.
     * @param detailAmountRecipDTO the detailAmountRecipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailAmountRecipDTO,
     * or with status {@code 400 (Bad Request)} if the detailAmountRecipDTO is not valid,
     * or with status {@code 404 (Not Found)} if the detailAmountRecipDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the detailAmountRecipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/detail-amount-recips/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<DetailAmountRecipDTO> partialUpdateDetailAmountRecip(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DetailAmountRecipDTO detailAmountRecipDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetailAmountRecip partially : {}, {}", id, detailAmountRecipDTO);
        if (detailAmountRecipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailAmountRecipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailAmountRecipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetailAmountRecipDTO> result = detailAmountRecipService.partialUpdate(detailAmountRecipDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailAmountRecipDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /detail-amount-recips} : get all the detailAmountRecips.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailAmountRecips in body.
     */
    @GetMapping("/detail-amount-recips")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<List<DetailAmountRecipDTO>> getAllDetailAmountRecips(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of DetailAmountRecips");
        Page<DetailAmountRecipDTO> page;
        if (eagerload) {
            page = detailAmountRecipService.findAllWithEagerRelationships(pageable);
        } else {
            page = detailAmountRecipService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /detail-amount-recips/:id} : get the "id" detailAmountRecip.
     *
     * @param id the id of the detailAmountRecipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailAmountRecipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detail-amount-recips/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<DetailAmountRecipDTO> getDetailAmountRecip(@PathVariable Long id) {
        log.debug("REST request to get DetailAmountRecip : {}", id);
        Optional<DetailAmountRecipDTO> detailAmountRecipDTO = detailAmountRecipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detailAmountRecipDTO);
    }

    /**
     * {@code DELETE  /detail-amount-recips/:id} : delete the "id" detailAmountRecip.
     *
     * @param id the id of the detailAmountRecipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detail-amount-recips/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<Void> deleteDetailAmountRecip(@PathVariable Long id) {
        log.debug("REST request to delete DetailAmountRecip : {}", id);
        detailAmountRecipService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
