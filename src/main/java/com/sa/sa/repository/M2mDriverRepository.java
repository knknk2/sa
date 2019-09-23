package com.sa.sa.repository;

import com.sa.sa.domain.M2mDriver;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the M2mDriver entity.
 */
@SuppressWarnings("unused")
@Repository
public interface M2mDriverRepository extends JpaRepository<M2mDriver, Long> {

}
