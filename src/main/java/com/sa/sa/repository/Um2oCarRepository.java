package com.sa.sa.repository;
import com.sa.sa.domain.Um2oCar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Um2oCar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Um2oCarRepository extends JpaRepository<Um2oCar, Long> {

}
