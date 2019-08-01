package com.sa.sa.service.impl;

import com.sa.sa.service.To2mCarService;
import com.sa.sa.domain.To2mCar;
import com.sa.sa.repository.To2mCarRepository;
import com.sa.sa.repository.search.To2mCarSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link To2mCar}.
 */
@Service
@Transactional
public class To2mCarServiceImpl implements To2mCarService {

    private final Logger log = LoggerFactory.getLogger(To2mCarServiceImpl.class);

    private final To2mCarRepository to2mCarRepository;

    private final To2mCarSearchRepository to2mCarSearchRepository;

    public To2mCarServiceImpl(To2mCarRepository to2mCarRepository, To2mCarSearchRepository to2mCarSearchRepository) {
        this.to2mCarRepository = to2mCarRepository;
        this.to2mCarSearchRepository = to2mCarSearchRepository;
    }

    /**
     * Save a to2mCar.
     *
     * @param to2mCar the entity to save.
     * @return the persisted entity.
     */
    @Override
    public To2mCar save(To2mCar to2mCar) {
        log.debug("Request to save To2mCar : {}", to2mCar);
        To2mCar result = to2mCarRepository.save(to2mCar);
        to2mCarSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the to2mCars.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<To2mCar> findAll(Pageable pageable) {
        log.debug("Request to get all To2mCars");
        return to2mCarRepository.findAll(pageable);
    }


    /**
     * Get one to2mCar by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<To2mCar> findOne(Long id) {
        log.debug("Request to get To2mCar : {}", id);
        return to2mCarRepository.findById(id);
    }

    /**
     * Delete the to2mCar by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete To2mCar : {}", id);
        to2mCarRepository.deleteById(id);
        to2mCarSearchRepository.deleteById(id);
    }

    /**
     * Search for the to2mCar corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<To2mCar> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of To2mCars for query {}", query);
        return to2mCarSearchRepository.search(queryStringQuery(query), pageable);    }
}
