package com.sa.sa.service;

import com.sa.sa.domain.Uo2oPassport;
import com.sa.sa.repository.Uo2oPassportRepository;
import com.sa.sa.repository.search.Uo2oPassportSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Uo2oPassport}.
 */
@Service
@Transactional
public class Uo2oPassportService {

    private final Logger log = LoggerFactory.getLogger(Uo2oPassportService.class);

    private final Uo2oPassportRepository uo2oPassportRepository;

    private final Uo2oPassportSearchRepository uo2oPassportSearchRepository;

    public Uo2oPassportService(Uo2oPassportRepository uo2oPassportRepository, Uo2oPassportSearchRepository uo2oPassportSearchRepository) {
        this.uo2oPassportRepository = uo2oPassportRepository;
        this.uo2oPassportSearchRepository = uo2oPassportSearchRepository;
    }

    /**
     * Save a uo2oPassport.
     *
     * @param uo2oPassport the entity to save.
     * @return the persisted entity.
     */
    public Uo2oPassport save(Uo2oPassport uo2oPassport) {
        log.debug("Request to save Uo2oPassport : {}", uo2oPassport);
        Uo2oPassport result = uo2oPassportRepository.save(uo2oPassport);
        uo2oPassportSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the uo2oPassports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Uo2oPassport> findAll(Pageable pageable) {
        log.debug("Request to get all Uo2oPassports");
        return uo2oPassportRepository.findAll(pageable);
    }


    /**
     * Get one uo2oPassport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Uo2oPassport> findOne(Long id) {
        log.debug("Request to get Uo2oPassport : {}", id);
        return uo2oPassportRepository.findById(id);
    }

    /**
     * Delete the uo2oPassport by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Uo2oPassport : {}", id);
        uo2oPassportRepository.deleteById(id);
        uo2oPassportSearchRepository.deleteById(id);
    }

    /**
     * Search for the uo2oPassport corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Uo2oPassport> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Uo2oPassports for query {}", query);
        return uo2oPassportSearchRepository.search(queryStringQuery(query), pageable);    }
}
