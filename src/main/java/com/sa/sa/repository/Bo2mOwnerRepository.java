package com.sa.sa.repository;

import com.sa.sa.domain.Bo2mOwner;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Bo2mOwner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Bo2mOwnerRepository extends JpaRepository<Bo2mOwner, Long> {

}
