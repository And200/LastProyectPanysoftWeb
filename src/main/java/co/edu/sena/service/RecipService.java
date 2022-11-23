package co.edu.sena.service;

import co.edu.sena.service.dto.RecipDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.Recip}.
 */
public interface RecipService {
    /**
     * Save a recip.
     *
     * @param recipDTO the entity to save.
     * @return the persisted entity.
     */
    RecipDTO save(RecipDTO recipDTO);

    /**
     * Updates a recip.
     *
     * @param recipDTO the entity to update.
     * @return the persisted entity.
     */
    RecipDTO update(RecipDTO recipDTO);

    /**
     * Partially updates a recip.
     *
     * @param recipDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RecipDTO> partialUpdate(RecipDTO recipDTO);

    /**
     * Get all the recips.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RecipDTO> findAll(Pageable pageable);

    /**
     * Get the "id" recip.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecipDTO> findOne(Long id);

    /**
     * Delete the "id" recip.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
