package com.sa.sa.web.rest;

import com.sa.sa.domain.Uo2oPassportDTOMF;
import com.sa.sa.service.Uo2oPassportDTOMFService;
import com.sa.sa.web.rest.errors.BadRequestAlertException;
import com.sa.sa.service.dto.Uo2oPassportDTOMFCriteria;
import com.sa.sa.service.Uo2oPassportDTOMFQueryService;

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
 * REST controller for managing {@link com.sa.sa.domain.Uo2oPassportDTOMF}.
 */
@RestController
@RequestMapping("/api")
public class Uo2oPassportDTOMFResource {

    private final Logger log = LoggerFactory.getLogger(Uo2oPassportDTOMFResource.class);

    private static final String ENTITY_NAME = "uo2oPassportDTOMF";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Uo2oPassportDTOMFService uo2oPassportDTOMFService;

    private final Uo2oPassportDTOMFQueryService uo2oPassportDTOMFQueryService;

    public Uo2oPassportDTOMFResource(Uo2oPassportDTOMFService uo2oPassportDTOMFService, Uo2oPassportDTOMFQueryService uo2oPassportDTOMFQueryService) {
        this.uo2oPassportDTOMFService = uo2oPassportDTOMFService;
        this.uo2oPassportDTOMFQueryService = uo2oPassportDTOMFQueryService;
    }

    /**
     * {@code POST  /uo-2-o-passport-dtomfs} : Create a new uo2oPassportDTOMF.
     *
     * @param uo2oPassportDTOMF the uo2oPassportDTOMF to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uo2oPassportDTOMF, or with status {@code 400 (Bad Request)} if the uo2oPassportDTOMF has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/uo-2-o-passport-dtomfs")
    public ResponseEntity<Uo2oPassportDTOMF> createUo2oPassportDTOMF(@RequestBody Uo2oPassportDTOMF uo2oPassportDTOMF) throws URISyntaxException {
        log.debug("REST request to save Uo2oPassportDTOMF : {}", uo2oPassportDTOMF);
        if (uo2oPassportDTOMF.getId() != null) {
            throw new BadRequestAlertException("A new uo2oPassportDTOMF cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Uo2oPassportDTOMF result = uo2oPassportDTOMFService.save(uo2oPassportDTOMF);
        return ResponseEntity.created(new URI("/api/uo-2-o-passport-dtomfs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /uo-2-o-passport-dtomfs} : Updates an existing uo2oPassportDTOMF.
     *
     * @param uo2oPassportDTOMF the uo2oPassportDTOMF to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uo2oPassportDTOMF,
     * or with status {@code 400 (Bad Request)} if the uo2oPassportDTOMF is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uo2oPassportDTOMF couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/uo-2-o-passport-dtomfs")
    public ResponseEntity<Uo2oPassportDTOMF> updateUo2oPassportDTOMF(@RequestBody Uo2oPassportDTOMF uo2oPassportDTOMF) throws URISyntaxException {
        log.debug("REST request to update Uo2oPassportDTOMF : {}", uo2oPassportDTOMF);
        if (uo2oPassportDTOMF.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Uo2oPassportDTOMF result = uo2oPassportDTOMFService.save(uo2oPassportDTOMF);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uo2oPassportDTOMF.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /uo-2-o-passport-dtomfs} : get all the uo2oPassportDTOMFS.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uo2oPassportDTOMFS in body.
     */
    @GetMapping("/uo-2-o-passport-dtomfs")
    public ResponseEntity<List<Uo2oPassportDTOMF>> getAllUo2oPassportDTOMFS(Uo2oPassportDTOMFCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Uo2oPassportDTOMFS by criteria: {}", criteria);
        Page<Uo2oPassportDTOMF> page = uo2oPassportDTOMFQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /uo-2-o-passport-dtomfs/count} : count all the uo2oPassportDTOMFS.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/uo-2-o-passport-dtomfs/count")
    public ResponseEntity<Long> countUo2oPassportDTOMFS(Uo2oPassportDTOMFCriteria criteria) {
        log.debug("REST request to count Uo2oPassportDTOMFS by criteria: {}", criteria);
        return ResponseEntity.ok().body(uo2oPassportDTOMFQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /uo-2-o-passport-dtomfs/:id} : get the "id" uo2oPassportDTOMF.
     *
     * @param id the id of the uo2oPassportDTOMF to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uo2oPassportDTOMF, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/uo-2-o-passport-dtomfs/{id}")
    public ResponseEntity<Uo2oPassportDTOMF> getUo2oPassportDTOMF(@PathVariable Long id) {
        log.debug("REST request to get Uo2oPassportDTOMF : {}", id);
        Optional<Uo2oPassportDTOMF> uo2oPassportDTOMF = uo2oPassportDTOMFService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uo2oPassportDTOMF);
    }

    /**
     * {@code DELETE  /uo-2-o-passport-dtomfs/:id} : delete the "id" uo2oPassportDTOMF.
     *
     * @param id the id of the uo2oPassportDTOMF to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/uo-2-o-passport-dtomfs/{id}")
    public ResponseEntity<Void> deleteUo2oPassportDTOMF(@PathVariable Long id) {
        log.debug("REST request to delete Uo2oPassportDTOMF : {}", id);
        uo2oPassportDTOMFService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/uo-2-o-passport-dtomfs?query=:query} : search for the uo2oPassportDTOMF corresponding
     * to the query.
     *
     * @param query the query of the uo2oPassportDTOMF search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/uo-2-o-passport-dtomfs")
    public ResponseEntity<List<Uo2oPassportDTOMF>> searchUo2oPassportDTOMFS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Uo2oPassportDTOMFS for query {}", query);
        Page<Uo2oPassportDTOMF> page = uo2oPassportDTOMFService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
