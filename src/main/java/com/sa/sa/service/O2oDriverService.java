package com.sa.sa.service;

import com.sa.sa.domain.O2oDriver;
import com.sa.sa.repository.O2oDriverRepository;
import com.sa.sa.repository.search.O2oDriverSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link O2oDriver}.
 */
@Service
@Transactional
public class O2oDriverService {

    private final Logger log = LoggerFactory.getLogger(O2oDriverService.class);

    private final O2oDriverRepository o2oDriverRepository;

    private final O2oDriverSearchRepository o2oDriverSearchRepository;

    public O2oDriverService(O2oDriverRepository o2oDriverRepository, O2oDriverSearchRepository o2oDriverSearchRepository) {
        this.o2oDriverRepository = o2oDriverRepository;
        this.o2oDriverSearchRepository = o2oDriverSearchRepository;
    }

    /**
     * Save a o2oDriver.
     *
     * @param o2oDriver the entity to save.
     * @return the persisted entity.
     */
    public O2oDriver save(O2oDriver o2oDriver) {
        log.debug("Request to save O2oDriver : {}", o2oDriver);
        O2oDriver result = o2oDriverRepository.save(o2oDriver);
        o2oDriverSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the o2oDrivers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<O2oDriver> findAll(Pageable pageable) {
        log.debug("Request to get all O2oDrivers");
        return o2oDriverRepository.findAll(pageable);
    }



    /**
    *  Get all the o2oDrivers where O2oCar is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<O2oDriver> findAllWhereO2oCarIsNull() {
        log.debug("Request to get all o2oDrivers where O2oCar is null");
        return StreamSupport
            .stream(o2oDriverRepository.findAll().spliterator(), false)
            .filter(o2oDriver -> o2oDriver.getO2oCar() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one o2oDriver by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<O2oDriver> findOne(Long id) {
        log.debug("Request to get O2oDriver : {}", id);
        return o2oDriverRepository.findById(id);
    }

    /**
     * Delete the o2oDriver by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete O2oDriver : {}", id);
        o2oDriverRepository.deleteById(id);
        o2oDriverSearchRepository.deleteById(id);
    }

    /**
     * Search for the o2oDriver corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<O2oDriver> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of O2oDrivers for query {}", query);
        return o2oDriverSearchRepository.search(queryStringQuery(query), pageable);    }
}
