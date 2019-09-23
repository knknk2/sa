package com.sa.sa.web.rest;

import com.sa.sa.domain.M2mDriverDTOMF;
import com.sa.sa.service.M2mDriverDTOMFService;
import com.sa.sa.web.rest.errors.BadRequestAlertException;
import com.sa.sa.service.dto.M2mDriverDTOMFCriteria;
import com.sa.sa.service.M2mDriverDTOMFQueryService;

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
 * REST controller for managing {@link com.sa.sa.domain.M2mDriverDTOMF}.
 */
@RestController
@RequestMapping("/api")
public class M2mDriverDTOMFResource {

    private final Logger log = LoggerFactory.getLogger(M2mDriverDTOMFResource.class);

    private static final String ENTITY_NAME = "m2mDriverDTOMF";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final M2mDriverDTOMFService m2mDriverDTOMFService;

    private final M2mDriverDTOMFQueryService m2mDriverDTOMFQueryService;

    public M2mDriverDTOMFResource(M2mDriverDTOMFService m2mDriverDTOMFService, M2mDriverDTOMFQueryService m2mDriverDTOMFQueryService) {
        this.m2mDriverDTOMFService = m2mDriverDTOMFService;
        this.m2mDriverDTOMFQueryService = m2mDriverDTOMFQueryService;
    }

    /**
     * {@code POST  /m-2-m-driver-dtomfs} : Create a new m2mDriverDTOMF.
     *
     * @param m2mDriverDTOMF the m2mDriverDTOMF to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new m2mDriverDTOMF, or with status {@code 400 (Bad Request)} if the m2mDriverDTOMF has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-2-m-driver-dtomfs")
    public ResponseEntity<M2mDriverDTOMF> createM2mDriverDTOMF(@RequestBody M2mDriverDTOMF m2mDriverDTOMF) throws URISyntaxException {
        log.debug("REST request to save M2mDriverDTOMF : {}", m2mDriverDTOMF);
        if (m2mDriverDTOMF.getId() != null) {
            throw new BadRequestAlertException("A new m2mDriverDTOMF cannot already have an ID", ENTITY_NAME, "idexists");
        }
        M2mDriverDTOMF result = m2mDriverDTOMFService.save(m2mDriverDTOMF);
        return ResponseEntity.created(new URI("/api/m-2-m-driver-dtomfs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-2-m-driver-dtomfs} : Updates an existing m2mDriverDTOMF.
     *
     * @param m2mDriverDTOMF the m2mDriverDTOMF to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated m2mDriverDTOMF,
     * or with status {@code 400 (Bad Request)} if the m2mDriverDTOMF is not valid,
     * or with status {@code 500 (Internal Server Error)} if the m2mDriverDTOMF couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-2-m-driver-dtomfs")
    public ResponseEntity<M2mDriverDTOMF> updateM2mDriverDTOMF(@RequestBody M2mDriverDTOMF m2mDriverDTOMF) throws URISyntaxException {
        log.debug("REST request to update M2mDriverDTOMF : {}", m2mDriverDTOMF);
        if (m2mDriverDTOMF.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        M2mDriverDTOMF result = m2mDriverDTOMFService.save(m2mDriverDTOMF);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, m2mDriverDTOMF.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-2-m-driver-dtomfs} : get all the m2mDriverDTOMFS.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of m2mDriverDTOMFS in body.
     */
    @GetMapping("/m-2-m-driver-dtomfs")
    public ResponseEntity<List<M2mDriverDTOMF>> getAllM2mDriverDTOMFS(M2mDriverDTOMFCriteria criteria, Pageable pageable) {
        log.debug("REST request to get M2mDriverDTOMFS by criteria: {}", criteria);
        Page<M2mDriverDTOMF> page = m2mDriverDTOMFQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /m-2-m-driver-dtomfs/count} : count all the m2mDriverDTOMFS.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/m-2-m-driver-dtomfs/count")
    public ResponseEntity<Long> countM2mDriverDTOMFS(M2mDriverDTOMFCriteria criteria) {
        log.debug("REST request to count M2mDriverDTOMFS by criteria: {}", criteria);
        return ResponseEntity.ok().body(m2mDriverDTOMFQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-2-m-driver-dtomfs/:id} : get the "id" m2mDriverDTOMF.
     *
     * @param id the id of the m2mDriverDTOMF to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the m2mDriverDTOMF, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-2-m-driver-dtomfs/{id}")
    public ResponseEntity<M2mDriverDTOMF> getM2mDriverDTOMF(@PathVariable Long id) {
        log.debug("REST request to get M2mDriverDTOMF : {}", id);
        Optional<M2mDriverDTOMF> m2mDriverDTOMF = m2mDriverDTOMFService.findOne(id);
        return ResponseUtil.wrapOrNotFound(m2mDriverDTOMF);
    }

    /**
     * {@code DELETE  /m-2-m-driver-dtomfs/:id} : delete the "id" m2mDriverDTOMF.
     *
     * @param id the id of the m2mDriverDTOMF to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-2-m-driver-dtomfs/{id}")
    public ResponseEntity<Void> deleteM2mDriverDTOMF(@PathVariable Long id) {
        log.debug("REST request to delete M2mDriverDTOMF : {}", id);
        m2mDriverDTOMFService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/m-2-m-driver-dtomfs?query=:query} : search for the m2mDriverDTOMF corresponding
     * to the query.
     *
     * @param query the query of the m2mDriverDTOMF search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/m-2-m-driver-dtomfs")
    public ResponseEntity<List<M2mDriverDTOMF>> searchM2mDriverDTOMFS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of M2mDriverDTOMFS for query {}", query);
        Page<M2mDriverDTOMF> page = m2mDriverDTOMFService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
