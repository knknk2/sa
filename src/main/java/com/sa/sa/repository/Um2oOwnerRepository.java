package com.sa.sa.repository;
import com.sa.sa.domain.Um2oOwner;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Um2oOwner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Um2oOwnerRepository extends JpaRepository<Um2oOwner, Long> {

}
