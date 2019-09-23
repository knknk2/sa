package com.sa.sa.web.rest;

import com.sa.sa.domain.M2mCarDTOMF;
import com.sa.sa.service.M2mCarDTOMFService;
import com.sa.sa.web.rest.errors.BadRequestAlertException;
import com.sa.sa.service.dto.M2mCarDTOMFCriteria;
import com.sa.sa.service.M2mCarDTOMFQueryService;

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
 * REST controller for managing {@link com.sa.sa.domain.M2mCarDTOMF}.
 */
@RestController
@RequestMapping("/api")
public class M2mCarDTOMFResource {

    private final Logger log = LoggerFactory.getLogger(M2mCarDTOMFResource.class);

    private static final String ENTITY_NAME = "m2mCarDTOMF";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final M2mCarDTOMFService m2mCarDTOMFService;

    private final M2mCarDTOMFQueryService m2mCarDTOMFQueryService;

    public M2mCarDTOMFResource(M2mCarDTOMFService m2mCarDTOMFService, M2mCarDTOMFQueryService m2mCarDTOMFQueryService) {
        this.m2mCarDTOMFService = m2mCarDTOMFService;
        this.m2mCarDTOMFQueryService = m2mCarDTOMFQueryService;
    }

    /**
     * {@code POST  /m-2-m-car-dtomfs} : Create a new m2mCarDTOMF.
     *
     * @param m2mCarDTOMF the m2mCarDTOMF to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new m2mCarDTOMF, or with status {@code 400 (Bad Request)} if the m2mCarDTOMF has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/m-2-m-car-dtomfs")
    public ResponseEntity<M2mCarDTOMF> createM2mCarDTOMF(@RequestBody M2mCarDTOMF m2mCarDTOMF) throws URISyntaxException {
        log.debug("REST request to save M2mCarDTOMF : {}", m2mCarDTOMF);
        if (m2mCarDTOMF.getId() != null) {
            throw new BadRequestAlertException("A new m2mCarDTOMF cannot already have an ID", ENTITY_NAME, "idexists");
        }
        M2mCarDTOMF result = m2mCarDTOMFService.save(m2mCarDTOMF);
        return ResponseEntity.created(new URI("/api/m-2-m-car-dtomfs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /m-2-m-car-dtomfs} : Updates an existing m2mCarDTOMF.
     *
     * @param m2mCarDTOMF the m2mCarDTOMF to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated m2mCarDTOMF,
     * or with status {@code 400 (Bad Request)} if the m2mCarDTOMF is not valid,
     * or with status {@code 500 (Internal Server Error)} if the m2mCarDTOMF couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/m-2-m-car-dtomfs")
    public ResponseEntity<M2mCarDTOMF> updateM2mCarDTOMF(@RequestBody M2mCarDTOMF m2mCarDTOMF) throws URISyntaxException {
        log.debug("REST request to update M2mCarDTOMF : {}", m2mCarDTOMF);
        if (m2mCarDTOMF.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        M2mCarDTOMF result = m2mCarDTOMFService.save(m2mCarDTOMF);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, m2mCarDTOMF.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /m-2-m-car-dtomfs} : get all the m2mCarDTOMFS.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of m2mCarDTOMFS in body.
     */
    @GetMapping("/m-2-m-car-dtomfs")
    public ResponseEntity<List<M2mCarDTOMF>> getAllM2mCarDTOMFS(M2mCarDTOMFCriteria criteria, Pageable pageable) {
        log.debug("REST request to get M2mCarDTOMFS by criteria: {}", criteria);
        Page<M2mCarDTOMF> page = m2mCarDTOMFQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /m-2-m-car-dtomfs/count} : count all the m2mCarDTOMFS.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/m-2-m-car-dtomfs/count")
    public ResponseEntity<Long> countM2mCarDTOMFS(M2mCarDTOMFCriteria criteria) {
        log.debug("REST request to count M2mCarDTOMFS by criteria: {}", criteria);
        return ResponseEntity.ok().body(m2mCarDTOMFQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /m-2-m-car-dtomfs/:id} : get the "id" m2mCarDTOMF.
     *
     * @param id the id of the m2mCarDTOMF to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the m2mCarDTOMF, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/m-2-m-car-dtomfs/{id}")
    public ResponseEntity<M2mCarDTOMF> getM2mCarDTOMF(@PathVariable Long id) {
        log.debug("REST request to get M2mCarDTOMF : {}", id);
        Optional<M2mCarDTOMF> m2mCarDTOMF = m2mCarDTOMFService.findOne(id);
        return ResponseUtil.wrapOrNotFound(m2mCarDTOMF);
    }

    /**
     * {@code DELETE  /m-2-m-car-dtomfs/:id} : delete the "id" m2mCarDTOMF.
     *
     * @param id the id of the m2mCarDTOMF to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/m-2-m-car-dtomfs/{id}")
    public ResponseEntity<Void> deleteM2mCarDTOMF(@PathVariable Long id) {
        log.debug("REST request to delete M2mCarDTOMF : {}", id);
        m2mCarDTOMFService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/m-2-m-car-dtomfs?query=:query} : search for the m2mCarDTOMF corresponding
     * to the query.
     *
     * @param query the query of the m2mCarDTOMF search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/m-2-m-car-dtomfs")
    public ResponseEntity<List<M2mCarDTOMF>> searchM2mCarDTOMFS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of M2mCarDTOMFS for query {}", query);
        Page<M2mCarDTOMF> page = m2mCarDTOMFService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
