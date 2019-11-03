package com.sa.sa.web.rest;

import com.sa.sa.domain.M2mCar;
import com.sa.sa.service.M2mCarService;
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
 * REST controller for managing {@link com.sa.sa.domain.M2mCar}.
 */
@RestController
@RequestMapping("/api")
public class M2mCarResource {

    private final Logger log = LoggerFactory.getLogger(M2mCarResource.class);

    private static final String ENTITY_NAME = "m2mCar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final M2mCarService m2mCarService;

    public M2mCarResource(M2mCarService m2mCarService) {
        this.m2mCarService = m2mCarService;
    }

    /**
     * {@code POST  /m-2-m-cars} : Create a new m2mCar.
     *
     * @param m2mCar the m2mCar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new m2mCar, or with status {@code 400 (Bad Request)} if the m2mCar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-2-m-cars")
    public ResponseEntity<M2mCar> createM2mCar(@RequestBody M2mCar m2mCar) throws URISyntaxException {
        log.debug("REST request to save M2mCar : {}", m2mCar);
        if (m2mCar.getId() != null) {
            throw new BadRequestAlertException("A new m2mCar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        M2mCar result = m2mCarService.save(m2mCar);
        return ResponseEntity.created(new URI("/api/m-2-m-cars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-2-m-cars} : Updates an existing m2mCar.
     *
     * @param m2mCar the m2mCar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated m2mCar,
     * or with status {@code 400 (Bad Request)} if the m2mCar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the m2mCar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-2-m-cars")
    public ResponseEntity<M2mCar> updateM2mCar(@RequestBody M2mCar m2mCar) throws URISyntaxException {
        log.debug("REST request to update M2mCar : {}", m2mCar);
        if (m2mCar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        M2mCar result = m2mCarService.save(m2mCar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, m2mCar.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-2-m-cars} : get all the m2mCars.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of m2mCars in body.
     */
    @GetMapping("/m-2-m-cars")
    public ResponseEntity<List<M2mCar>> getAllM2mCars(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of M2mCars");
        Page<M2mCar> page;
        if (eagerload) {
            page = m2mCarService.findAllWithEagerRelationships(pageable);
        } else {
            page = m2mCarService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /m-2-m-cars/:id} : get the "id" m2mCar.
     *
     * @param id the id of the m2mCar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the m2mCar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-2-m-cars/{id}")
    public ResponseEntity<M2mCar> getM2mCar(@PathVariable Long id) {
        log.debug("REST request to get M2mCar : {}", id);
        Optional<M2mCar> m2mCar = m2mCarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(m2mCar);
    }

    /**
     * {@code DELETE  /m-2-m-cars/:id} : delete the "id" m2mCar.
     *
     * @param id the id of the m2mCar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-2-m-cars/{id}")
    public ResponseEntity<Void> deleteM2mCar(@PathVariable Long id) {
        log.debug("REST request to delete M2mCar : {}", id);
        m2mCarService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/m-2-m-cars?query=:query} : search for the m2mCar corresponding
     * to the query.
     *
     * @param query the query of the m2mCar search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/m-2-m-cars")
    public ResponseEntity<List<M2mCar>> searchM2mCars(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of M2mCars for query {}", query);
        Page<M2mCar> page = m2mCarService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
