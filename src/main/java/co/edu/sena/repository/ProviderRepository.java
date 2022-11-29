package co.edu.sena.repository;

import co.edu.sena.domain.Provider;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Provider entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {
    Optional<Provider> findByEmail(String email);
    Optional<Provider> findByName(String name);
    Optional<Provider> findByNit(String nit);
    Optional<Provider> findByPhone(String phone);
}
