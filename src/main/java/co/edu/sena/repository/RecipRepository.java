package co.edu.sena.repository;

import co.edu.sena.domain.Recip;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Recip entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipRepository extends JpaRepository<Recip, Long> {
    Optional<Recip> findByNameRecip(String nameRecip);
}
