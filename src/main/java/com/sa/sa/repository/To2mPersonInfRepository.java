package com.sa.sa.repository;

import com.sa.sa.domain.To2mPersonInf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the To2mPersonInf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface To2mPersonInfRepository extends JpaRepository<To2mPersonInf, Long> {

}
