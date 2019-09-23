package com.sa.sa.service;

import com.sa.sa.domain.M2mCar;
import com.sa.sa.repository.M2mCarRepository;
import com.sa.sa.repository.search.M2mCarSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link M2mCar}.
 */
@Service
@Transactional
public class M2mCarService {

    private final Logger log = LoggerFactory.getLogger(M2mCarService.class);

    private final M2mCarRepository m2mCarRepository;

    private final M2mCarSearchRepository m2mCarSearchRepository;

    public M2mCarService(M2mCarRepository m2mCarRepository, M2mCarSearchRepository m2mCarSearchRepository) {
        this.m2mCarRepository = m2mCarRepository;
        this.m2mCarSearchRepository = m2mCarSearchRepository;
    }

    /**
     * Save a m2mCar.
     *
     * @param m2mCar the entity to save.
     * @return the persisted entity.
     */
    public M2mCar save(M2mCar m2mCar) {
        log.debug("Request to save M2mCar : {}", m2mCar);
        M2mCar result = m2mCarRepository.save(m2mCar);
        m2mCarSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the m2mCars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<M2mCar> findAll(Pageable pageable) {
        log.debug("Request to get all M2mCars");
        return m2mCarRepository.findAll(pageable);
    }

    /**
     * Get all the m2mCars with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<M2mCar> findAllWithEagerRelationships(Pageable pageable) {
        return m2mCarRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one m2mCar by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<M2mCar> findOne(Long id) {
        log.debug("Request to get M2mCar : {}", id);
        return m2mCarRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the m2mCar by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete M2mCar : {}", id);
        m2mCarRepository.deleteById(id);
        m2mCarSearchRepository.deleteById(id);
    }

    /**
     * Search for the m2mCar corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<M2mCar> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of M2mCars for query {}", query);
        return m2mCarSearchRepository.search(queryStringQuery(query), pageable);    }
}
