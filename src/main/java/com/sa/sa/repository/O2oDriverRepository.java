package com.sa.sa.repository;

import com.sa.sa.domain.O2oDriver;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the O2oDriver entity.
 */
@SuppressWarnings("unused")
@Repository
public interface O2oDriverRepository extends JpaRepository<O2oDriver, Long> {

}
