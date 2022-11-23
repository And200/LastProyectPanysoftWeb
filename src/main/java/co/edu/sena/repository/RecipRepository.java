package co.edu.sena.repository;

import co.edu.sena.domain.Recip;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Recip entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecipRepository extends JpaRepository<Recip, Long> {}
