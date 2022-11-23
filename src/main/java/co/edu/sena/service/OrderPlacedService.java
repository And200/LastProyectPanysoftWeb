package co.edu.sena.service;

import co.edu.sena.service.dto.OrderPlacedDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.OrderPlaced}.
 */
public interface OrderPlacedService {
    /**
     * Save a orderPlaced.
     *
     * @param orderPlacedDTO the entity to save.
     * @return the persisted entity.
     */
    OrderPlacedDTO save(OrderPlacedDTO orderPlacedDTO);

    /**
     * Updates a orderPlaced.
     *
     * @param orderPlacedDTO the entity to update.
     * @return the persisted entity.
     */
    OrderPlacedDTO update(OrderPlacedDTO orderPlacedDTO);

    /**
     * Partially updates a orderPlaced.
     *
     * @param orderPlacedDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrderPlacedDTO> partialUpdate(OrderPlacedDTO orderPlacedDTO);

    /**
     * Get all the orderPlaceds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrderPlacedDTO> findAll(Pageable pageable);

    /**
     * Get the "id" orderPlaced.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderPlacedDTO> findOne(Long id);

    /**
     * Delete the "id" orderPlaced.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
