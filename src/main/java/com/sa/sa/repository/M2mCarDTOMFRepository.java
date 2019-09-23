package com.sa.sa.repository;

import com.sa.sa.domain.M2mCarDTOMF;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the M2mCarDTOMF entity.
 */
@Repository
public interface M2mCarDTOMFRepository extends JpaRepository<M2mCarDTOMF, Long>, JpaSpecificationExecutor<M2mCarDTOMF> {

    @Query(value = "select distinct m2mCarDTOMF from M2mCarDTOMF m2mCarDTOMF left join fetch m2mCarDTOMF.m2mDriverDTOMFS",
        countQuery = "select count(distinct m2mCarDTOMF) from M2mCarDTOMF m2mCarDTOMF")
    Page<M2mCarDTOMF> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct m2mCarDTOMF from M2mCarDTOMF m2mCarDTOMF left join fetch m2mCarDTOMF.m2mDriverDTOMFS")
    List<M2mCarDTOMF> findAllWithEagerRelationships();

    @Query("select m2mCarDTOMF from M2mCarDTOMF m2mCarDTOMF left join fetch m2mCarDTOMF.m2mDriverDTOMFS where m2mCarDTOMF.id =:id")
    Optional<M2mCarDTOMF> findOneWithEagerRelationships(@Param("id") Long id);

}
