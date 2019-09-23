package com.sa.sa.service;

import com.sa.sa.domain.Um2oOwner;
import com.sa.sa.repository.Um2oOwnerRepository;
import com.sa.sa.repository.search.Um2oOwnerSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Um2oOwner}.
 */
@Service
@Transactional
public class Um2oOwnerService {

    private final Logger log = LoggerFactory.getLogger(Um2oOwnerService.class);

    private final Um2oOwnerRepository um2oOwnerRepository;

    private final Um2oOwnerSearchRepository um2oOwnerSearchRepository;

    public Um2oOwnerService(Um2oOwnerRepository um2oOwnerRepository, Um2oOwnerSearchRepository um2oOwnerSearchRepository) {
        this.um2oOwnerRepository = um2oOwnerRepository;
        this.um2oOwnerSearchRepository = um2oOwnerSearchRepository;
    }

    /**
     * Save a um2oOwner.
     *
     * @param um2oOwner the entity to save.
     * @return the persisted entity.
     */
    public Um2oOwner save(Um2oOwner um2oOwner) {
        log.debug("Request to save Um2oOwner : {}", um2oOwner);
        Um2oOwner result = um2oOwnerRepository.save(um2oOwner);
        um2oOwnerSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the um2oOwners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Um2oOwner> findAll(Pageable pageable) {
        log.debug("Request to get all Um2oOwners");
        return um2oOwnerRepository.findAll(pageable);
    }


    /**
     * Get one um2oOwner by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Um2oOwner> findOne(Long id) {
        log.debug("Request to get Um2oOwner : {}", id);
        return um2oOwnerRepository.findById(id);
    }

    /**
     * Delete the um2oOwner by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Um2oOwner : {}", id);
        um2oOwnerRepository.deleteById(id);
        um2oOwnerSearchRepository.deleteById(id);
    }

    /**
     * Search for the um2oOwner corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Um2oOwner> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Um2oOwners for query {}", query);
        return um2oOwnerSearchRepository.search(queryStringQuery(query), pageable);    }
}
