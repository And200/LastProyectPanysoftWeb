package co.edu.sena.web.rest;

import co.edu.sena.repository.DetailSaleRepository;
import co.edu.sena.security.AuthoritiesConstants;
import co.edu.sena.service.DetailSaleService;
import co.edu.sena.service.dto.DetailSaleDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.edu.sena.domain.DetailSale}.
 */
@RestController
@RequestMapping("/api")
public class DetailSaleResource {

    private final Logger log = LoggerFactory.getLogger(DetailSaleResource.class);

    private static final String ENTITY_NAME = "detailSale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailSaleService detailSaleService;

    private final DetailSaleRepository detailSaleRepository;

    public DetailSaleResource(DetailSaleService detailSaleService, DetailSaleRepository detailSaleRepository) {
        this.detailSaleService = detailSaleService;
        this.detailSaleRepository = detailSaleRepository;
    }

    /**
     * {@code POST  /detail-sales} : Create a new detailSale.
     *
     * @param detailSaleDTO the detailSaleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailSaleDTO, or with status {@code 400 (Bad Request)} if the detailSale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detail-sales")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')or hasAuthority('" + AuthoritiesConstants.CASHIER + "')")
    public ResponseEntity<DetailSaleDTO> createDetailSale(@Valid @RequestBody DetailSaleDTO detailSaleDTO) throws URISyntaxException {
        log.debug("REST request to save DetailSale : {}", detailSaleDTO);
        if (detailSaleDTO.getId() != null) {
            throw new BadRequestAlertException("A new detailSale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailSaleDTO result = detailSaleService.save(detailSaleDTO);
        return ResponseEntity
            .created(new URI("/api/detail-sales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detail-sales/:id} : Updates an existing detailSale.
     *
     * @param id the id of the detailSaleDTO to save.
     * @param detailSaleDTO the detailSaleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailSaleDTO,
     * or with status {@code 400 (Bad Request)} if the detailSaleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailSaleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detail-sales/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')or hasAuthority('" + AuthoritiesConstants.CASHIER + "')")
    public ResponseEntity<DetailSaleDTO> updateDetailSale(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DetailSaleDTO detailSaleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DetailSale : {}, {}", id, detailSaleDTO);
        if (detailSaleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailSaleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailSaleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetailSaleDTO result = detailSaleService.update(detailSaleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailSaleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /detail-sales/:id} : Partial updates given fields of an existing detailSale, field will ignore if it is null
     *
     * @param id the id of the detailSaleDTO to save.
     * @param detailSaleDTO the detailSaleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailSaleDTO,
     * or with status {@code 400 (Bad Request)} if the detailSaleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the detailSaleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the detailSaleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/detail-sales/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')or hasAuthority('" + AuthoritiesConstants.CASHIER + "')")
    public ResponseEntity<DetailSaleDTO> partialUpdateDetailSale(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DetailSaleDTO detailSaleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetailSale partially : {}, {}", id, detailSaleDTO);
        if (detailSaleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailSaleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailSaleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetailSaleDTO> result = detailSaleService.partialUpdate(detailSaleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailSaleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /detail-sales} : get all the detailSales.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailSales in body.
     */
    @GetMapping("/detail-sales")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')or hasAuthority('" + AuthoritiesConstants.CASHIER + "')")
    public ResponseEntity<List<DetailSaleDTO>> getAllDetailSales(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of DetailSales");
        Page<DetailSaleDTO> page;
        if (eagerload) {
            page = detailSaleService.findAllWithEagerRelationships(pageable);
        } else {
            page = detailSaleService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /detail-sales/:id} : get the "id" detailSale.
     *
     * @param id the id of the detailSaleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailSaleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detail-sales/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')or hasAuthority('" + AuthoritiesConstants.CASHIER + "')")
    public ResponseEntity<DetailSaleDTO> getDetailSale(@PathVariable Long id) {
        log.debug("REST request to get DetailSale : {}", id);
        Optional<DetailSaleDTO> detailSaleDTO = detailSaleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detailSaleDTO);
    }

    /**
     * {@code DELETE  /detail-sales/:id} : delete the "id" detailSale.
     *
     * @param id the id of the detailSaleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detail-sales/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')or hasAuthority('" + AuthoritiesConstants.CASHIER + "')")
    public ResponseEntity<Void> deleteDetailSale(@PathVariable Long id) {
        log.debug("REST request to delete DetailSale : {}", id);
        try {
            detailSaleService.delete(id);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestAlertException("This register depends of  other entity", ENTITY_NAME, "entityDepends");
        }
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
