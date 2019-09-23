package com.sa.sa.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.sa.sa.domain.Uo2oPassportDTOMF;
import com.sa.sa.domain.*; // for static metamodels
import com.sa.sa.repository.Uo2oPassportDTOMFRepository;
import com.sa.sa.repository.search.Uo2oPassportDTOMFSearchRepository;
import com.sa.sa.service.dto.Uo2oPassportDTOMFCriteria;

/**
 * Service for executing complex queries for {@link Uo2oPassportDTOMF} entities in the database.
 * The main input is a {@link Uo2oPassportDTOMFCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Uo2oPassportDTOMF} or a {@link Page} of {@link Uo2oPassportDTOMF} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class Uo2oPassportDTOMFQueryService extends QueryService<Uo2oPassportDTOMF> {

    private final Logger log = LoggerFactory.getLogger(Uo2oPassportDTOMFQueryService.class);

    private final Uo2oPassportDTOMFRepository uo2oPassportDTOMFRepository;

    private final Uo2oPassportDTOMFSearchRepository uo2oPassportDTOMFSearchRepository;

    public Uo2oPassportDTOMFQueryService(Uo2oPassportDTOMFRepository uo2oPassportDTOMFRepository, Uo2oPassportDTOMFSearchRepository uo2oPassportDTOMFSearchRepository) {
        this.uo2oPassportDTOMFRepository = uo2oPassportDTOMFRepository;
        this.uo2oPassportDTOMFSearchRepository = uo2oPassportDTOMFSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Uo2oPassportDTOMF} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Uo2oPassportDTOMF> findByCriteria(Uo2oPassportDTOMFCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Uo2oPassportDTOMF> specification = createSpecification(criteria);
        return uo2oPassportDTOMFRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Uo2oPassportDTOMF} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Uo2oPassportDTOMF> findByCriteria(Uo2oPassportDTOMFCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Uo2oPassportDTOMF> specification = createSpecification(criteria);
        return uo2oPassportDTOMFRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(Uo2oPassportDTOMFCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Uo2oPassportDTOMF> specification = createSpecification(criteria);
        return uo2oPassportDTOMFRepository.count(specification);
    }

    /**
     * Function to convert {@link Uo2oPassportDTOMFCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Uo2oPassportDTOMF> createSpecification(Uo2oPassportDTOMFCriteria criteria) {
        Specification<Uo2oPassportDTOMF> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Uo2oPassportDTOMF_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Uo2oPassportDTOMF_.name));
            }
        }
        return specification;
    }
}
