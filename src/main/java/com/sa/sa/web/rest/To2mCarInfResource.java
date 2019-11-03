package com.sa.sa.web.rest;

import com.sa.sa.domain.To2mCarInf;
import com.sa.sa.service.To2mCarInfService;
import com.sa.sa.web.rest.errors.BadRequestAlertException;
import com.sa.sa.service.dto.To2mCarInfCriteria;
import com.sa.sa.service.To2mCarInfQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.sa.sa.domain.To2mCarInf}.
 */
@RestController
@RequestMapping("/api")
public class To2mCarInfResource {

    private final Logger log = LoggerFactory.getLogger(To2mCarInfResource.class);

    private static final String ENTITY_NAME = "to2mCarInf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final To2mCarInfService to2mCarInfService;

    private final To2mCarInfQueryService to2mCarInfQueryService;

    public To2mCarInfResource(To2mCarInfService to2mCarInfService, To2mCarInfQueryService to2mCarInfQueryService) {
        this.to2mCarInfService = to2mCarInfService;
        this.to2mCarInfQueryService = to2mCarInfQueryService;
    }

    /**
     * {@code POST  /to-2-m-car-infs} : Create a new to2mCarInf.
     *
     * @param to2mCarInf the to2mCarInf to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new to2mCarInf, or with status {@code 400 (Bad Request)} if the to2mCarInf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/to-2-m-car-infs")
    public ResponseEntity<To2mCarInf> createTo2mCarInf(@RequestBody To2mCarInf to2mCarInf) throws URISyntaxException {
        log.debug("REST request to save To2mCarInf : {}", to2mCarInf);
        if (to2mCarInf.getId() != null) {
            throw new BadRequestAlertException("A new to2mCarInf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        To2mCarInf result = to2mCarInfService.save(to2mCarInf);
        return ResponseEntity.created(new URI("/api/to-2-m-car-infs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /to-2-m-car-infs} : Updates an existing to2mCarInf.
     *
     * @param to2mCarInf the to2mCarInf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated to2mCarInf,
     * or with status {@code 400 (Bad Request)} if the to2mCarInf is not valid,
     * or with status {@code 500 (Internal Server Error)} if the to2mCarInf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/to-2-m-car-infs")
    public ResponseEntity<To2mCarInf> updateTo2mCarInf(@RequestBody To2mCarInf to2mCarInf) throws URISyntaxException {
        log.debug("REST request to update To2mCarInf : {}", to2mCarInf);
        if (to2mCarInf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        To2mCarInf result = to2mCarInfService.save(to2mCarInf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, to2mCarInf.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /to-2-m-car-infs} : get all the to2mCarInfs.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of to2mCarInfs in body.
     */
    @GetMapping("/to-2-m-car-infs")
    public ResponseEntity<List<To2mCarInf>> getAllTo2mCarInfs(To2mCarInfCriteria criteria, Pageable pageable) {
        log.debug("REST request to get To2mCarInfs by criteria: {}", criteria);
        Page<To2mCarInf> page = to2mCarInfQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /to-2-m-car-infs/count} : count all the to2mCarInfs.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/to-2-m-car-infs/count")
    public ResponseEntity<Long> countTo2mCarInfs(To2mCarInfCriteria criteria) {
        log.debug("REST request to count To2mCarInfs by criteria: {}", criteria);
        return ResponseEntity.ok().body(to2mCarInfQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /to-2-m-car-infs/:id} : get the "id" to2mCarInf.
     *
     * @param id the id of the to2mCarInf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the to2mCarInf, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/to-2-m-car-infs/{id}")
    public ResponseEntity<To2mCarInf> getTo2mCarInf(@PathVariable Long id) {
        log.debug("REST request to get To2mCarInf : {}", id);
        Optional<To2mCarInf> to2mCarInf = to2mCarInfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(to2mCarInf);
    }

    /**
     * {@code DELETE  /to-2-m-car-infs/:id} : delete the "id" to2mCarInf.
     *
     * @param id the id of the to2mCarInf to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/to-2-m-car-infs/{id}")
    public ResponseEntity<Void> deleteTo2mCarInf(@PathVariable Long id) {
        log.debug("REST request to delete To2mCarInf : {}", id);
        to2mCarInfService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/to-2-m-car-infs?query=:query} : search for the to2mCarInf corresponding
     * to the query.
     *
     * @param query the query of the to2mCarInf search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/to-2-m-car-infs")
    public ResponseEntity<List<To2mCarInf>> searchTo2mCarInfs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of To2mCarInfs for query {}", query);
        Page<To2mCarInf> page = to2mCarInfService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
