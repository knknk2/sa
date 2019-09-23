package com.sa.sa.service;

import com.sa.sa.domain.To2mPerson;
import com.sa.sa.repository.To2mPersonRepository;
import com.sa.sa.repository.search.To2mPersonSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link To2mPerson}.
 */
@Service
@Transactional
public class To2mPersonService {

    private final Logger log = LoggerFactory.getLogger(To2mPersonService.class);

    private final To2mPersonRepository to2mPersonRepository;

    private final To2mPersonSearchRepository to2mPersonSearchRepository;

    public To2mPersonService(To2mPersonRepository to2mPersonRepository, To2mPersonSearchRepository to2mPersonSearchRepository) {
        this.to2mPersonRepository = to2mPersonRepository;
        this.to2mPersonSearchRepository = to2mPersonSearchRepository;
    }

    /**
     * Save a to2mPerson.
     *
     * @param to2mPerson the entity to save.
     * @return the persisted entity.
     */
    public To2mPerson save(To2mPerson to2mPerson) {
        log.debug("Request to save To2mPerson : {}", to2mPerson);
        To2mPerson result = to2mPersonRepository.save(to2mPerson);
        to2mPersonSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the to2mPeople.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<To2mPerson> findAll(Pageable pageable) {
        log.debug("Request to get all To2mPeople");
        return to2mPersonRepository.findAll(pageable);
    }


    /**
     * Get one to2mPerson by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<To2mPerson> findOne(Long id) {
        log.debug("Request to get To2mPerson : {}", id);
        return to2mPersonRepository.findById(id);
    }

    /**
     * Delete the to2mPerson by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete To2mPerson : {}", id);
        to2mPersonRepository.deleteById(id);
        to2mPersonSearchRepository.deleteById(id);
    }

    /**
     * Search for the to2mPerson corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<To2mPerson> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of To2mPeople for query {}", query);
        return to2mPersonSearchRepository.search(queryStringQuery(query), pageable);    }
}
