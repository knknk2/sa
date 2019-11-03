package com.sa.sa.web.rest;

import com.sa.sa.domain.Uo2oPassport;
import com.sa.sa.service.Uo2oPassportService;
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
 * REST controller for managing {@link com.sa.sa.domain.Uo2oPassport}.
 */
@RestController
@RequestMapping("/api")
public class Uo2oPassportResource {

    private final Logger log = LoggerFactory.getLogger(Uo2oPassportResource.class);

    private static final String ENTITY_NAME = "uo2oPassport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Uo2oPassportService uo2oPassportService;

    public Uo2oPassportResource(Uo2oPassportService uo2oPassportService) {
        this.uo2oPassportService = uo2oPassportService;
    }

    /**
     * {@code POST  /uo-2-o-passports} : Create a new uo2oPassport.
     *
     * @param uo2oPassport the uo2oPassport to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uo2oPassport, or with status {@code 400 (Bad Request)} if the uo2oPassport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/uo-2-o-passports")
    public ResponseEntity<Uo2oPassport> createUo2oPassport(@RequestBody Uo2oPassport uo2oPassport) throws URISyntaxException {
        log.debug("REST request to save Uo2oPassport : {}", uo2oPassport);
        if (uo2oPassport.getId() != null) {
            throw new BadRequestAlertException("A new uo2oPassport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Uo2oPassport result = uo2oPassportService.save(uo2oPassport);
        return ResponseEntity.created(new URI("/api/uo-2-o-passports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /uo-2-o-passports} : Updates an existing uo2oPassport.
     *
     * @param uo2oPassport the uo2oPassport to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uo2oPassport,
     * or with status {@code 400 (Bad Request)} if the uo2oPassport is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uo2oPassport couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/uo-2-o-passports")
    public ResponseEntity<Uo2oPassport> updateUo2oPassport(@RequestBody Uo2oPassport uo2oPassport) throws URISyntaxException {
        log.debug("REST request to update Uo2oPassport : {}", uo2oPassport);
        if (uo2oPassport.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Uo2oPassport result = uo2oPassportService.save(uo2oPassport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uo2oPassport.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /uo-2-o-passports} : get all the uo2oPassports.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uo2oPassports in body.
     */
    @GetMapping("/uo-2-o-passports")
    public ResponseEntity<List<Uo2oPassport>> getAllUo2oPassports(Pageable pageable) {
        log.debug("REST request to get a page of Uo2oPassports");
        Page<Uo2oPassport> page = uo2oPassportService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /uo-2-o-passports/:id} : get the "id" uo2oPassport.
     *
     * @param id the id of the uo2oPassport to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uo2oPassport, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/uo-2-o-passports/{id}")
    public ResponseEntity<Uo2oPassport> getUo2oPassport(@PathVariable Long id) {
        log.debug("REST request to get Uo2oPassport : {}", id);
        Optional<Uo2oPassport> uo2oPassport = uo2oPassportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uo2oPassport);
    }

    /**
     * {@code DELETE  /uo-2-o-passports/:id} : delete the "id" uo2oPassport.
     *
     * @param id the id of the uo2oPassport to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/uo-2-o-passports/{id}")
    public ResponseEntity<Void> deleteUo2oPassport(@PathVariable Long id) {
        log.debug("REST request to delete Uo2oPassport : {}", id);
        uo2oPassportService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/uo-2-o-passports?query=:query} : search for the uo2oPassport corresponding
     * to the query.
     *
     * @param query the query of the uo2oPassport search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/uo-2-o-passports")
    public ResponseEntity<List<Uo2oPassport>> searchUo2oPassports(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Uo2oPassports for query {}", query);
        Page<Uo2oPassport> page = uo2oPassportService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
