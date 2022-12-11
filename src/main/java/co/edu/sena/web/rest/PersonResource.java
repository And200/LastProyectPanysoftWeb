package co.edu.sena.web.rest;

import co.edu.sena.domain.Person;
import co.edu.sena.repository.PersonRepository;
import co.edu.sena.security.AuthoritiesConstants;
import co.edu.sena.service.PersonService;
import co.edu.sena.service.dto.PersonDTO;
import co.edu.sena.service.mapper.PersonMapper;
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
 * REST controller for managing {@link co.edu.sena.domain.Person}.
 */
@RestController
@RequestMapping("/api")
public class PersonResource {

    private final Logger log = LoggerFactory.getLogger(PersonResource.class);

    private static final String ENTITY_NAME = "person";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonService personService;

    private final PersonRepository personRepository;

    public PersonResource(PersonService personService, PersonRepository personRepository) {
        this.personService = personService;
        this.personRepository = personRepository;
    }

    /**
     * {@code POST  /people} : Create a new person.
     *
     * @param personDTO the personDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personDTO, or with status {@code 400 (Bad Request)} if the person has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/people")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<PersonDTO> createPerson(@Valid @RequestBody PersonDTO personDTO) throws URISyntaxException {
        log.debug("REST request to save Person : {}", personDTO);
        if (personDTO.getId() != null) {
            throw new BadRequestAlertException("A new person cannot already have an ID", ENTITY_NAME, "idexists");
        } else if (personRepository.findByEmail(personDTO.getEmail()).isPresent()) {
            throw new BadRequestAlertException("The email already exist", ENTITY_NAME, "EmailExist");
        }
        PersonDTO result = personService.save(personDTO);
        return ResponseEntity
            .created(new URI("/api/people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /people/:id} : Updates an existing person.
     *
     * @param id the id of the personDTO to save.
     * @param personDTO the personDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personDTO,
     * or with status {@code 400 (Bad Request)} if the personDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/people/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<PersonDTO> updatePerson(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PersonDTO personDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Person : {}, {}", id, personDTO);
        Optional<Person> personOptional = personRepository.findByEmail(personDTO.getEmail());
        if (personDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        } else if (personOptional.isPresent()) {
            if (!Objects.equals(personOptional.get().getId(), personDTO.getId())) {
                throw new BadRequestAlertException("The email already exist", ENTITY_NAME, "EmailExist");
            }
        }
        if (!Objects.equals(id, personDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonDTO result = personService.update(personDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /people/:id} : Partial updates given fields of an existing person, field will ignore if it is null
     *
     * @param id the id of the personDTO to save.
     * @param personDTO the personDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personDTO,
     * or with status {@code 400 (Bad Request)} if the personDTO is not valid,
     * or with status {@code 404 (Not Found)} if the personDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the personDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PatchMapping(value = "/people/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<PersonDTO> partialUpdatePerson(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PersonDTO personDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Person partially : {}, {}", id, personDTO);
        Optional<Person> personOptional = personRepository.findByEmail(personDTO.getEmail());
        if (personDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        } else if (personOptional.isPresent()) {
            if (!Objects.equals(personOptional.get().getId(), personDTO.getId())) {
                throw new BadRequestAlertException("The email already exist", ENTITY_NAME, "EmailExist");
            }
        }
        if (!Objects.equals(id, personDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!personRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<PersonDTO> result = personService.partialUpdate(personDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /people} : get all the people.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of people in body.
     */
    @GetMapping("/people")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<List<PersonDTO>> getAllPeople(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of People");
        Page<PersonDTO> page;
        if (eagerload) {
            page = personService.findAllWithEagerRelationships(pageable);
        } else {
            page = personService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /people/:id} : get the "id" person.
     *
     * @param id the id of the personDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/people/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<PersonDTO> getPerson(@PathVariable Long id) {
        log.debug("REST request to get Person : {}", id);
        Optional<PersonDTO> personDTO = personService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personDTO);
    }

    /**
     * {@code DELETE  /people/:id} : delete the "id" person.
     *
     * @param id the id of the personDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/people/{id}")
    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        log.debug("REST request to delete Person : {}", id);
        try {
            personService.delete(id);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestAlertException("This register depends of  other entity", ENTITY_NAME, "entityDepends");
        }
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
