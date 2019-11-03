package com.sa.sa.web.rest;

import com.sa.sa.domain.To2mPerson;
import com.sa.sa.service.To2mPersonService;
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
 * REST controller for managing {@link com.sa.sa.domain.To2mPerson}.
 */
@RestController
@RequestMapping("/api")
public class To2mPersonResource {

    private final Logger log = LoggerFactory.getLogger(To2mPersonResource.class);

    private static final String ENTITY_NAME = "to2mPerson";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final To2mPersonService to2mPersonService;

    public To2mPersonResource(To2mPersonService to2mPersonService) {
        this.to2mPersonService = to2mPersonService;
    }

    /**
     * {@code POST  /to-2-m-people} : Create a new to2mPerson.
     *
     * @param to2mPerson the to2mPerson to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new to2mPerson, or with status {@code 400 (Bad Request)} if the to2mPerson has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/to-2-m-people")
    public ResponseEntity<To2mPerson> createTo2mPerson(@RequestBody To2mPerson to2mPerson) throws URISyntaxException {
        log.debug("REST request to save To2mPerson : {}", to2mPerson);
        if (to2mPerson.getId() != null) {
            throw new BadRequestAlertException("A new to2mPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        To2mPerson result = to2mPersonService.save(to2mPerson);
        return ResponseEntity.created(new URI("/api/to-2-m-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /to-2-m-people} : Updates an existing to2mPerson.
     *
     * @param to2mPerson the to2mPerson to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated to2mPerson,
     * or with status {@code 400 (Bad Request)} if the to2mPerson is not valid,
     * or with status {@code 500 (Internal Server Error)} if the to2mPerson couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/to-2-m-people")
    public ResponseEntity<To2mPerson> updateTo2mPerson(@RequestBody To2mPerson to2mPerson) throws URISyntaxException {
        log.debug("REST request to update To2mPerson : {}", to2mPerson);
        if (to2mPerson.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        To2mPerson result = to2mPersonService.save(to2mPerson);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, to2mPerson.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /to-2-m-people} : get all the to2mPeople.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of to2mPeople in body.
     */
    @GetMapping("/to-2-m-people")
    public ResponseEntity<List<To2mPerson>> getAllTo2mPeople(Pageable pageable) {
        log.debug("REST request to get a page of To2mPeople");
        Page<To2mPerson> page = to2mPersonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /to-2-m-people/:id} : get the "id" to2mPerson.
     *
     * @param id the id of the to2mPerson to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the to2mPerson, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/to-2-m-people/{id}")
    public ResponseEntity<To2mPerson> getTo2mPerson(@PathVariable Long id) {
        log.debug("REST request to get To2mPerson : {}", id);
        Optional<To2mPerson> to2mPerson = to2mPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(to2mPerson);
    }

    /**
     * {@code DELETE  /to-2-m-people/:id} : delete the "id" to2mPerson.
     *
     * @param id the id of the to2mPerson to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/to-2-m-people/{id}")
    public ResponseEntity<Void> deleteTo2mPerson(@PathVariable Long id) {
        log.debug("REST request to delete To2mPerson : {}", id);
        to2mPersonService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/to-2-m-people?query=:query} : search for the to2mPerson corresponding
     * to the query.
     *
     * @param query the query of the to2mPerson search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/to-2-m-people")
    public ResponseEntity<List<To2mPerson>> searchTo2mPeople(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of To2mPeople for query {}", query);
        Page<To2mPerson> page = to2mPersonService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
