package com.sa.sa.repository;
import com.sa.sa.domain.Uo2oCitizenDTOMF;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Uo2oCitizenDTOMF entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Uo2oCitizenDTOMFRepository extends JpaRepository<Uo2oCitizenDTOMF, Long>, JpaSpecificationExecutor<Uo2oCitizenDTOMF> {

}
