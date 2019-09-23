package com.sa.sa.service;

import com.sa.sa.domain.M2mDriverDTOMF;
import com.sa.sa.repository.M2mDriverDTOMFRepository;
import com.sa.sa.repository.search.M2mDriverDTOMFSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link M2mDriverDTOMF}.
 */
@Service
@Transactional
public class M2mDriverDTOMFService {

    private final Logger log = LoggerFactory.getLogger(M2mDriverDTOMFService.class);

    private final M2mDriverDTOMFRepository m2mDriverDTOMFRepository;

    private final M2mDriverDTOMFSearchRepository m2mDriverDTOMFSearchRepository;

    public M2mDriverDTOMFService(M2mDriverDTOMFRepository m2mDriverDTOMFRepository, M2mDriverDTOMFSearchRepository m2mDriverDTOMFSearchRepository) {
        this.m2mDriverDTOMFRepository = m2mDriverDTOMFRepository;
        this.m2mDriverDTOMFSearchRepository = m2mDriverDTOMFSearchRepository;
    }

    /**
     * Save a m2mDriverDTOMF.
     *
     * @param m2mDriverDTOMF the entity to save.
     * @return the persisted entity.
     */
    public M2mDriverDTOMF save(M2mDriverDTOMF m2mDriverDTOMF) {
        log.debug("Request to save M2mDriverDTOMF : {}", m2mDriverDTOMF);
        M2mDriverDTOMF result = m2mDriverDTOMFRepository.save(m2mDriverDTOMF);
        m2mDriverDTOMFSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the m2mDriverDTOMFS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<M2mDriverDTOMF> findAll(Pageable pageable) {
        log.debug("Request to get all M2mDriverDTOMFS");
        return m2mDriverDTOMFRepository.findAll(pageable);
    }


    /**
     * Get one m2mDriverDTOMF by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<M2mDriverDTOMF> findOne(Long id) {
        log.debug("Request to get M2mDriverDTOMF : {}", id);
        return m2mDriverDTOMFRepository.findById(id);
    }

    /**
     * Delete the m2mDriverDTOMF by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete M2mDriverDTOMF : {}", id);
        m2mDriverDTOMFRepository.deleteById(id);
        m2mDriverDTOMFSearchRepository.deleteById(id);
    }

    /**
     * Search for the m2mDriverDTOMF corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<M2mDriverDTOMF> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of M2mDriverDTOMFS for query {}", query);
        return m2mDriverDTOMFSearchRepository.search(queryStringQuery(query), pageable);    }
}
