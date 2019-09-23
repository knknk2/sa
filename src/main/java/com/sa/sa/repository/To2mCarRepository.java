package com.sa.sa.repository;
import com.sa.sa.domain.To2mCar;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the To2mCar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface To2mCarRepository extends JpaRepository<To2mCar, Long>, JpaSpecificationExecutor<To2mCar> {

}
