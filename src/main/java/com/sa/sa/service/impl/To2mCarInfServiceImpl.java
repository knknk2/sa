package com.sa.sa.service.impl;

import com.sa.sa.service.To2mCarInfService;
import com.sa.sa.domain.To2mCarInf;
import com.sa.sa.repository.To2mCarInfRepository;
import com.sa.sa.repository.search.To2mCarInfSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link To2mCarInf}.
 */
@Service
@Transactional
public class To2mCarInfServiceImpl implements To2mCarInfService {

    private final Logger log = LoggerFactory.getLogger(To2mCarInfServiceImpl.class);

    private final To2mCarInfRepository to2mCarInfRepository;

    private final To2mCarInfSearchRepository to2mCarInfSearchRepository;

    public To2mCarInfServiceImpl(To2mCarInfRepository to2mCarInfRepository, To2mCarInfSearchRepository to2mCarInfSearchRepository) {
        this.to2mCarInfRepository = to2mCarInfRepository;
        this.to2mCarInfSearchRepository = to2mCarInfSearchRepository;
    }

    /**
     * Save a to2mCarInf.
     *
     * @param to2mCarInf the entity to save.
     * @return the persisted entity.
     */
    @Override
    public To2mCarInf save(To2mCarInf to2mCarInf) {
        log.debug("Request to save To2mCarInf : {}", to2mCarInf);
        To2mCarInf result = to2mCarInfRepository.save(to2mCarInf);
        to2mCarInfSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the to2mCarInfs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<To2mCarInf> findAll(Pageable pageable) {
        log.debug("Request to get all To2mCarInfs");
        return to2mCarInfRepository.findAll(pageable);
    }


    /**
     * Get one to2mCarInf by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<To2mCarInf> findOne(Long id) {
        log.debug("Request to get To2mCarInf : {}", id);
        return to2mCarInfRepository.findById(id);
    }

    /**
     * Delete the to2mCarInf by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete To2mCarInf : {}", id);
        to2mCarInfRepository.deleteById(id);
        to2mCarInfSearchRepository.deleteById(id);
    }

    /**
     * Search for the to2mCarInf corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<To2mCarInf> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of To2mCarInfs for query {}", query);
        return to2mCarInfSearchRepository.search(queryStringQuery(query), pageable);    }
}
