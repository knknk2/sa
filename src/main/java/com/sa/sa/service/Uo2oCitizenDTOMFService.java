package com.sa.sa.service;

import com.sa.sa.domain.Uo2oCitizenDTOMF;
import com.sa.sa.repository.Uo2oCitizenDTOMFRepository;
import com.sa.sa.repository.Uo2oPassportDTOMFRepository;
import com.sa.sa.repository.search.Uo2oCitizenDTOMFSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Uo2oCitizenDTOMF}.
 */
@Service
@Transactional
public class Uo2oCitizenDTOMFService {

    private final Logger log = LoggerFactory.getLogger(Uo2oCitizenDTOMFService.class);

    private final Uo2oCitizenDTOMFRepository uo2oCitizenDTOMFRepository;

    private final Uo2oCitizenDTOMFSearchRepository uo2oCitizenDTOMFSearchRepository;

    private final Uo2oPassportDTOMFRepository uo2oPassportDTOMFRepository;

    public Uo2oCitizenDTOMFService(Uo2oCitizenDTOMFRepository uo2oCitizenDTOMFRepository, Uo2oCitizenDTOMFSearchRepository uo2oCitizenDTOMFSearchRepository, Uo2oPassportDTOMFRepository uo2oPassportDTOMFRepository) {
        this.uo2oCitizenDTOMFRepository = uo2oCitizenDTOMFRepository;
        this.uo2oCitizenDTOMFSearchRepository = uo2oCitizenDTOMFSearchRepository;
        this.uo2oPassportDTOMFRepository = uo2oPassportDTOMFRepository;
    }

    /**
     * Save a uo2oCitizenDTOMF.
     *
     * @param uo2oCitizenDTOMF the entity to save.
     * @return the persisted entity.
     */
    public Uo2oCitizenDTOMF save(Uo2oCitizenDTOMF uo2oCitizenDTOMF) {
        log.debug("Request to save Uo2oCitizenDTOMF : {}", uo2oCitizenDTOMF);
        long uo2oPassportDTOMFId = uo2oCitizenDTOMF.getUo2oPassportDTOMF().getId();
        uo2oPassportDTOMFRepository.findById(uo2oPassportDTOMFId).ifPresent(uo2oCitizenDTOMF::uo2oPassportDTOMF);
        Uo2oCitizenDTOMF result = uo2oCitizenDTOMFRepository.save(uo2oCitizenDTOMF);
        uo2oCitizenDTOMFSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the uo2oCitizenDTOMFS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Uo2oCitizenDTOMF> findAll(Pageable pageable) {
        log.debug("Request to get all Uo2oCitizenDTOMFS");
        return uo2oCitizenDTOMFRepository.findAll(pageable);
    }


    /**
     * Get one uo2oCitizenDTOMF by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Uo2oCitizenDTOMF> findOne(Long id) {
        log.debug("Request to get Uo2oCitizenDTOMF : {}", id);
        return uo2oCitizenDTOMFRepository.findById(id);
    }

    /**
     * Delete the uo2oCitizenDTOMF by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Uo2oCitizenDTOMF : {}", id);
        uo2oCitizenDTOMFRepository.deleteById(id);
        uo2oCitizenDTOMFSearchRepository.deleteById(id);
    }

    /**
     * Search for the uo2oCitizenDTOMF corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Uo2oCitizenDTOMF> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Uo2oCitizenDTOMFS for query {}", query);
        return uo2oCitizenDTOMFSearchRepository.search(queryStringQuery(query), pageable);    }
}
