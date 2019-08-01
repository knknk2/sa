package com.sa.sa.service;

import com.sa.sa.domain.O2oCar;
import com.sa.sa.repository.O2oCarRepository;
import com.sa.sa.repository.O2oDriverRepository;
import com.sa.sa.repository.search.O2oCarSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link O2oCar}.
 */
@Service
@Transactional
public class O2oCarService {

    private final Logger log = LoggerFactory.getLogger(O2oCarService.class);

    private final O2oCarRepository o2oCarRepository;

    private final O2oCarSearchRepository o2oCarSearchRepository;

    private final O2oDriverRepository o2oDriverRepository;

    public O2oCarService(O2oCarRepository o2oCarRepository, O2oCarSearchRepository o2oCarSearchRepository, O2oDriverRepository o2oDriverRepository) {
        this.o2oCarRepository = o2oCarRepository;
        this.o2oCarSearchRepository = o2oCarSearchRepository;
        this.o2oDriverRepository = o2oDriverRepository;
    }

    /**
     * Save a o2oCar.
     *
     * @param o2oCar the entity to save.
     * @return the persisted entity.
     */
    public O2oCar save(O2oCar o2oCar) {
        log.debug("Request to save O2oCar : {}", o2oCar);
        Long o2oDriverId = o2oCar.getO2oDriver().getId();
        o2oDriverRepository.findById(o2oDriverId).ifPresent(o2oCar::o2oDriver);
        O2oCar result = o2oCarRepository.save(o2oCar);
        o2oCarSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the o2oCars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<O2oCar> findAll(Pageable pageable) {
        log.debug("Request to get all O2oCars");
        return o2oCarRepository.findAll(pageable);
    }


    /**
     * Get one o2oCar by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<O2oCar> findOne(Long id) {
        log.debug("Request to get O2oCar : {}", id);
        return o2oCarRepository.findById(id);
    }

    /**
     * Delete the o2oCar by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete O2oCar : {}", id);
        o2oCarRepository.deleteById(id);
        o2oCarSearchRepository.deleteById(id);
    }

    /**
     * Search for the o2oCar corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<O2oCar> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of O2oCars for query {}", query);
        return o2oCarSearchRepository.search(queryStringQuery(query), pageable);    }
}
