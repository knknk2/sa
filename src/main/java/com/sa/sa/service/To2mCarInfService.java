package com.sa.sa.service;

import com.sa.sa.domain.To2mCarInf;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link To2mCarInf}.
 */
public interface To2mCarInfService {

    /**
     * Save a to2mCarInf.
     *
     * @param to2mCarInf the entity to save.
     * @return the persisted entity.
     */
    To2mCarInf save(To2mCarInf to2mCarInf);

    /**
     * Get all the to2mCarInfs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<To2mCarInf> findAll(Pageable pageable);


    /**
     * Get the "id" to2mCarInf.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<To2mCarInf> findOne(Long id);

    /**
     * Delete the "id" to2mCarInf.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the to2mCarInf corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<To2mCarInf> search(String query, Pageable pageable);
}
