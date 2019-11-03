package com.sa.sa.web.rest;

import com.sa.sa.domain.To2mPersonInf;
import com.sa.sa.service.To2mPersonInfService;
import com.sa.sa.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link com.sa.sa.domain.To2mPersonInf}.
 */
@RestController
@RequestMapping("/api")
public class To2mPersonInfResource {

    private final Logger log = LoggerFactory.getLogger(To2mPersonInfResource.class);

    private static final String ENTITY_NAME = "to2mPersonInf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final To2mPersonInfService to2mPersonInfService;

    public To2mPersonInfResource(To2mPersonInfService to2mPersonInfService) {
        this.to2mPersonInfService = to2mPersonInfService;
    }

    /**
     * {@code POST  /to-2-m-person-infs} : Create a new to2mPersonInf.
     *
     * @param to2mPersonInf the to2mPersonInf to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new to2mPersonInf, or with status {@code 400 (Bad Request)} if the to2mPersonInf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/to-2-m-person-infs")
    public ResponseEntity<To2mPersonInf> createTo2mPersonInf(@RequestBody To2mPersonInf to2mPersonInf) throws URISyntaxException {
        log.debug("REST request to save To2mPersonInf : {}", to2mPersonInf);
        if (to2mPersonInf.getId() != null) {
            throw new BadRequestAlertException("A new to2mPersonInf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        To2mPersonInf result = to2mPersonInfService.save(to2mPersonInf);
        return ResponseEntity.created(new URI("/api/to-2-m-person-infs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /to-2-m-person-infs} : Updates an existing to2mPersonInf.
     *
     * @param to2mPersonInf the to2mPersonInf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated to2mPersonInf,
     * or with status {@code 400 (Bad Request)} if the to2mPersonInf is not valid,
     * or with status {@code 500 (Internal Server Error)} if the to2mPersonInf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/to-2-m-person-infs")
    public ResponseEntity<To2mPersonInf> updateTo2mPersonInf(@RequestBody To2mPersonInf to2mPersonInf) throws URISyntaxException {
        log.debug("REST request to update To2mPersonInf : {}", to2mPersonInf);
        if (to2mPersonInf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        To2mPersonInf result = to2mPersonInfService.save(to2mPersonInf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, to2mPersonInf.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /to-2-m-person-infs} : get all the to2mPersonInfs.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of to2mPersonInfs in body.
     */
    @GetMapping("/to-2-m-person-infs")
    public ResponseEntity<List<To2mPersonInf>> getAllTo2mPersonInfs(Pageable pageable) {
        log.debug("REST request to get a page of To2mPersonInfs");
        Page<To2mPersonInf> page = to2mPersonInfService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /to-2-m-person-infs/:id} : get the "id" to2mPersonInf.
     *
     * @param id the id of the to2mPersonInf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the to2mPersonInf, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/to-2-m-person-infs/{id}")
    public ResponseEntity<To2mPersonInf> getTo2mPersonInf(@PathVariable Long id) {
        log.debug("REST request to get To2mPersonInf : {}", id);
        Optional<To2mPersonInf> to2mPersonInf = to2mPersonInfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(to2mPersonInf);
    }

    /**
     * {@code DELETE  /to-2-m-person-infs/:id} : delete the "id" to2mPersonInf.
     *
     * @param id the id of the to2mPersonInf to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/to-2-m-person-infs/{id}")
    public ResponseEntity<Void> deleteTo2mPersonInf(@PathVariable Long id) {
        log.debug("REST request to delete To2mPersonInf : {}", id);
        to2mPersonInfService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/to-2-m-person-infs?query=:query} : search for the to2mPersonInf corresponding
     * to the query.
     *
     * @param query the query of the to2mPersonInf search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/to-2-m-person-infs")
    public ResponseEntity<List<To2mPersonInf>> searchTo2mPersonInfs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of To2mPersonInfs for query {}", query);
        Page<To2mPersonInf> page = to2mPersonInfService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
