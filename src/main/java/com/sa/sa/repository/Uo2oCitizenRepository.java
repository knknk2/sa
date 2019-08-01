package com.sa.sa.repository;

import com.sa.sa.domain.Uo2oCitizen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Uo2oCitizen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Uo2oCitizenRepository extends JpaRepository<Uo2oCitizen, Long> {

}
