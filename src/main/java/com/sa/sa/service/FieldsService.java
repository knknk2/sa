package com.sa.sa.service;

import com.sa.sa.domain.Fields;
import com.sa.sa.repository.FieldsRepository;
import com.sa.sa.repository.search.FieldsSearchRepository;
import com.sa.sa.service.dto.FieldsDTO;
import com.sa.sa.service.mapper.FieldsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Fields}.
 */
@Service
@Transactional
public class FieldsService {

    private final Logger log = LoggerFactory.getLogger(FieldsService.class);

    private final FieldsRepository fieldsRepository;

    private final FieldsMapper fieldsMapper;

    private final FieldsSearchRepository fieldsSearchRepository;

    public FieldsService(FieldsRepository fieldsRepository, FieldsMapper fieldsMapper, FieldsSearchRepository fieldsSearchRepository) {
        this.fieldsRepository = fieldsRepository;
        this.fieldsMapper = fieldsMapper;
        this.fieldsSearchRepository = fieldsSearchRepository;
    }

    /**
     * Save a fields.
     *
     * @param fieldsDTO the entity to save.
     * @return the persisted entity.
     */
    public FieldsDTO save(FieldsDTO fieldsDTO) {
        log.debug("Request to save Fields : {}", fieldsDTO);
        Fields fields = fieldsMapper.toEntity(fieldsDTO);
        fields = fieldsRepository.save(fields);
        FieldsDTO result = fieldsMapper.toDto(fields);
        fieldsSearchRepository.save(fields);
        return result;
    }

    /**
     * Get all the fields.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FieldsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fields");
        return fieldsRepository.findAll(pageable)
            .map(fieldsMapper::toDto);
    }


    /**
     * Get one fields by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FieldsDTO> findOne(Long id) {
        log.debug("Request to get Fields : {}", id);
        return fieldsRepository.findById(id)
            .map(fieldsMapper::toDto);
    }

    /**
     * Delete the fields by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Fields : {}", id);
        fieldsRepository.deleteById(id);
        fieldsSearchRepository.deleteById(id);
    }

    /**
     * Search for the fields corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FieldsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Fields for query {}", query);
        return fieldsSearchRepository.search(queryStringQuery(query), pageable)
            .map(fieldsMapper::toDto);
    }
}
