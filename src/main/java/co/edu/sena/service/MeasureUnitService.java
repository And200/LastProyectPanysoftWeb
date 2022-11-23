package co.edu.sena.service;

import co.edu.sena.service.dto.MeasureUnitDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.MeasureUnit}.
 */
public interface MeasureUnitService {
    /**
     * Save a measureUnit.
     *
     * @param measureUnitDTO the entity to save.
     * @return the persisted entity.
     */
    MeasureUnitDTO save(MeasureUnitDTO measureUnitDTO);

    /**
     * Updates a measureUnit.
     *
     * @param measureUnitDTO the entity to update.
     * @return the persisted entity.
     */
    MeasureUnitDTO update(MeasureUnitDTO measureUnitDTO);

    /**
     * Partially updates a measureUnit.
     *
     * @param measureUnitDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MeasureUnitDTO> partialUpdate(MeasureUnitDTO measureUnitDTO);

    /**
     * Get all the measureUnits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MeasureUnitDTO> findAll(Pageable pageable);

    /**
     * Get the "id" measureUnit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MeasureUnitDTO> findOne(Long id);

    /**
     * Delete the "id" measureUnit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
