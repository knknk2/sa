package com.sa.sa.service;

import com.sa.sa.domain.Um2oCar;
import com.sa.sa.repository.Um2oCarRepository;
import com.sa.sa.repository.search.Um2oCarSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Um2oCar}.
 */
@Service
@Transactional
public class Um2oCarService {

    private final Logger log = LoggerFactory.getLogger(Um2oCarService.class);

    private final Um2oCarRepository um2oCarRepository;

    private final Um2oCarSearchRepository um2oCarSearchRepository;

    public Um2oCarService(Um2oCarRepository um2oCarRepository, Um2oCarSearchRepository um2oCarSearchRepository) {
        this.um2oCarRepository = um2oCarRepository;
        this.um2oCarSearchRepository = um2oCarSearchRepository;
    }

    /**
     * Save a um2oCar.
     *
     * @param um2oCar the entity to save.
     * @return the persisted entity.
     */
    public Um2oCar save(Um2oCar um2oCar) {
        log.debug("Request to save Um2oCar : {}", um2oCar);
        Um2oCar result = um2oCarRepository.save(um2oCar);
        um2oCarSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the um2oCars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Um2oCar> findAll(Pageable pageable) {
        log.debug("Request to get all Um2oCars");
        return um2oCarRepository.findAll(pageable);
    }


    /**
     * Get one um2oCar by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Um2oCar> findOne(Long id) {
        log.debug("Request to get Um2oCar : {}", id);
        return um2oCarRepository.findById(id);
    }

    /**
     * Delete the um2oCar by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Um2oCar : {}", id);
        um2oCarRepository.deleteById(id);
        um2oCarSearchRepository.deleteById(id);
    }

    /**
     * Search for the um2oCar corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Um2oCar> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Um2oCars for query {}", query);
        return um2oCarSearchRepository.search(queryStringQuery(query), pageable);    }
}
