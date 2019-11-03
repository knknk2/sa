package com.sa.sa.web.rest;

import com.sa.sa.domain.M2mDriver;
import com.sa.sa.service.M2mDriverService;
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
 * REST controller for managing {@link com.sa.sa.domain.M2mDriver}.
 */
@RestController
@RequestMapping("/api")
public class M2mDriverResource {

    private final Logger log = LoggerFactory.getLogger(M2mDriverResource.class);

    private static final String ENTITY_NAME = "m2mDriver";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final M2mDriverService m2mDriverService;

    public M2mDriverResource(M2mDriverService m2mDriverService) {
        this.m2mDriverService = m2mDriverService;
    }

    /**
     * {@code POST  /m-2-m-drivers} : Create a new m2mDriver.
     *
     * @param m2mDriver the m2mDriver to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new m2mDriver, or with status {@code 400 (Bad Request)} if the m2mDriver has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-2-m-drivers")
    public ResponseEntity<M2mDriver> createM2mDriver(@RequestBody M2mDriver m2mDriver) throws URISyntaxException {
        log.debug("REST request to save M2mDriver : {}", m2mDriver);
        if (m2mDriver.getId() != null) {
            throw new BadRequestAlertException("A new m2mDriver cannot already have an ID", ENTITY_NAME, "idexists");
        }
        M2mDriver result = m2mDriverService.save(m2mDriver);
        return ResponseEntity.created(new URI("/api/m-2-m-drivers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-2-m-drivers} : Updates an existing m2mDriver.
     *
     * @param m2mDriver the m2mDriver to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated m2mDriver,
     * or with status {@code 400 (Bad Request)} if the m2mDriver is not valid,
     * or with status {@code 500 (Internal Server Error)} if the m2mDriver couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-2-m-drivers")
    public ResponseEntity<M2mDriver> updateM2mDriver(@RequestBody M2mDriver m2mDriver) throws URISyntaxException {
        log.debug("REST request to update M2mDriver : {}", m2mDriver);
        if (m2mDriver.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        M2mDriver result = m2mDriverService.save(m2mDriver);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, m2mDriver.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-2-m-drivers} : get all the m2mDrivers.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of m2mDrivers in body.
     */
    @GetMapping("/m-2-m-drivers")
    public ResponseEntity<List<M2mDriver>> getAllM2mDrivers(Pageable pageable) {
        log.debug("REST request to get a page of M2mDrivers");
        Page<M2mDriver> page = m2mDriverService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-2-m-drivers/:id} : get the "id" m2mDriver.
     *
     * @param id the id of the m2mDriver to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the m2mDriver, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-2-m-drivers/{id}")
    public ResponseEntity<M2mDriver> getM2mDriver(@PathVariable Long id) {
        log.debug("REST request to get M2mDriver : {}", id);
        Optional<M2mDriver> m2mDriver = m2mDriverService.findOne(id);
        return ResponseUtil.wrapOrNotFound(m2mDriver);
    }

    /**
     * {@code DELETE  /m-2-m-drivers/:id} : delete the "id" m2mDriver.
     *
     * @param id the id of the m2mDriver to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-2-m-drivers/{id}")
    public ResponseEntity<Void> deleteM2mDriver(@PathVariable Long id) {
        log.debug("REST request to delete M2mDriver : {}", id);
        m2mDriverService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/m-2-m-drivers?query=:query} : search for the m2mDriver corresponding
     * to the query.
     *
     * @param query the query of the m2mDriver search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/m-2-m-drivers")
    public ResponseEntity<List<M2mDriver>> searchM2mDrivers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of M2mDrivers for query {}", query);
        Page<M2mDriver> page = m2mDriverService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
