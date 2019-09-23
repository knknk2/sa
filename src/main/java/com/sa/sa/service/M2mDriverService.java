package com.sa.sa.service;

import com.sa.sa.domain.M2mDriver;
import com.sa.sa.repository.M2mDriverRepository;
import com.sa.sa.repository.search.M2mDriverSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link M2mDriver}.
 */
@Service
@Transactional
public class M2mDriverService {

    private final Logger log = LoggerFactory.getLogger(M2mDriverService.class);

    private final M2mDriverRepository m2mDriverRepository;

    private final M2mDriverSearchRepository m2mDriverSearchRepository;

    public M2mDriverService(M2mDriverRepository m2mDriverRepository, M2mDriverSearchRepository m2mDriverSearchRepository) {
        this.m2mDriverRepository = m2mDriverRepository;
        this.m2mDriverSearchRepository = m2mDriverSearchRepository;
    }

    /**
     * Save a m2mDriver.
     *
     * @param m2mDriver the entity to save.
     * @return the persisted entity.
     */
    public M2mDriver save(M2mDriver m2mDriver) {
        log.debug("Request to save M2mDriver : {}", m2mDriver);
        M2mDriver result = m2mDriverRepository.save(m2mDriver);
        m2mDriverSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the m2mDrivers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<M2mDriver> findAll(Pageable pageable) {
        log.debug("Request to get all M2mDrivers");
        return m2mDriverRepository.findAll(pageable);
    }


    /**
     * Get one m2mDriver by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<M2mDriver> findOne(Long id) {
        log.debug("Request to get M2mDriver : {}", id);
        return m2mDriverRepository.findById(id);
    }

    /**
     * Delete the m2mDriver by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete M2mDriver : {}", id);
        m2mDriverRepository.deleteById(id);
        m2mDriverSearchRepository.deleteById(id);
    }

    /**
     * Search for the m2mDriver corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<M2mDriver> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of M2mDrivers for query {}", query);
        return m2mDriverSearchRepository.search(queryStringQuery(query), pageable);    }
}
