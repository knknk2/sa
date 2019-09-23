package com.sa.sa.repository;
import com.sa.sa.domain.Uo2oPassport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Uo2oPassport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Uo2oPassportRepository extends JpaRepository<Uo2oPassport, Long> {

}
