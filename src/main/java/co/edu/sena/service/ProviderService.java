package co.edu.sena.service;

import co.edu.sena.service.dto.ProviderDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.sena.domain.Provider}.
 */
public interface ProviderService {
    /**
     * Save a provider.
     *
     * @param providerDTO the entity to save.
     * @return the persisted entity.
     */
    ProviderDTO save(ProviderDTO providerDTO);

    /**
     * Updates a provider.
     *
     * @param providerDTO the entity to update.
     * @return the persisted entity.
     */
    ProviderDTO update(ProviderDTO providerDTO);

    /**
     * Partially updates a provider.
     *
     * @param providerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProviderDTO> partialUpdate(ProviderDTO providerDTO);

    /**
     * Get all the providers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProviderDTO> findAll(Pageable pageable);

    /**
     * Get the "id" provider.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProviderDTO> findOne(Long id);

    /**
     * Delete the "id" provider.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
