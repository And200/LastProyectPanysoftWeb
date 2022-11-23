package co.edu.sena.service;

import co.edu.sena.service.dto.PurchaseReceiptDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.PurchaseReceipt}.
 */
public interface PurchaseReceiptService {
    /**
     * Save a purchaseReceipt.
     *
     * @param purchaseReceiptDTO the entity to save.
     * @return the persisted entity.
     */
    PurchaseReceiptDTO save(PurchaseReceiptDTO purchaseReceiptDTO);

    /**
     * Updates a purchaseReceipt.
     *
     * @param purchaseReceiptDTO the entity to update.
     * @return the persisted entity.
     */
    PurchaseReceiptDTO update(PurchaseReceiptDTO purchaseReceiptDTO);

    /**
     * Partially updates a purchaseReceipt.
     *
     * @param purchaseReceiptDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PurchaseReceiptDTO> partialUpdate(PurchaseReceiptDTO purchaseReceiptDTO);

    /**
     * Get all the purchaseReceipts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PurchaseReceiptDTO> findAll(Pageable pageable);

    /**
     * Get all the purchaseReceipts with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PurchaseReceiptDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" purchaseReceipt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PurchaseReceiptDTO> findOne(Long id);

    /**
     * Delete the "id" purchaseReceipt.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
