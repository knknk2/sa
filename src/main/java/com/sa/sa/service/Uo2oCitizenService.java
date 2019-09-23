package com.sa.sa.service;

import com.sa.sa.domain.Uo2oCitizen;
import com.sa.sa.repository.Uo2oCitizenRepository;
import com.sa.sa.repository.Uo2oPassportRepository;
import com.sa.sa.repository.search.Uo2oCitizenSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Uo2oCitizen}.
 */
@Service
@Transactional
public class Uo2oCitizenService {

    private final Logger log = LoggerFactory.getLogger(Uo2oCitizenService.class);

    private final Uo2oCitizenRepository uo2oCitizenRepository;

    private final Uo2oCitizenSearchRepository uo2oCitizenSearchRepository;

    private final Uo2oPassportRepository uo2oPassportRepository;

    public Uo2oCitizenService(Uo2oCitizenRepository uo2oCitizenRepository, Uo2oCitizenSearchRepository uo2oCitizenSearchRepository, Uo2oPassportRepository uo2oPassportRepository) {
        this.uo2oCitizenRepository = uo2oCitizenRepository;
        this.uo2oCitizenSearchRepository = uo2oCitizenSearchRepository;
        this.uo2oPassportRepository = uo2oPassportRepository;
    }

    /**
     * Save a uo2oCitizen.
     *
     * @param uo2oCitizen the entity to save.
     * @return the persisted entity.
     */
    public Uo2oCitizen save(Uo2oCitizen uo2oCitizen) {
        log.debug("Request to save Uo2oCitizen : {}", uo2oCitizen);
        Long uo2oPassportId = uo2oCitizen.getUo2oPassport().getId();
        uo2oPassportRepository.findById(uo2oPassportId).ifPresent(uo2oCitizen::uo2oPassport);
        Uo2oCitizen result = uo2oCitizenRepository.save(uo2oCitizen);
        uo2oCitizenSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the uo2oCitizens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Uo2oCitizen> findAll(Pageable pageable) {
        log.debug("Request to get all Uo2oCitizens");
        return uo2oCitizenRepository.findAll(pageable);
    }


    /**
     * Get one uo2oCitizen by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Uo2oCitizen> findOne(Long id) {
        log.debug("Request to get Uo2oCitizen : {}", id);
        return uo2oCitizenRepository.findById(id);
    }

    /**
     * Delete the uo2oCitizen by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Uo2oCitizen : {}", id);
        uo2oCitizenRepository.deleteById(id);
        uo2oCitizenSearchRepository.deleteById(id);
    }

    /**
     * Search for the uo2oCitizen corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Uo2oCitizen> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Uo2oCitizens for query {}", query);
        return uo2oCitizenSearchRepository.search(queryStringQuery(query), pageable);    }
}
