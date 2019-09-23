package com.sa.sa.service;

import com.sa.sa.domain.To2mPersonInf;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link To2mPersonInf}.
 */
public interface To2mPersonInfService {

    /**
     * Save a to2mPersonInf.
     *
     * @param to2mPersonInf the entity to save.
     * @return the persisted entity.
     */
    To2mPersonInf save(To2mPersonInf to2mPersonInf);

    /**
     * Get all the to2mPersonInfs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<To2mPersonInf> findAll(Pageable pageable);


    /**
     * Get the "id" to2mPersonInf.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<To2mPersonInf> findOne(Long id);

    /**
     * Delete the "id" to2mPersonInf.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the to2mPersonInf corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<To2mPersonInf> search(String query, Pageable pageable);
}
