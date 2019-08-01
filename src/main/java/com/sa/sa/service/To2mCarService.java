package com.sa.sa.service;

import com.sa.sa.domain.To2mCar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link To2mCar}.
 */
public interface To2mCarService {

    /**
     * Save a to2mCar.
     *
     * @param to2mCar the entity to save.
     * @return the persisted entity.
     */
    To2mCar save(To2mCar to2mCar);

    /**
     * Get all the to2mCars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<To2mCar> findAll(Pageable pageable);


    /**
     * Get the "id" to2mCar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<To2mCar> findOne(Long id);

    /**
     * Delete the "id" to2mCar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the to2mCar corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<To2mCar> search(String query, Pageable pageable);
}
