package com.sa.sa.repository;
import com.sa.sa.domain.O2oCar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the O2oCar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface O2oCarRepository extends JpaRepository<O2oCar, Long> {

}
