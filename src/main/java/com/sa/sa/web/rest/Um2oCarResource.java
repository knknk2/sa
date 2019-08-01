package com.sa.sa.web.rest;

import com.sa.sa.domain.Um2oCar;
import com.sa.sa.service.Um2oCarService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.sa.sa.domain.Um2oCar}.
 */
@RestController
@RequestMapping("/api")
public class Um2oCarResource {

    private final Logger log = LoggerFactory.getLogger(Um2oCarResource.class);

    private static final String ENTITY_NAME = "um2oCar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Um2oCarService um2oCarService;

    public Um2oCarResource(Um2oCarService um2oCarService) {
        this.um2oCarService = um2oCarService;
    }

    /**
     * {@code POST  /um-2-o-cars} : Create a new um2oCar.
     *
     * @param um2oCar the um2oCar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new um2oCar, or with status {@code 400 (Bad Request)} if the um2oCar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/um-2-o-cars")
    public ResponseEntity<Um2oCar> createUm2oCar(@Valid @RequestBody Um2oCar um2oCar) throws URISyntaxException {
        log.debug("REST request to save Um2oCar : {}", um2oCar);
        if (um2oCar.getId() != null) {
            throw new BadRequestAlertException("A new um2oCar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Um2oCar result = um2oCarService.save(um2oCar);
        return ResponseEntity.created(new URI("/api/um-2-o-cars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /um-2-o-cars} : Updates an existing um2oCar.
     *
     * @param um2oCar the um2oCar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated um2oCar,
     * or with status {@code 400 (Bad Request)} if the um2oCar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the um2oCar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/um-2-o-cars")
    public ResponseEntity<Um2oCar> updateUm2oCar(@Valid @RequestBody Um2oCar um2oCar) throws URISyntaxException {
        log.debug("REST request to update Um2oCar : {}", um2oCar);
        if (um2oCar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Um2oCar result = um2oCarService.save(um2oCar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, um2oCar.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /um-2-o-cars} : get all the um2oCars.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of um2oCars in body.
     */
    @GetMapping("/um-2-o-cars")
    public ResponseEntity<List<Um2oCar>> getAllUm2oCars(Pageable pageable) {
        log.debug("REST request to get a page of Um2oCars");
        Page<Um2oCar> page = um2oCarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /um-2-o-cars/:id} : get the "id" um2oCar.
     *
     * @param id the id of the um2oCar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the um2oCar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/um-2-o-cars/{id}")
    public ResponseEntity<Um2oCar> getUm2oCar(@PathVariable Long id) {
        log.debug("REST request to get Um2oCar : {}", id);
        Optional<Um2oCar> um2oCar = um2oCarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(um2oCar);
    }

    /**
     * {@code DELETE  /um-2-o-cars/:id} : delete the "id" um2oCar.
     *
     * @param id the id of the um2oCar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/um-2-o-cars/{id}")
    public ResponseEntity<Void> deleteUm2oCar(@PathVariable Long id) {
        log.debug("REST request to delete Um2oCar : {}", id);
        um2oCarService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/um-2-o-cars?query=:query} : search for the um2oCar corresponding
     * to the query.
     *
     * @param query the query of the um2oCar search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/um-2-o-cars")
    public ResponseEntity<List<Um2oCar>> searchUm2oCars(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Um2oCars for query {}", query);
        Page<Um2oCar> page = um2oCarService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
