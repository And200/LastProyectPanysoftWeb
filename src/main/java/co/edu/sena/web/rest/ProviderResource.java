package co.edu.sena.web.rest;

import co.edu.sena.repository.ProviderRepository;
import co.edu.sena.security.AuthoritiesConstants;
import co.edu.sena.service.ProviderService;
import co.edu.sena.service.dto.ProviderDTO;
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
 * REST controller for managing {@link co.edu.sena.domain.Provider}.
 */
@RestController
@RequestMapping("/api")
public class ProviderResource {

    private final Logger log = LoggerFactory.getLogger(ProviderResource.class);

    private static final String ENTITY_NAME = "provider";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProviderService providerService;

    private final ProviderRepository providerRepository;

    public ProviderResource(ProviderService providerService, ProviderRepository providerRepository) {
        this.providerService = providerService;
        this.providerRepository = providerRepository;
    }

    /**
     * {@code POST  /providers} : Create a new provider.
     *
     * @param providerDTO the providerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new providerDTO, or with status {@code 400 (Bad Request)} if the provider has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/providers")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<ProviderDTO> createProvider(@Valid @RequestBody ProviderDTO providerDTO) throws URISyntaxException {
        log.debug("REST request to save Provider : {}", providerDTO);
        if (providerDTO.getId() != null) {
            throw new BadRequestAlertException("A new provider cannot already have an ID", ENTITY_NAME, "idexists");
        } else if (providerRepository.findByEmail(providerDTO.getEmail()).isPresent()) {
            throw new BadRequestAlertException("The email already exist", ENTITY_NAME, "EmailExist");
        } else if (providerRepository.findByName(providerDTO.getName()).isPresent()) {
            throw new BadRequestAlertException("The provide Name Already Exist", ENTITY_NAME, "NameProviderExist");
        } else if (providerRepository.findByPhone(providerDTO.getPhone()).isPresent()) {
            throw new BadRequestAlertException("A new provider cannot already have an existing phone", ENTITY_NAME, "PhoneExist");
        } else if (providerRepository.findByNit(providerDTO.getNit()).isPresent()) {
            throw new BadRequestAlertException("Already exist a provider with these nit", ENTITY_NAME, "NitAlreadyExist");
        }
        ProviderDTO result = providerService.save(providerDTO);
        return ResponseEntity
            .created(new URI("/api/providers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /providers/:id} : Updates an existing provider.
     *
     * @param id the id of the providerDTO to save.
     * @param providerDTO the providerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated providerDTO,
     * or with status {@code 400 (Bad Request)} if the providerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the providerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/providers/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<ProviderDTO> updateProvider(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProviderDTO providerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Provider : {}, {}", id, providerDTO);
        if (providerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, providerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!providerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProviderDTO result = providerService.update(providerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, providerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /providers/:id} : Partial updates given fields of an existing provider, field will ignore if it is null
     *
     * @param id the id of the providerDTO to save.
     * @param providerDTO the providerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated providerDTO,
     * or with status {@code 400 (Bad Request)} if the providerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the providerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the providerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/providers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<ProviderDTO> partialUpdateProvider(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProviderDTO providerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Provider partially : {}, {}", id, providerDTO);
        if (providerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, providerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!providerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<ProviderDTO> result = providerService.partialUpdate(providerDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, providerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /providers} : get all the providers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of providers in body.
     */
    @GetMapping("/providers")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')or hasAuthority('" + AuthoritiesConstants.BAKER + "')")
    public ResponseEntity<List<ProviderDTO>> getAllProviders(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Providers");
        Page<ProviderDTO> page = providerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /providers/:id} : get the "id" provider.
     *
     * @param id the id of the providerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the providerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/providers/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')or hasAuthority('" + AuthoritiesConstants.BAKER + "')")
    public ResponseEntity<ProviderDTO> getProvider(@PathVariable Long id) {
        log.debug("REST request to get Provider : {}", id);
        Optional<ProviderDTO> providerDTO = providerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(providerDTO);
    }

    /**
     * {@code DELETE  /providers/:id} : delete the "id" provider.
     *
     * @param id the id of the providerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/providers/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        log.debug("REST request to delete Provider : {}", id);
        providerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
