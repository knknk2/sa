package com.sa.sa.repository;

import com.sa.sa.domain.To2mCarInf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the To2mCarInf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface To2mCarInfRepository extends JpaRepository<To2mCarInf, Long>, JpaSpecificationExecutor<To2mCarInf> {

}
