package co.edu.sena.web.rest;

import co.edu.sena.repository.OrderPlacedRepository;
import co.edu.sena.service.OrderPlacedService;
import co.edu.sena.service.dto.OrderPlacedDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.OrderPlaced}.
 */
@RestController
@RequestMapping("/api")
public class OrderPlacedResource {

    private final Logger log = LoggerFactory.getLogger(OrderPlacedResource.class);

    private static final String ENTITY_NAME = "orderPlaced";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderPlacedService orderPlacedService;

    private final OrderPlacedRepository orderPlacedRepository;

    public OrderPlacedResource(OrderPlacedService orderPlacedService, OrderPlacedRepository orderPlacedRepository) {
        this.orderPlacedService = orderPlacedService;
        this.orderPlacedRepository = orderPlacedRepository;
    }

    /**
     * {@code POST  /order-placeds} : Create a new orderPlaced.
     *
     * @param orderPlacedDTO the orderPlacedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderPlacedDTO, or with status {@code 400 (Bad Request)} if the orderPlaced has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-placeds")
    public ResponseEntity<OrderPlacedDTO> createOrderPlaced(@Valid @RequestBody OrderPlacedDTO orderPlacedDTO) throws URISyntaxException {
        log.debug("REST request to save OrderPlaced : {}", orderPlacedDTO);
        if (orderPlacedDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderPlaced cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderPlacedDTO result = orderPlacedService.save(orderPlacedDTO);
        return ResponseEntity
            .created(new URI("/api/order-placeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-placeds/:id} : Updates an existing orderPlaced.
     *
     * @param id the id of the orderPlacedDTO to save.
     * @param orderPlacedDTO the orderPlacedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderPlacedDTO,
     * or with status {@code 400 (Bad Request)} if the orderPlacedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderPlacedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-placeds/{id}")
    public ResponseEntity<OrderPlacedDTO> updateOrderPlaced(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrderPlacedDTO orderPlacedDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrderPlaced : {}, {}", id, orderPlacedDTO);
        if (orderPlacedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderPlacedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderPlacedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderPlacedDTO result = orderPlacedService.update(orderPlacedDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderPlacedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /order-placeds/:id} : Partial updates given fields of an existing orderPlaced, field will ignore if it is null
     *
     * @param id the id of the orderPlacedDTO to save.
     * @param orderPlacedDTO the orderPlacedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderPlacedDTO,
     * or with status {@code 400 (Bad Request)} if the orderPlacedDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orderPlacedDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderPlacedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/order-placeds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderPlacedDTO> partialUpdateOrderPlaced(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrderPlacedDTO orderPlacedDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderPlaced partially : {}, {}", id, orderPlacedDTO);
        if (orderPlacedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderPlacedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderPlacedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderPlacedDTO> result = orderPlacedService.partialUpdate(orderPlacedDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderPlacedDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /order-placeds} : get all the orderPlaceds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderPlaceds in body.
     */
    @GetMapping("/order-placeds")
    public ResponseEntity<List<OrderPlacedDTO>> getAllOrderPlaceds(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of OrderPlaceds");
        Page<OrderPlacedDTO> page = orderPlacedService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-placeds/:id} : get the "id" orderPlaced.
     *
     * @param id the id of the orderPlacedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderPlacedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-placeds/{id}")
    public ResponseEntity<OrderPlacedDTO> getOrderPlaced(@PathVariable Long id) {
        log.debug("REST request to get OrderPlaced : {}", id);
        Optional<OrderPlacedDTO> orderPlacedDTO = orderPlacedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderPlacedDTO);
    }

    /**
     * {@code DELETE  /order-placeds/:id} : delete the "id" orderPlaced.
     *
     * @param id the id of the orderPlacedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-placeds/{id}")
    public ResponseEntity<Void> deleteOrderPlaced(@PathVariable Long id) {
        log.debug("REST request to delete OrderPlaced : {}", id);
        orderPlacedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
