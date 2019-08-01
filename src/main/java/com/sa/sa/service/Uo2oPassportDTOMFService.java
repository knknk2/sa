package com.sa.sa.service;

import com.sa.sa.domain.Uo2oPassportDTOMF;
import com.sa.sa.repository.Uo2oPassportDTOMFRepository;
import com.sa.sa.repository.search.Uo2oPassportDTOMFSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Uo2oPassportDTOMF}.
 */
@Service
@Transactional
public class Uo2oPassportDTOMFService {

    private final Logger log = LoggerFactory.getLogger(Uo2oPassportDTOMFService.class);

    private final Uo2oPassportDTOMFRepository uo2oPassportDTOMFRepository;

    private final Uo2oPassportDTOMFSearchRepository uo2oPassportDTOMFSearchRepository;

    public Uo2oPassportDTOMFService(Uo2oPassportDTOMFRepository uo2oPassportDTOMFRepository, Uo2oPassportDTOMFSearchRepository uo2oPassportDTOMFSearchRepository) {
        this.uo2oPassportDTOMFRepository = uo2oPassportDTOMFRepository;
        this.uo2oPassportDTOMFSearchRepository = uo2oPassportDTOMFSearchRepository;
    }

    /**
     * Save a uo2oPassportDTOMF.
     *
     * @param uo2oPassportDTOMF the entity to save.
     * @return the persisted entity.
     */
    public Uo2oPassportDTOMF save(Uo2oPassportDTOMF uo2oPassportDTOMF) {
        log.debug("Request to save Uo2oPassportDTOMF : {}", uo2oPassportDTOMF);
        Uo2oPassportDTOMF result = uo2oPassportDTOMFRepository.save(uo2oPassportDTOMF);
        uo2oPassportDTOMFSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the uo2oPassportDTOMFS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Uo2oPassportDTOMF> findAll(Pageable pageable) {
        log.debug("Request to get all Uo2oPassportDTOMFS");
        return uo2oPassportDTOMFRepository.findAll(pageable);
    }


    /**
     * Get one uo2oPassportDTOMF by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Uo2oPassportDTOMF> findOne(Long id) {
        log.debug("Request to get Uo2oPassportDTOMF : {}", id);
        return uo2oPassportDTOMFRepository.findById(id);
    }

    /**
     * Delete the uo2oPassportDTOMF by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Uo2oPassportDTOMF : {}", id);
        uo2oPassportDTOMFRepository.deleteById(id);
        uo2oPassportDTOMFSearchRepository.deleteById(id);
    }

    /**
     * Search for the uo2oPassportDTOMF corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Uo2oPassportDTOMF> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Uo2oPassportDTOMFS for query {}", query);
        return uo2oPassportDTOMFSearchRepository.search(queryStringQuery(query), pageable);    }
}
