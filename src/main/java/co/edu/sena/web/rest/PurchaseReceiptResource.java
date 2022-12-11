package co.edu.sena.web.rest;

import co.edu.sena.repository.PurchaseReceiptRepository;
import co.edu.sena.security.AuthoritiesConstants;
import co.edu.sena.service.PurchaseReceiptService;
import co.edu.sena.service.dto.PurchaseReceiptDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.PurchaseReceipt}.
 */
@RestController
@RequestMapping("/api")
public class PurchaseReceiptResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseReceiptResource.class);

    private static final String ENTITY_NAME = "purchaseReceipt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PurchaseReceiptService purchaseReceiptService;

    private final PurchaseReceiptRepository purchaseReceiptRepository;

    public PurchaseReceiptResource(PurchaseReceiptService purchaseReceiptService, PurchaseReceiptRepository purchaseReceiptRepository) {
        this.purchaseReceiptService = purchaseReceiptService;
        this.purchaseReceiptRepository = purchaseReceiptRepository;
    }

    /**
     * {@code POST  /purchase-receipts} : Create a new purchaseReceipt.
     *
     * @param purchaseReceiptDTO the purchaseReceiptDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new purchaseReceiptDTO, or with status {@code 400 (Bad Request)} if the purchaseReceipt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/purchase-receipts")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')or hasAuthority('" + AuthoritiesConstants.CASHIER + "')")
    public ResponseEntity<PurchaseReceiptDTO> createPurchaseReceipt(@Valid @RequestBody PurchaseReceiptDTO purchaseReceiptDTO)
        throws URISyntaxException {
        log.debug("REST request to save PurchaseReceipt : {}", purchaseReceiptDTO);
        if (purchaseReceiptDTO.getId() != null) {
            throw new BadRequestAlertException("A new purchaseReceipt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseReceiptDTO result = purchaseReceiptService.save(purchaseReceiptDTO);
        return ResponseEntity
            .created(new URI("/api/purchase-receipts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /purchase-receipts/:id} : Updates an existing purchaseReceipt.
     *
     * @param id the id of the purchaseReceiptDTO to save.
     * @param purchaseReceiptDTO the purchaseReceiptDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchaseReceiptDTO,
     * or with status {@code 400 (Bad Request)} if the purchaseReceiptDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the purchaseReceiptDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/purchase-receipts/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')or hasAuthority('" + AuthoritiesConstants.CASHIER + "')")
    public ResponseEntity<PurchaseReceiptDTO> updatePurchaseReceipt(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PurchaseReceiptDTO purchaseReceiptDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PurchaseReceipt : {}, {}", id, purchaseReceiptDTO);
        if (purchaseReceiptDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, purchaseReceiptDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!purchaseReceiptRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PurchaseReceiptDTO result = purchaseReceiptService.update(purchaseReceiptDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, purchaseReceiptDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /purchase-receipts/:id} : Partial updates given fields of an existing purchaseReceipt, field will ignore if it is null
     *
     * @param id the id of the purchaseReceiptDTO to save.
     * @param purchaseReceiptDTO the purchaseReceiptDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated purchaseReceiptDTO,
     * or with status {@code 400 (Bad Request)} if the purchaseReceiptDTO is not valid,
     * or with status {@code 404 (Not Found)} if the purchaseReceiptDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the purchaseReceiptDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/purchase-receipts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')or hasAuthority('" + AuthoritiesConstants.CASHIER + "')")
    public ResponseEntity<PurchaseReceiptDTO> partialUpdatePurchaseReceipt(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PurchaseReceiptDTO purchaseReceiptDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PurchaseReceipt partially : {}, {}", id, purchaseReceiptDTO);
        if (purchaseReceiptDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, purchaseReceiptDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!purchaseReceiptRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<PurchaseReceiptDTO> result = purchaseReceiptService.partialUpdate(purchaseReceiptDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, purchaseReceiptDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /purchase-receipts} : get all the purchaseReceipts.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of purchaseReceipts in body.
     */
    @GetMapping("/purchase-receipts")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')or hasAuthority('" + AuthoritiesConstants.CASHIER + "')")
    public ResponseEntity<List<PurchaseReceiptDTO>> getAllPurchaseReceipts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of PurchaseReceipts");
        Page<PurchaseReceiptDTO> page;
        if (eagerload) {
            page = purchaseReceiptService.findAllWithEagerRelationships(pageable);
        } else {
            page = purchaseReceiptService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /purchase-receipts/:id} : get the "id" purchaseReceipt.
     *
     * @param id the id of the purchaseReceiptDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the purchaseReceiptDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/purchase-receipts/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')or hasAuthority('" + AuthoritiesConstants.CASHIER + "')")
    public ResponseEntity<PurchaseReceiptDTO> getPurchaseReceipt(@PathVariable Long id) {
        log.debug("REST request to get PurchaseReceipt : {}", id);
        Optional<PurchaseReceiptDTO> purchaseReceiptDTO = purchaseReceiptService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseReceiptDTO);
    }

    /**
     * {@code DELETE  /purchase-receipts/:id} : delete the "id" purchaseReceipt.
     *
     * @param id the id of the purchaseReceiptDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/purchase-receipts/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')or hasAuthority('" + AuthoritiesConstants.CASHIER + "')")
    public ResponseEntity<Void> deletePurchaseReceipt(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseReceipt : {}", id);
        try {
            purchaseReceiptService.delete(id);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestAlertException("This register depends of  other entity", ENTITY_NAME, "entityDepends");
        }
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
