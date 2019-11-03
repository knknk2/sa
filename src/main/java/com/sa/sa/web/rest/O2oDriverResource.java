package com.sa.sa.web.rest;

import com.sa.sa.domain.O2oDriver;
import com.sa.sa.service.O2oDriverService;
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
 * REST controller for managing {@link com.sa.sa.domain.O2oDriver}.
 */
@RestController
@RequestMapping("/api")
public class O2oDriverResource {

    private final Logger log = LoggerFactory.getLogger(O2oDriverResource.class);

    private static final String ENTITY_NAME = "o2oDriver";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final O2oDriverService o2oDriverService;

    public O2oDriverResource(O2oDriverService o2oDriverService) {
        this.o2oDriverService = o2oDriverService;
    }

    /**
     * {@code POST  /o-2-o-drivers} : Create a new o2oDriver.
     *
     * @param o2oDriver the o2oDriver to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new o2oDriver, or with status {@code 400 (Bad Request)} if the o2oDriver has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/o-2-o-drivers")
    public ResponseEntity<O2oDriver> createO2oDriver(@RequestBody O2oDriver o2oDriver) throws URISyntaxException {
        log.debug("REST request to save O2oDriver : {}", o2oDriver);
        if (o2oDriver.getId() != null) {
            throw new BadRequestAlertException("A new o2oDriver cannot already have an ID", ENTITY_NAME, "idexists");
        }
        O2oDriver result = o2oDriverService.save(o2oDriver);
        return ResponseEntity.created(new URI("/api/o-2-o-drivers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /o-2-o-drivers} : Updates an existing o2oDriver.
     *
     * @param o2oDriver the o2oDriver to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated o2oDriver,
     * or with status {@code 400 (Bad Request)} if the o2oDriver is not valid,
     * or with status {@code 500 (Internal Server Error)} if the o2oDriver couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/o-2-o-drivers")
    public ResponseEntity<O2oDriver> updateO2oDriver(@RequestBody O2oDriver o2oDriver) throws URISyntaxException {
        log.debug("REST request to update O2oDriver : {}", o2oDriver);
        if (o2oDriver.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        O2oDriver result = o2oDriverService.save(o2oDriver);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, o2oDriver.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /o-2-o-drivers} : get all the o2oDrivers.
     *

     * @param pageable the pagination information.

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of o2oDrivers in body.
     */
    @GetMapping("/o-2-o-drivers")
    public ResponseEntity<List<O2oDriver>> getAllO2oDrivers(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("o2ocar-is-null".equals(filter)) {
            log.debug("REST request to get all O2oDrivers where o2oCar is null");
            return new ResponseEntity<>(o2oDriverService.findAllWhereO2oCarIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of O2oDrivers");
        Page<O2oDriver> page = o2oDriverService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /o-2-o-drivers/:id} : get the "id" o2oDriver.
     *
     * @param id the id of the o2oDriver to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the o2oDriver, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/o-2-o-drivers/{id}")
    public ResponseEntity<O2oDriver> getO2oDriver(@PathVariable Long id) {
        log.debug("REST request to get O2oDriver : {}", id);
        Optional<O2oDriver> o2oDriver = o2oDriverService.findOne(id);
        return ResponseUtil.wrapOrNotFound(o2oDriver);
    }

    /**
     * {@code DELETE  /o-2-o-drivers/:id} : delete the "id" o2oDriver.
     *
     * @param id the id of the o2oDriver to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/o-2-o-drivers/{id}")
    public ResponseEntity<Void> deleteO2oDriver(@PathVariable Long id) {
        log.debug("REST request to delete O2oDriver : {}", id);
        o2oDriverService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/o-2-o-drivers?query=:query} : search for the o2oDriver corresponding
     * to the query.
     *
     * @param query the query of the o2oDriver search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/o-2-o-drivers")
    public ResponseEntity<List<O2oDriver>> searchO2oDrivers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of O2oDrivers for query {}", query);
        Page<O2oDriver> page = o2oDriverService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
