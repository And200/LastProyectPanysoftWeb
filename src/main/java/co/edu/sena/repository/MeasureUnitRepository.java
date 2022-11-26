package co.edu.sena.repository;

import co.edu.sena.domain.MeasureUnit;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MeasureUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeasureUnitRepository extends JpaRepository<MeasureUnit, Long> {
    Optional<MeasureUnit> findByNameUnit(String nameUnit);
}
