package com.sa.sa.repository;
import com.sa.sa.domain.Uo2oPassportDTOMF;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Uo2oPassportDTOMF entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Uo2oPassportDTOMFRepository extends JpaRepository<Uo2oPassportDTOMF, Long>, JpaSpecificationExecutor<Uo2oPassportDTOMF> {

}
