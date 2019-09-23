package com.sa.sa.service.impl;

import com.sa.sa.service.To2mPersonInfService;
import com.sa.sa.domain.To2mPersonInf;
import com.sa.sa.repository.To2mPersonInfRepository;
import com.sa.sa.repository.search.To2mPersonInfSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link To2mPersonInf}.
 */
@Service
@Transactional
public class To2mPersonInfServiceImpl implements To2mPersonInfService {

    private final Logger log = LoggerFactory.getLogger(To2mPersonInfServiceImpl.class);

    private final To2mPersonInfRepository to2mPersonInfRepository;

    private final To2mPersonInfSearchRepository to2mPersonInfSearchRepository;

    public To2mPersonInfServiceImpl(To2mPersonInfRepository to2mPersonInfRepository, To2mPersonInfSearchRepository to2mPersonInfSearchRepository) {
        this.to2mPersonInfRepository = to2mPersonInfRepository;
        this.to2mPersonInfSearchRepository = to2mPersonInfSearchRepository;
    }

    /**
     * Save a to2mPersonInf.
     *
     * @param to2mPersonInf the entity to save.
     * @return the persisted entity.
     */
    @Override
    public To2mPersonInf save(To2mPersonInf to2mPersonInf) {
        log.debug("Request to save To2mPersonInf : {}", to2mPersonInf);
        To2mPersonInf result = to2mPersonInfRepository.save(to2mPersonInf);
        to2mPersonInfSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the to2mPersonInfs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<To2mPersonInf> findAll(Pageable pageable) {
        log.debug("Request to get all To2mPersonInfs");
        return to2mPersonInfRepository.findAll(pageable);
    }


    /**
     * Get one to2mPersonInf by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<To2mPersonInf> findOne(Long id) {
        log.debug("Request to get To2mPersonInf : {}", id);
        return to2mPersonInfRepository.findById(id);
    }

    /**
     * Delete the to2mPersonInf by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete To2mPersonInf : {}", id);
        to2mPersonInfRepository.deleteById(id);
        to2mPersonInfSearchRepository.deleteById(id);
    }

    /**
     * Search for the to2mPersonInf corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<To2mPersonInf> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of To2mPersonInfs for query {}", query);
        return to2mPersonInfSearchRepository.search(queryStringQuery(query), pageable);    }
}
