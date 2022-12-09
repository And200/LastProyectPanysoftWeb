package co.edu.sena.web.rest;

import co.edu.sena.domain.DetailOrder;
import co.edu.sena.repository.DetailOrderRepository;
import co.edu.sena.security.AuthoritiesConstants;
import co.edu.sena.service.DetailOrderService;
import co.edu.sena.service.dto.DetailOrderDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.DetailOrder}.
 */
@RestController
@RequestMapping("/api")
public class DetailOrderResource {

    private final Logger log = LoggerFactory.getLogger(DetailOrderResource.class);

    private static final String ENTITY_NAME = "detailOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailOrderService detailOrderService;

    private final DetailOrderRepository detailOrderRepository;

    public DetailOrderResource(DetailOrderService detailOrderService, DetailOrderRepository detailOrderRepository) {
        this.detailOrderService = detailOrderService;
        this.detailOrderRepository = detailOrderRepository;
    }

    /**
     * {@code POST  /detail-orders} : Create a new detailOrder.
     *
     * @param detailOrderDTO the detailOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailOrderDTO, or with status {@code 400 (Bad Request)} if the detailOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detail-orders")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<DetailOrderDTO> createDetailOrder(@Valid @RequestBody DetailOrderDTO detailOrderDTO) throws URISyntaxException {
        log.debug("REST request to save DetailOrder : {}", detailOrderDTO);
        if (detailOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new detailOrder cannot already have an ID", ENTITY_NAME, "idexists");
        } else if (detailOrderRepository.findByInvoiceNumber(detailOrderDTO.getInvoiceNumber()).isPresent()) {
            throw new BadRequestAlertException("the invoice number already exist ", ENTITY_NAME, "invoiceNumberExist");
        }
        DetailOrderDTO result = detailOrderService.save(detailOrderDTO);
        return ResponseEntity
            .created(new URI("/api/detail-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detail-orders/:id} : Updates an existing detailOrder.
     *
     * @param id the id of the detailOrderDTO to save.
     * @param detailOrderDTO the detailOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailOrderDTO,
     * or with status {@code 400 (Bad Request)} if the detailOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detail-orders/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<DetailOrderDTO> updateDetailOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DetailOrderDTO detailOrderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DetailOrder : {}, {}", id, detailOrderDTO);
        Optional<DetailOrder> detailOrderOptional = detailOrderRepository.findByInvoiceNumber(detailOrderDTO.getInvoiceNumber());
        if (detailOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        } else if (detailOrderOptional.isPresent()) {
            if (!Objects.equals(detailOrderOptional.get().getId(), detailOrderDTO.getId())) {
                throw new BadRequestAlertException("the invoice number already exist ", ENTITY_NAME, "invoiceNumberExist");
            }
        }
        if (!Objects.equals(id, detailOrderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!detailOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetailOrderDTO result = detailOrderService.update(detailOrderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /detail-orders/:id} : Partial updates given fields of an existing detailOrder, field will ignore if it is null
     *
     * @param id the id of the detailOrderDTO to save.
     * @param detailOrderDTO the detailOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailOrderDTO,
     * or with status {@code 400 (Bad Request)} if the detailOrderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the detailOrderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the detailOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/detail-orders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<DetailOrderDTO> partialUpdateDetailOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DetailOrderDTO detailOrderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetailOrder partially : {}, {}", id, detailOrderDTO);
        Optional<DetailOrder> detailOrderOptional = detailOrderRepository.findByInvoiceNumber(detailOrderDTO.getInvoiceNumber());
        if (detailOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        } else if (detailOrderOptional.isPresent()) {
            if (!Objects.equals(detailOrderOptional.get().getId(), detailOrderDTO.getId())) {
                throw new BadRequestAlertException("the invoice number already exist ", ENTITY_NAME, "invoiceNumberExist");
            }
        }
        if (!Objects.equals(id, detailOrderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetailOrderDTO> result = detailOrderService.partialUpdate(detailOrderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detailOrderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /detail-orders} : get all the detailOrders.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailOrders in body.
     */
    @GetMapping("/detail-orders")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<List<DetailOrderDTO>> getAllDetailOrders(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of DetailOrders");
        Page<DetailOrderDTO> page;
        if (eagerload) {
            page = detailOrderService.findAllWithEagerRelationships(pageable);
        } else {
            page = detailOrderService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /detail-orders/:id} : get the "id" detailOrder.
     *
     * @param id the id of the detailOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detail-orders/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<DetailOrderDTO> getDetailOrder(@PathVariable Long id) {
        log.debug("REST request to get DetailOrder : {}", id);
        Optional<DetailOrderDTO> detailOrderDTO = detailOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detailOrderDTO);
    }

    /**
     * {@code DELETE  /detail-orders/:id} : delete the "id" detailOrder.
     *
     * @param id the id of the detailOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detail-orders/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<Void> deleteDetailOrder(@PathVariable Long id) {
        log.debug("REST request to delete DetailOrder : {}", id);
        detailOrderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
