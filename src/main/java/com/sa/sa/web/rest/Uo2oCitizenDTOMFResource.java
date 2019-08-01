package com.sa.sa.web.rest;

import com.sa.sa.domain.Uo2oCitizenDTOMF;
import com.sa.sa.service.Uo2oCitizenDTOMFService;
import com.sa.sa.web.rest.errors.BadRequestAlertException;
import com.sa.sa.service.dto.Uo2oCitizenDTOMFCriteria;
import com.sa.sa.service.Uo2oCitizenDTOMFQueryService;

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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.sa.sa.domain.Uo2oCitizenDTOMF}.
 */
@RestController
@RequestMapping("/api")
public class Uo2oCitizenDTOMFResource {

    private final Logger log = LoggerFactory.getLogger(Uo2oCitizenDTOMFResource.class);

    private static final String ENTITY_NAME = "uo2oCitizenDTOMF";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Uo2oCitizenDTOMFService uo2oCitizenDTOMFService;

    private final Uo2oCitizenDTOMFQueryService uo2oCitizenDTOMFQueryService;

    public Uo2oCitizenDTOMFResource(Uo2oCitizenDTOMFService uo2oCitizenDTOMFService, Uo2oCitizenDTOMFQueryService uo2oCitizenDTOMFQueryService) {
        this.uo2oCitizenDTOMFService = uo2oCitizenDTOMFService;
        this.uo2oCitizenDTOMFQueryService = uo2oCitizenDTOMFQueryService;
    }

    /**
     * {@code POST  /uo-2-o-citizen-dtomfs} : Create a new uo2oCitizenDTOMF.
     *
     * @param uo2oCitizenDTOMF the uo2oCitizenDTOMF to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uo2oCitizenDTOMF, or with status {@code 400 (Bad Request)} if the uo2oCitizenDTOMF has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/uo-2-o-citizen-dtomfs")
    public ResponseEntity<Uo2oCitizenDTOMF> createUo2oCitizenDTOMF(@Valid @RequestBody Uo2oCitizenDTOMF uo2oCitizenDTOMF) throws URISyntaxException {
        log.debug("REST request to save Uo2oCitizenDTOMF : {}", uo2oCitizenDTOMF);
        if (uo2oCitizenDTOMF.getId() != null) {
            throw new BadRequestAlertException("A new uo2oCitizenDTOMF cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(uo2oCitizenDTOMF.getUo2oPassportDTOMF())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        Uo2oCitizenDTOMF result = uo2oCitizenDTOMFService.save(uo2oCitizenDTOMF);
        return ResponseEntity.created(new URI("/api/uo-2-o-citizen-dtomfs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /uo-2-o-citizen-dtomfs} : Updates an existing uo2oCitizenDTOMF.
     *
     * @param uo2oCitizenDTOMF the uo2oCitizenDTOMF to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uo2oCitizenDTOMF,
     * or with status {@code 400 (Bad Request)} if the uo2oCitizenDTOMF is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uo2oCitizenDTOMF couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/uo-2-o-citizen-dtomfs")
    public ResponseEntity<Uo2oCitizenDTOMF> updateUo2oCitizenDTOMF(@Valid @RequestBody Uo2oCitizenDTOMF uo2oCitizenDTOMF) throws URISyntaxException {
        log.debug("REST request to update Uo2oCitizenDTOMF : {}", uo2oCitizenDTOMF);
        if (uo2oCitizenDTOMF.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Uo2oCitizenDTOMF result = uo2oCitizenDTOMFService.save(uo2oCitizenDTOMF);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uo2oCitizenDTOMF.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /uo-2-o-citizen-dtomfs} : get all the uo2oCitizenDTOMFS.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uo2oCitizenDTOMFS in body.
     */
    @GetMapping("/uo-2-o-citizen-dtomfs")
    public ResponseEntity<List<Uo2oCitizenDTOMF>> getAllUo2oCitizenDTOMFS(Uo2oCitizenDTOMFCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Uo2oCitizenDTOMFS by criteria: {}", criteria);
        Page<Uo2oCitizenDTOMF> page = uo2oCitizenDTOMFQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /uo-2-o-citizen-dtomfs/count} : count all the uo2oCitizenDTOMFS.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/uo-2-o-citizen-dtomfs/count")
    public ResponseEntity<Long> countUo2oCitizenDTOMFS(Uo2oCitizenDTOMFCriteria criteria) {
        log.debug("REST request to count Uo2oCitizenDTOMFS by criteria: {}", criteria);
        return ResponseEntity.ok().body(uo2oCitizenDTOMFQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /uo-2-o-citizen-dtomfs/:id} : get the "id" uo2oCitizenDTOMF.
     *
     * @param id the id of the uo2oCitizenDTOMF to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uo2oCitizenDTOMF, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/uo-2-o-citizen-dtomfs/{id}")
    public ResponseEntity<Uo2oCitizenDTOMF> getUo2oCitizenDTOMF(@PathVariable Long id) {
        log.debug("REST request to get Uo2oCitizenDTOMF : {}", id);
        Optional<Uo2oCitizenDTOMF> uo2oCitizenDTOMF = uo2oCitizenDTOMFService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uo2oCitizenDTOMF);
    }

    /**
     * {@code DELETE  /uo-2-o-citizen-dtomfs/:id} : delete the "id" uo2oCitizenDTOMF.
     *
     * @param id the id of the uo2oCitizenDTOMF to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/uo-2-o-citizen-dtomfs/{id}")
    public ResponseEntity<Void> deleteUo2oCitizenDTOMF(@PathVariable Long id) {
        log.debug("REST request to delete Uo2oCitizenDTOMF : {}", id);
        uo2oCitizenDTOMFService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/uo-2-o-citizen-dtomfs?query=:query} : search for the uo2oCitizenDTOMF corresponding
     * to the query.
     *
     * @param query the query of the uo2oCitizenDTOMF search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/uo-2-o-citizen-dtomfs")
    public ResponseEntity<List<Uo2oCitizenDTOMF>> searchUo2oCitizenDTOMFS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Uo2oCitizenDTOMFS for query {}", query);
        Page<Uo2oCitizenDTOMF> page = uo2oCitizenDTOMFService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
