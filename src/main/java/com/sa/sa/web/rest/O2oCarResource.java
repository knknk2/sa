package com.sa.sa.web.rest;

import com.sa.sa.domain.O2oCar;
import com.sa.sa.service.O2oCarService;
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
 * REST controller for managing {@link com.sa.sa.domain.O2oCar}.
 */
@RestController
@RequestMapping("/api")
public class O2oCarResource {

    private final Logger log = LoggerFactory.getLogger(O2oCarResource.class);

    private static final String ENTITY_NAME = "o2oCar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final O2oCarService o2oCarService;

    public O2oCarResource(O2oCarService o2oCarService) {
        this.o2oCarService = o2oCarService;
    }

    /**
     * {@code POST  /o-2-o-cars} : Create a new o2oCar.
     *
     * @param o2oCar the o2oCar to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new o2oCar, or with status {@code 400 (Bad Request)} if the o2oCar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/o-2-o-cars")
    public ResponseEntity<O2oCar> createO2oCar(@Valid @RequestBody O2oCar o2oCar) throws URISyntaxException {
        log.debug("REST request to save O2oCar : {}", o2oCar);
        if (o2oCar.getId() != null) {
            throw new BadRequestAlertException("A new o2oCar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(o2oCar.getO2oDriver())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        O2oCar result = o2oCarService.save(o2oCar);
        return ResponseEntity.created(new URI("/api/o-2-o-cars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /o-2-o-cars} : Updates an existing o2oCar.
     *
     * @param o2oCar the o2oCar to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated o2oCar,
     * or with status {@code 400 (Bad Request)} if the o2oCar is not valid,
     * or with status {@code 500 (Internal Server Error)} if the o2oCar couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/o-2-o-cars")
    public ResponseEntity<O2oCar> updateO2oCar(@Valid @RequestBody O2oCar o2oCar) throws URISyntaxException {
        log.debug("REST request to update O2oCar : {}", o2oCar);
        if (o2oCar.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        O2oCar result = o2oCarService.save(o2oCar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, o2oCar.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /o-2-o-cars} : get all the o2oCars.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of o2oCars in body.
     */
    @GetMapping("/o-2-o-cars")
    public ResponseEntity<List<O2oCar>> getAllO2oCars(Pageable pageable) {
        log.debug("REST request to get a page of O2oCars");
        Page<O2oCar> page = o2oCarService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /o-2-o-cars/:id} : get the "id" o2oCar.
     *
     * @param id the id of the o2oCar to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the o2oCar, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/o-2-o-cars/{id}")
    public ResponseEntity<O2oCar> getO2oCar(@PathVariable Long id) {
        log.debug("REST request to get O2oCar : {}", id);
        Optional<O2oCar> o2oCar = o2oCarService.findOne(id);
        return ResponseUtil.wrapOrNotFound(o2oCar);
    }

    /**
     * {@code DELETE  /o-2-o-cars/:id} : delete the "id" o2oCar.
     *
     * @param id the id of the o2oCar to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/o-2-o-cars/{id}")
    public ResponseEntity<Void> deleteO2oCar(@PathVariable Long id) {
        log.debug("REST request to delete O2oCar : {}", id);
        o2oCarService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/o-2-o-cars?query=:query} : search for the o2oCar corresponding
     * to the query.
     *
     * @param query the query of the o2oCar search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/o-2-o-cars")
    public ResponseEntity<List<O2oCar>> searchO2oCars(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of O2oCars for query {}", query);
        Page<O2oCar> page = o2oCarService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
