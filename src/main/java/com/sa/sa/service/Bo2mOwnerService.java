package com.sa.sa.service;

import com.sa.sa.domain.Bo2mOwner;
import com.sa.sa.repository.Bo2mOwnerRepository;
import com.sa.sa.repository.search.Bo2mOwnerSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Bo2mOwner}.
 */
@Service
@Transactional
public class Bo2mOwnerService {

    private final Logger log = LoggerFactory.getLogger(Bo2mOwnerService.class);

    private final Bo2mOwnerRepository bo2mOwnerRepository;

    private final Bo2mOwnerSearchRepository bo2mOwnerSearchRepository;

    public Bo2mOwnerService(Bo2mOwnerRepository bo2mOwnerRepository, Bo2mOwnerSearchRepository bo2mOwnerSearchRepository) {
        this.bo2mOwnerRepository = bo2mOwnerRepository;
        this.bo2mOwnerSearchRepository = bo2mOwnerSearchRepository;
    }

    /**
     * Save a bo2mOwner.
     *
     * @param bo2mOwner the entity to save.
     * @return the persisted entity.
     */
    public Bo2mOwner save(Bo2mOwner bo2mOwner) {
        log.debug("Request to save Bo2mOwner : {}", bo2mOwner);
        Bo2mOwner result = bo2mOwnerRepository.save(bo2mOwner);
        bo2mOwnerSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the bo2mOwners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Bo2mOwner> findAll(Pageable pageable) {
        log.debug("Request to get all Bo2mOwners");
        return bo2mOwnerRepository.findAll(pageable);
    }


    /**
     * Get one bo2mOwner by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Bo2mOwner> findOne(Long id) {
        log.debug("Request to get Bo2mOwner : {}", id);
        return bo2mOwnerRepository.findById(id);
    }

    /**
     * Delete the bo2mOwner by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Bo2mOwner : {}", id);
        bo2mOwnerRepository.deleteById(id);
        bo2mOwnerSearchRepository.deleteById(id);
    }

    /**
     * Search for the bo2mOwner corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Bo2mOwner> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Bo2mOwners for query {}", query);
        return bo2mOwnerSearchRepository.search(queryStringQuery(query), pageable);    }
}
