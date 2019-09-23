package com.sa.sa.repository;

import com.sa.sa.domain.To2mPerson;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the To2mPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface To2mPersonRepository extends JpaRepository<To2mPerson, Long> {

}
