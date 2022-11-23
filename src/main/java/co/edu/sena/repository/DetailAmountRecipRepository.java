package co.edu.sena.repository;

import co.edu.sena.domain.DetailAmountRecip;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DetailAmountRecip entity.
 */
@Repository
public interface DetailAmountRecipRepository extends JpaRepository<DetailAmountRecip, Long> {
    default Optional<DetailAmountRecip> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DetailAmountRecip> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DetailAmountRecip> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct detailAmountRecip from DetailAmountRecip detailAmountRecip left join fetch detailAmountRecip.product left join fetch detailAmountRecip.recip",
        countQuery = "select count(distinct detailAmountRecip) from DetailAmountRecip detailAmountRecip"
    )
    Page<DetailAmountRecip> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct detailAmountRecip from DetailAmountRecip detailAmountRecip left join fetch detailAmountRecip.product left join fetch detailAmountRecip.recip"
    )
    List<DetailAmountRecip> findAllWithToOneRelationships();

    @Query(
        "select detailAmountRecip from DetailAmountRecip detailAmountRecip left join fetch detailAmountRecip.product left join fetch detailAmountRecip.recip where detailAmountRecip.id =:id"
    )
    Optional<DetailAmountRecip> findOneWithToOneRelationships(@Param("id") Long id);
}
