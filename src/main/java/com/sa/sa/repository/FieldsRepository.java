package com.sa.sa.repository;

import com.sa.sa.domain.Fields;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Fields entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldsRepository extends JpaRepository<Fields, Long>, JpaSpecificationExecutor<Fields> {

}
