package co.edu.sena.service;

import co.edu.sena.service.dto.DetailOrderDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.DetailOrder}.
 */
public interface DetailOrderService {
    /**
     * Save a detailOrder.
     *
     * @param detailOrderDTO the entity to save.
     * @return the persisted entity.
     */
    DetailOrderDTO save(DetailOrderDTO detailOrderDTO);

    /**
     * Updates a detailOrder.
     *
     * @param detailOrderDTO the entity to update.
     * @return the persisted entity.
     */
    DetailOrderDTO update(DetailOrderDTO detailOrderDTO);

    /**
     * Partially updates a detailOrder.
     *
     * @param detailOrderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DetailOrderDTO> partialUpdate(DetailOrderDTO detailOrderDTO);

    /**
     * Get all the detailOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DetailOrderDTO> findAll(Pageable pageable);

    /**
     * Get all the detailOrders with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DetailOrderDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" detailOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DetailOrderDTO> findOne(Long id);

    /**
     * Delete the "id" detailOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
