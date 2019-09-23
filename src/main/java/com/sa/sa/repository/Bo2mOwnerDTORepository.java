package com.sa.sa.repository;

import com.sa.sa.domain.Bo2mOwnerDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Bo2mOwnerDTO entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Bo2mOwnerDTORepository extends JpaRepository<Bo2mOwnerDTO, Long> {

}
