package com.sa.sa.repository;

import com.sa.sa.domain.M2mCar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the M2mCar entity.
 */
@Repository
public interface M2mCarRepository extends JpaRepository<M2mCar, Long> {

    @Query(value = "select distinct m2mCar from M2mCar m2mCar left join fetch m2mCar.m2mDrivers",
        countQuery = "select count(distinct m2mCar) from M2mCar m2mCar")
    Page<M2mCar> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct m2mCar from M2mCar m2mCar left join fetch m2mCar.m2mDrivers")
    List<M2mCar> findAllWithEagerRelationships();

    @Query("select m2mCar from M2mCar m2mCar left join fetch m2mCar.m2mDrivers where m2mCar.id =:id")
    Optional<M2mCar> findOneWithEagerRelationships(@Param("id") Long id);

}
