package co.edu.sena.service;

import co.edu.sena.service.dto.DetailAmountRecipDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.DetailAmountRecip}.
 */
public interface DetailAmountRecipService {
    /**
     * Save a detailAmountRecip.
     *
     * @param detailAmountRecipDTO the entity to save.
     * @return the persisted entity.
     */
    DetailAmountRecipDTO save(DetailAmountRecipDTO detailAmountRecipDTO);

    /**
     * Updates a detailAmountRecip.
     *
     * @param detailAmountRecipDTO the entity to update.
     * @return the persisted entity.
     */
    DetailAmountRecipDTO update(DetailAmountRecipDTO detailAmountRecipDTO);

    /**
     * Partially updates a detailAmountRecip.
     *
     * @param detailAmountRecipDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DetailAmountRecipDTO> partialUpdate(DetailAmountRecipDTO detailAmountRecipDTO);

    /**
     * Get all the detailAmountRecips.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DetailAmountRecipDTO> findAll(Pageable pageable);

    /**
     * Get all the detailAmountRecips with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DetailAmountRecipDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" detailAmountRecip.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DetailAmountRecipDTO> findOne(Long id);

    /**
     * Delete the "id" detailAmountRecip.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
