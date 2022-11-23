package co.edu.sena.service;

import co.edu.sena.service.dto.PresentationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.Presentation}.
 */
public interface PresentationService {
    /**
     * Save a presentation.
     *
     * @param presentationDTO the entity to save.
     * @return the persisted entity.
     */
    PresentationDTO save(PresentationDTO presentationDTO);

    /**
     * Updates a presentation.
     *
     * @param presentationDTO the entity to update.
     * @return the persisted entity.
     */
    PresentationDTO update(PresentationDTO presentationDTO);

    /**
     * Partially updates a presentation.
     *
     * @param presentationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PresentationDTO> partialUpdate(PresentationDTO presentationDTO);

    /**
     * Get all the presentations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PresentationDTO> findAll(Pageable pageable);

    /**
     * Get all the presentations with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PresentationDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" presentation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PresentationDTO> findOne(Long id);

    /**
     * Delete the "id" presentation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
