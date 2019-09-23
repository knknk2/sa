package com.sa.sa.repository;
import com.sa.sa.domain.M2mDriverDTOMF;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the M2mDriverDTOMF entity.
 */
@SuppressWarnings("unused")
@Repository
public interface M2mDriverDTOMFRepository extends JpaRepository<M2mDriverDTOMF, Long>, JpaSpecificationExecutor<M2mDriverDTOMF> {

}
