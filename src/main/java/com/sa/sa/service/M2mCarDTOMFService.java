package com.sa.sa.service;

import com.sa.sa.domain.M2mCarDTOMF;
import com.sa.sa.repository.M2mCarDTOMFRepository;
import com.sa.sa.repository.search.M2mCarDTOMFSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link M2mCarDTOMF}.
 */
@Service
@Transactional
public class M2mCarDTOMFService {

    private final Logger log = LoggerFactory.getLogger(M2mCarDTOMFService.class);

    private final M2mCarDTOMFRepository m2mCarDTOMFRepository;

    private final M2mCarDTOMFSearchRepository m2mCarDTOMFSearchRepository;

    public M2mCarDTOMFService(M2mCarDTOMFRepository m2mCarDTOMFRepository, M2mCarDTOMFSearchRepository m2mCarDTOMFSearchRepository) {
        this.m2mCarDTOMFRepository = m2mCarDTOMFRepository;
        this.m2mCarDTOMFSearchRepository = m2mCarDTOMFSearchRepository;
    }

    /**
     * Save a m2mCarDTOMF.
     *
     * @param m2mCarDTOMF the entity to save.
     * @return the persisted entity.
     */
    public M2mCarDTOMF save(M2mCarDTOMF m2mCarDTOMF) {
        log.debug("Request to save M2mCarDTOMF : {}", m2mCarDTOMF);
        M2mCarDTOMF result = m2mCarDTOMFRepository.save(m2mCarDTOMF);
        m2mCarDTOMFSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the m2mCarDTOMFS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<M2mCarDTOMF> findAll(Pageable pageable) {
        log.debug("Request to get all M2mCarDTOMFS");
        return m2mCarDTOMFRepository.findAll(pageable);
    }

    /**
     * Get all the m2mCarDTOMFS with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<M2mCarDTOMF> findAllWithEagerRelationships(Pageable pageable) {
        return m2mCarDTOMFRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one m2mCarDTOMF by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<M2mCarDTOMF> findOne(Long id) {
        log.debug("Request to get M2mCarDTOMF : {}", id);
        return m2mCarDTOMFRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the m2mCarDTOMF by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete M2mCarDTOMF : {}", id);
        m2mCarDTOMFRepository.deleteById(id);
        m2mCarDTOMFSearchRepository.deleteById(id);
    }

    /**
     * Search for the m2mCarDTOMF corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<M2mCarDTOMF> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of M2mCarDTOMFS for query {}", query);
        return m2mCarDTOMFSearchRepository.search(queryStringQuery(query), pageable);    }
}
