package com.sa.sa.web.rest;

import com.sa.sa.domain.To2mCar;
import com.sa.sa.service.To2mCarService;
import com.sa.sa.web.rest.errors.BadRequestAlertException;
import com.sa.sa.service.dto.To2mCarCriteria;
import com.sa.sa.service.To2mCarQueryService;

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
 * REST controller for managing {@link com.sa.sa.domain.To2mCar}.
 */
@RestController
@RequestMapping("/api")
public class To2mCarResource {

    private final Logger log = LoggerFactory.getLogger(To2mCarResource.class);

    private static final String ENTITY_NAME = "to2mCar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final To2mCarService to2mCarService;

    private final To2mCarQueryService to2mCarQueryService;

    public To2mCarResource(To2mCarService to2mCarService, To2mCarQueryService to2mCarQueryService) {
        this.to2mCarService = to2mCarService;
        this.to2mCarQueryService = to2mCarQueryService;
    }

    /**
     * {@code POST  /to-2-m-cars} : Create a new to2mCar.
     *
     * @param to2mCar the to2mCar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new to2mCar, or with status {@code 400 (Bad Request)} if the to2mCar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/to-2-m-cars")
    public ResponseEntity<To2mCar> createTo2mCar(@RequestBody To2mCar to2mCar) throws URISyntaxException {
        log.debug("REST request to save To2mCar : {}", to2mCar);
        if (to2mCar.getId() != null) {
            throw new BadRequestAlertException("A new to2mCar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        To2mCar result = to2mCarService.save(to2mCar);
        return ResponseEntity.created(new URI("/api/to-2-m-cars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /to-2-m-cars} : Updates an existing to2mCar.
     *
     * @param to2mCar the to2mCar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated to2mCar,
     * or with status {@code 400 (Bad Request)} if the to2mCar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the to2mCar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/to-2-m-cars")
    public ResponseEntity<To2mCar> updateTo2mCar(@RequestBody To2mCar to2mCar) throws URISyntaxException {
        log.debug("REST request to update To2mCar : {}", to2mCar);
        if (to2mCar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        To2mCar result = to2mCarService.save(to2mCar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, to2mCar.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /to-2-m-cars} : get all the to2mCars.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of to2mCars in body.
     */
    @GetMapping("/to-2-m-cars")
    public ResponseEntity<List<To2mCar>> getAllTo2mCars(To2mCarCriteria criteria, Pageable pageable) {
        log.debug("REST request to get To2mCars by criteria: {}", criteria);
        Page<To2mCar> page = to2mCarQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /to-2-m-cars/count} : count all the to2mCars.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/to-2-m-cars/count")
    public ResponseEntity<Long> countTo2mCars(To2mCarCriteria criteria) {
        log.debug("REST request to count To2mCars by criteria: {}", criteria);
        return ResponseEntity.ok().body(to2mCarQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /to-2-m-cars/:id} : get the "id" to2mCar.
     *
     * @param id the id of the to2mCar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the to2mCar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/to-2-m-cars/{id}")
    public ResponseEntity<To2mCar> getTo2mCar(@PathVariable Long id) {
        log.debug("REST request to get To2mCar : {}", id);
        Optional<To2mCar> to2mCar = to2mCarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(to2mCar);
    }

    /**
     * {@code DELETE  /to-2-m-cars/:id} : delete the "id" to2mCar.
     *
     * @param id the id of the to2mCar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/to-2-m-cars/{id}")
    public ResponseEntity<Void> deleteTo2mCar(@PathVariable Long id) {
        log.debug("REST request to delete To2mCar : {}", id);
        to2mCarService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/to-2-m-cars?query=:query} : search for the to2mCar corresponding
     * to the query.
     *
     * @param query the query of the to2mCar search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/to-2-m-cars")
    public ResponseEntity<List<To2mCar>> searchTo2mCars(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of To2mCars for query {}", query);
        Page<To2mCar> page = to2mCarService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
