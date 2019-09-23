package com.sa.sa.web.rest;

import com.sa.sa.domain.Uo2oCitizen;
import com.sa.sa.service.Uo2oCitizenService;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.sa.sa.domain.Uo2oCitizen}.
 */
@RestController
@RequestMapping("/api")
public class Uo2oCitizenResource {

    private final Logger log = LoggerFactory.getLogger(Uo2oCitizenResource.class);

    private static final String ENTITY_NAME = "uo2oCitizen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Uo2oCitizenService uo2oCitizenService;

    public Uo2oCitizenResource(Uo2oCitizenService uo2oCitizenService) {
        this.uo2oCitizenService = uo2oCitizenService;
    }

    /**
     * {@code POST  /uo-2-o-citizens} : Create a new uo2oCitizen.
     *
     * @param uo2oCitizen the uo2oCitizen to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uo2oCitizen, or with status {@code 400 (Bad Request)} if the uo2oCitizen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/uo-2-o-citizens")
    public ResponseEntity<Uo2oCitizen> createUo2oCitizen(@Valid @RequestBody Uo2oCitizen uo2oCitizen) throws URISyntaxException {
        log.debug("REST request to save Uo2oCitizen : {}", uo2oCitizen);
        if (uo2oCitizen.getId() != null) {
            throw new BadRequestAlertException("A new uo2oCitizen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(uo2oCitizen.getUo2oPassport())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        Uo2oCitizen result = uo2oCitizenService.save(uo2oCitizen);
        return ResponseEntity.created(new URI("/api/uo-2-o-citizens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /uo-2-o-citizens} : Updates an existing uo2oCitizen.
     *
     * @param uo2oCitizen the uo2oCitizen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uo2oCitizen,
     * or with status {@code 400 (Bad Request)} if the uo2oCitizen is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uo2oCitizen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/uo-2-o-citizens")
    public ResponseEntity<Uo2oCitizen> updateUo2oCitizen(@Valid @RequestBody Uo2oCitizen uo2oCitizen) throws URISyntaxException {
        log.debug("REST request to update Uo2oCitizen : {}", uo2oCitizen);
        if (uo2oCitizen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Uo2oCitizen result = uo2oCitizenService.save(uo2oCitizen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, uo2oCitizen.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /uo-2-o-citizens} : get all the uo2oCitizens.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uo2oCitizens in body.
     */
    @GetMapping("/uo-2-o-citizens")
    public ResponseEntity<List<Uo2oCitizen>> getAllUo2oCitizens(Pageable pageable) {
        log.debug("REST request to get a page of Uo2oCitizens");
        Page<Uo2oCitizen> page = uo2oCitizenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /uo-2-o-citizens/:id} : get the "id" uo2oCitizen.
     *
     * @param id the id of the uo2oCitizen to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uo2oCitizen, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/uo-2-o-citizens/{id}")
    public ResponseEntity<Uo2oCitizen> getUo2oCitizen(@PathVariable Long id) {
        log.debug("REST request to get Uo2oCitizen : {}", id);
        Optional<Uo2oCitizen> uo2oCitizen = uo2oCitizenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uo2oCitizen);
    }

    /**
     * {@code DELETE  /uo-2-o-citizens/:id} : delete the "id" uo2oCitizen.
     *
     * @param id the id of the uo2oCitizen to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/uo-2-o-citizens/{id}")
    public ResponseEntity<Void> deleteUo2oCitizen(@PathVariable Long id) {
        log.debug("REST request to delete Uo2oCitizen : {}", id);
        uo2oCitizenService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/uo-2-o-citizens?query=:query} : search for the uo2oCitizen corresponding
     * to the query.
     *
     * @param query the query of the uo2oCitizen search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/uo-2-o-citizens")
    public ResponseEntity<List<Uo2oCitizen>> searchUo2oCitizens(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Uo2oCitizens for query {}", query);
        Page<Uo2oCitizen> page = uo2oCitizenService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
