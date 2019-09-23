package com.sa.sa.web.rest;

import com.sa.sa.service.FieldsService;
import com.sa.sa.web.rest.errors.BadRequestAlertException;
import com.sa.sa.service.dto.FieldsDTO;
import com.sa.sa.service.dto.FieldsCriteria;
import com.sa.sa.service.FieldsQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.sa.sa.domain.Fields}.
 */
@RestController
@RequestMapping("/api")
public class FieldsResource {

    private final Logger log = LoggerFactory.getLogger(FieldsResource.class);

    private static final String ENTITY_NAME = "fields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldsService fieldsService;

    private final FieldsQueryService fieldsQueryService;

    public FieldsResource(FieldsService fieldsService, FieldsQueryService fieldsQueryService) {
        this.fieldsService = fieldsService;
        this.fieldsQueryService = fieldsQueryService;
    }

    /**
     * {@code POST  /fields} : Create a new fields.
     *
     * @param fieldsDTO the fieldsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldsDTO, or with status {@code 400 (Bad Request)} if the fields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fields")
    public ResponseEntity<FieldsDTO> createFields(@Valid @RequestBody FieldsDTO fieldsDTO) throws URISyntaxException {
        log.debug("REST request to save Fields : {}", fieldsDTO);
        if (fieldsDTO.getId() != null) {
            throw new BadRequestAlertException("A new fields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldsDTO result = fieldsService.save(fieldsDTO);
        return ResponseEntity.created(new URI("/api/fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fields} : Updates an existing fields.
     *
     * @param fieldsDTO the fieldsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldsDTO,
     * or with status {@code 400 (Bad Request)} if the fieldsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fields")
    public ResponseEntity<FieldsDTO> updateFields(@Valid @RequestBody FieldsDTO fieldsDTO) throws URISyntaxException {
        log.debug("REST request to update Fields : {}", fieldsDTO);
        if (fieldsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FieldsDTO result = fieldsService.save(fieldsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fieldsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fields} : get all the fields.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fields in body.
     */
    @GetMapping("/fields")
    public ResponseEntity<List<FieldsDTO>> getAllFields(FieldsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Fields by criteria: {}", criteria);
        Page<FieldsDTO> page = fieldsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /fields/count} : count all the fields.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/fields/count")
    public ResponseEntity<Long> countFields(FieldsCriteria criteria) {
        log.debug("REST request to count Fields by criteria: {}", criteria);
        return ResponseEntity.ok().body(fieldsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fields/:id} : get the "id" fields.
     *
     * @param id the id of the fieldsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fields/{id}")
    public ResponseEntity<FieldsDTO> getFields(@PathVariable Long id) {
        log.debug("REST request to get Fields : {}", id);
        Optional<FieldsDTO> fieldsDTO = fieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldsDTO);
    }

    /**
     * {@code DELETE  /fields/:id} : delete the "id" fields.
     *
     * @param id the id of the fieldsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fields/{id}")
    public ResponseEntity<Void> deleteFields(@PathVariable Long id) {
        log.debug("REST request to delete Fields : {}", id);
        fieldsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/fields?query=:query} : search for the fields corresponding
     * to the query.
     *
     * @param query the query of the fields search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/fields")
    public ResponseEntity<List<FieldsDTO>> searchFields(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Fields for query {}", query);
        Page<FieldsDTO> page = fieldsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
