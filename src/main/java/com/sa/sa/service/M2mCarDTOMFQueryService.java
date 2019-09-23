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

import com.sa.sa.domain.M2mCarDTOMF;
import com.sa.sa.domain.*; // for static metamodels
import com.sa.sa.repository.M2mCarDTOMFRepository;
import com.sa.sa.repository.search.M2mCarDTOMFSearchRepository;
import com.sa.sa.service.dto.M2mCarDTOMFCriteria;

/**
 * Service for executing complex queries for {@link M2mCarDTOMF} entities in the database.
 * The main input is a {@link M2mCarDTOMFCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link M2mCarDTOMF} or a {@link Page} of {@link M2mCarDTOMF} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class M2mCarDTOMFQueryService extends QueryService<M2mCarDTOMF> {

    private final Logger log = LoggerFactory.getLogger(M2mCarDTOMFQueryService.class);

    private final M2mCarDTOMFRepository m2mCarDTOMFRepository;

    private final M2mCarDTOMFSearchRepository m2mCarDTOMFSearchRepository;

    public M2mCarDTOMFQueryService(M2mCarDTOMFRepository m2mCarDTOMFRepository, M2mCarDTOMFSearchRepository m2mCarDTOMFSearchRepository) {
        this.m2mCarDTOMFRepository = m2mCarDTOMFRepository;
        this.m2mCarDTOMFSearchRepository = m2mCarDTOMFSearchRepository;
    }

    /**
     * Return a {@link List} of {@link M2mCarDTOMF} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<M2mCarDTOMF> findByCriteria(M2mCarDTOMFCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<M2mCarDTOMF> specification = createSpecification(criteria);
        return m2mCarDTOMFRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link M2mCarDTOMF} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<M2mCarDTOMF> findByCriteria(M2mCarDTOMFCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<M2mCarDTOMF> specification = createSpecification(criteria);
        return m2mCarDTOMFRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(M2mCarDTOMFCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<M2mCarDTOMF> specification = createSpecification(criteria);
        return m2mCarDTOMFRepository.count(specification);
    }

    /**
     * Function to convert {@link M2mCarDTOMFCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<M2mCarDTOMF> createSpecification(M2mCarDTOMFCriteria criteria) {
        Specification<M2mCarDTOMF> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), M2mCarDTOMF_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), M2mCarDTOMF_.name));
            }
            if (criteria.getM2mDriverDTOMFId() != null) {
                specification = specification.and(buildSpecification(criteria.getM2mDriverDTOMFId(),
                    root -> root.join(M2mCarDTOMF_.m2mDriverDTOMFS, JoinType.LEFT).get(M2mDriverDTOMF_.id)));
            }
        }
        return specification;
    }
}
