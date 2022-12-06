package co.edu.sena.web.rest;

import co.edu.sena.repository.MeasureUnitRepository;
import co.edu.sena.security.AuthoritiesConstants;
import co.edu.sena.service.MeasureUnitService;
import co.edu.sena.service.dto.MeasureUnitDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.MeasureUnit}.
 */
@RestController
@RequestMapping("/api")
public class MeasureUnitResource {

    private final Logger log = LoggerFactory.getLogger(MeasureUnitResource.class);

    private static final String ENTITY_NAME = "measureUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeasureUnitService measureUnitService;

    private final MeasureUnitRepository measureUnitRepository;

    public MeasureUnitResource(MeasureUnitService measureUnitService, MeasureUnitRepository measureUnitRepository) {
        this.measureUnitService = measureUnitService;
        this.measureUnitRepository = measureUnitRepository;
    }

    /**
     * {@code POST  /measure-units} : Create a new measureUnit.
     *
     * @param measureUnitDTO the measureUnitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new measureUnitDTO, or with status {@code 400 (Bad Request)} if the measureUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/measure-units")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<MeasureUnitDTO> createMeasureUnit(@Valid @RequestBody MeasureUnitDTO measureUnitDTO) throws URISyntaxException {
        log.debug("REST request to save MeasureUnit : {}", measureUnitDTO);
        if (measureUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new measureUnit cannot already have an ID", ENTITY_NAME, "idexists");
        } else if (measureUnitRepository.findByNameUnit(measureUnitDTO.getNameUnit()).isPresent()) {
            throw new BadRequestAlertException("the name unit alread exist", ENTITY_NAME, "unitExist");
        }
        MeasureUnitDTO result = measureUnitService.save(measureUnitDTO);
        return ResponseEntity
            .created(new URI("/api/measure-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /measure-units/:id} : Updates an existing measureUnit.
     *
     * @param id the id of the measureUnitDTO to save.
     * @param measureUnitDTO the measureUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated measureUnitDTO,
     * or with status {@code 400 (Bad Request)} if the measureUnitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the measureUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/measure-units/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<MeasureUnitDTO> updateMeasureUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MeasureUnitDTO measureUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MeasureUnit : {}, {}", id, measureUnitDTO);
        if (measureUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, measureUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!measureUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        MeasureUnitDTO result = measureUnitService.update(measureUnitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, measureUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /measure-units/:id} : Partial updates given fields of an existing measureUnit, field will ignore if it is null
     *
     * @param id the id of the measureUnitDTO to save.
     * @param measureUnitDTO the measureUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated measureUnitDTO,
     * or with status {@code 400 (Bad Request)} if the measureUnitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the measureUnitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the measureUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/measure-units/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<MeasureUnitDTO> partialUpdateMeasureUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MeasureUnitDTO measureUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MeasureUnit partially : {}, {}", id, measureUnitDTO);
        if (measureUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, measureUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!measureUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MeasureUnitDTO> result = measureUnitService.partialUpdate(measureUnitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, measureUnitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /measure-units} : get all the measureUnits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of measureUnits in body.
     */
    @GetMapping("/measure-units")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<List<MeasureUnitDTO>> getAllMeasureUnits(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of MeasureUnits");
        Page<MeasureUnitDTO> page = measureUnitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /measure-units/:id} : get the "id" measureUnit.
     *
     * @param id the id of the measureUnitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the measureUnitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/measure-units/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<MeasureUnitDTO> getMeasureUnit(@PathVariable Long id) {
        log.debug("REST request to get MeasureUnit : {}", id);
        Optional<MeasureUnitDTO> measureUnitDTO = measureUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(measureUnitDTO);
    }

    /**
     * {@code DELETE  /measure-units/:id} : delete the "id" measureUnit.
     *
     * @param id the id of the measureUnitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/measure-units/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<Void> deleteMeasureUnit(@PathVariable Long id) {
        log.debug("REST request to delete MeasureUnit : {}", id);
        measureUnitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
