package com.sa.sa.web.rest;

import com.sa.sa.domain.Bo2mOwner;
import com.sa.sa.service.Bo2mOwnerService;
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
 * REST controller for managing {@link com.sa.sa.domain.Bo2mOwner}.
 */
@RestController
@RequestMapping("/api")
public class Bo2mOwnerResource {

    private final Logger log = LoggerFactory.getLogger(Bo2mOwnerResource.class);

    private static final String ENTITY_NAME = "bo2mOwner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Bo2mOwnerService bo2mOwnerService;

    public Bo2mOwnerResource(Bo2mOwnerService bo2mOwnerService) {
        this.bo2mOwnerService = bo2mOwnerService;
    }

    /**
     * {@code POST  /bo-2-m-owners} : Create a new bo2mOwner.
     *
     * @param bo2mOwner the bo2mOwner to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bo2mOwner, or with status {@code 400 (Bad Request)} if the bo2mOwner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bo-2-m-owners")
    public ResponseEntity<Bo2mOwner> createBo2mOwner(@RequestBody Bo2mOwner bo2mOwner) throws URISyntaxException {
        log.debug("REST request to save Bo2mOwner : {}", bo2mOwner);
        if (bo2mOwner.getId() != null) {
            throw new BadRequestAlertException("A new bo2mOwner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bo2mOwner result = bo2mOwnerService.save(bo2mOwner);
        return ResponseEntity.created(new URI("/api/bo-2-m-owners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bo-2-m-owners} : Updates an existing bo2mOwner.
     *
     * @param bo2mOwner the bo2mOwner to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bo2mOwner,
     * or with status {@code 400 (Bad Request)} if the bo2mOwner is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bo2mOwner couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bo-2-m-owners")
    public ResponseEntity<Bo2mOwner> updateBo2mOwner(@RequestBody Bo2mOwner bo2mOwner) throws URISyntaxException {
        log.debug("REST request to update Bo2mOwner : {}", bo2mOwner);
        if (bo2mOwner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bo2mOwner result = bo2mOwnerService.save(bo2mOwner);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bo2mOwner.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bo-2-m-owners} : get all the bo2mOwners.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bo2mOwners in body.
     */
    @GetMapping("/bo-2-m-owners")
    public ResponseEntity<List<Bo2mOwner>> getAllBo2mOwners(Pageable pageable) {
        log.debug("REST request to get a page of Bo2mOwners");
        Page<Bo2mOwner> page = bo2mOwnerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bo-2-m-owners/:id} : get the "id" bo2mOwner.
     *
     * @param id the id of the bo2mOwner to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bo2mOwner, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bo-2-m-owners/{id}")
    public ResponseEntity<Bo2mOwner> getBo2mOwner(@PathVariable Long id) {
        log.debug("REST request to get Bo2mOwner : {}", id);
        Optional<Bo2mOwner> bo2mOwner = bo2mOwnerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bo2mOwner);
    }

    /**
     * {@code DELETE  /bo-2-m-owners/:id} : delete the "id" bo2mOwner.
     *
     * @param id the id of the bo2mOwner to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bo-2-m-owners/{id}")
    public ResponseEntity<Void> deleteBo2mOwner(@PathVariable Long id) {
        log.debug("REST request to delete Bo2mOwner : {}", id);
        bo2mOwnerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/bo-2-m-owners?query=:query} : search for the bo2mOwner corresponding
     * to the query.
     *
     * @param query the query of the bo2mOwner search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/bo-2-m-owners")
    public ResponseEntity<List<Bo2mOwner>> searchBo2mOwners(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Bo2mOwners for query {}", query);
        Page<Bo2mOwner> page = bo2mOwnerService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
