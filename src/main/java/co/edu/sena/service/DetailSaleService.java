package co.edu.sena.service;

import co.edu.sena.service.dto.DetailSaleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.DetailSale}.
 */
public interface DetailSaleService {
    /**
     * Save a detailSale.
     *
     * @param detailSaleDTO the entity to save.
     * @return the persisted entity.
     */
    DetailSaleDTO save(DetailSaleDTO detailSaleDTO);

    /**
     * Updates a detailSale.
     *
     * @param detailSaleDTO the entity to update.
     * @return the persisted entity.
     */
    DetailSaleDTO update(DetailSaleDTO detailSaleDTO);

    /**
     * Partially updates a detailSale.
     *
     * @param detailSaleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DetailSaleDTO> partialUpdate(DetailSaleDTO detailSaleDTO);

    /**
     * Get all the detailSales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DetailSaleDTO> findAll(Pageable pageable);

    /**
     * Get all the detailSales with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DetailSaleDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" detailSale.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DetailSaleDTO> findOne(Long id);

    /**
     * Delete the "id" detailSale.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
