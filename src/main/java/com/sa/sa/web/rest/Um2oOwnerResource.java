package com.sa.sa.web.rest;

import com.sa.sa.domain.Um2oOwner;
import com.sa.sa.service.Um2oOwnerService;
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
 * REST controller for managing {@link com.sa.sa.domain.Um2oOwner}.
 */
@RestController
@RequestMapping("/api")
public class Um2oOwnerResource {

    private final Logger log = LoggerFactory.getLogger(Um2oOwnerResource.class);

    private static final String ENTITY_NAME = "um2oOwner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Um2oOwnerService um2oOwnerService;

    public Um2oOwnerResource(Um2oOwnerService um2oOwnerService) {
        this.um2oOwnerService = um2oOwnerService;
    }

    /**
     * {@code POST  /um-2-o-owners} : Create a new um2oOwner.
     *
     * @param um2oOwner the um2oOwner to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new um2oOwner, or with status {@code 400 (Bad Request)} if the um2oOwner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/um-2-o-owners")
    public ResponseEntity<Um2oOwner> createUm2oOwner(@RequestBody Um2oOwner um2oOwner) throws URISyntaxException {
        log.debug("REST request to save Um2oOwner : {}", um2oOwner);
        if (um2oOwner.getId() != null) {
            throw new BadRequestAlertException("A new um2oOwner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Um2oOwner result = um2oOwnerService.save(um2oOwner);
        return ResponseEntity.created(new URI("/api/um-2-o-owners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /um-2-o-owners} : Updates an existing um2oOwner.
     *
     * @param um2oOwner the um2oOwner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated um2oOwner,
     * or with status {@code 400 (Bad Request)} if the um2oOwner is not valid,
     * or with status {@code 500 (Internal Server Error)} if the um2oOwner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/um-2-o-owners")
    public ResponseEntity<Um2oOwner> updateUm2oOwner(@RequestBody Um2oOwner um2oOwner) throws URISyntaxException {
        log.debug("REST request to update Um2oOwner : {}", um2oOwner);
        if (um2oOwner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Um2oOwner result = um2oOwnerService.save(um2oOwner);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, um2oOwner.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /um-2-o-owners} : get all the um2oOwners.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of um2oOwners in body.
     */
    @GetMapping("/um-2-o-owners")
    public ResponseEntity<List<Um2oOwner>> getAllUm2oOwners(Pageable pageable) {
        log.debug("REST request to get a page of Um2oOwners");
        Page<Um2oOwner> page = um2oOwnerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /um-2-o-owners/:id} : get the "id" um2oOwner.
     *
     * @param id the id of the um2oOwner to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the um2oOwner, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/um-2-o-owners/{id}")
    public ResponseEntity<Um2oOwner> getUm2oOwner(@PathVariable Long id) {
        log.debug("REST request to get Um2oOwner : {}", id);
        Optional<Um2oOwner> um2oOwner = um2oOwnerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(um2oOwner);
    }

    /**
     * {@code DELETE  /um-2-o-owners/:id} : delete the "id" um2oOwner.
     *
     * @param id the id of the um2oOwner to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/um-2-o-owners/{id}")
    public ResponseEntity<Void> deleteUm2oOwner(@PathVariable Long id) {
        log.debug("REST request to delete Um2oOwner : {}", id);
        um2oOwnerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/um-2-o-owners?query=:query} : search for the um2oOwner corresponding
     * to the query.
     *
     * @param query the query of the um2oOwner search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/um-2-o-owners")
    public ResponseEntity<List<Um2oOwner>> searchUm2oOwners(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Um2oOwners for query {}", query);
        Page<Um2oOwner> page = um2oOwnerService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
