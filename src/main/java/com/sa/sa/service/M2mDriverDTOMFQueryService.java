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

import com.sa.sa.domain.M2mDriverDTOMF;
import com.sa.sa.domain.*; // for static metamodels
import com.sa.sa.repository.M2mDriverDTOMFRepository;
import com.sa.sa.repository.search.M2mDriverDTOMFSearchRepository;
import com.sa.sa.service.dto.M2mDriverDTOMFCriteria;

/**
 * Service for executing complex queries for {@link M2mDriverDTOMF} entities in the database.
 * The main input is a {@link M2mDriverDTOMFCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link M2mDriverDTOMF} or a {@link Page} of {@link M2mDriverDTOMF} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class M2mDriverDTOMFQueryService extends QueryService<M2mDriverDTOMF> {

    private final Logger log = LoggerFactory.getLogger(M2mDriverDTOMFQueryService.class);

    private final M2mDriverDTOMFRepository m2mDriverDTOMFRepository;

    private final M2mDriverDTOMFSearchRepository m2mDriverDTOMFSearchRepository;

    public M2mDriverDTOMFQueryService(M2mDriverDTOMFRepository m2mDriverDTOMFRepository, M2mDriverDTOMFSearchRepository m2mDriverDTOMFSearchRepository) {
        this.m2mDriverDTOMFRepository = m2mDriverDTOMFRepository;
        this.m2mDriverDTOMFSearchRepository = m2mDriverDTOMFSearchRepository;
    }

    /**
     * Return a {@link List} of {@link M2mDriverDTOMF} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<M2mDriverDTOMF> findByCriteria(M2mDriverDTOMFCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<M2mDriverDTOMF> specification = createSpecification(criteria);
        return m2mDriverDTOMFRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link M2mDriverDTOMF} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<M2mDriverDTOMF> findByCriteria(M2mDriverDTOMFCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<M2mDriverDTOMF> specification = createSpecification(criteria);
        return m2mDriverDTOMFRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(M2mDriverDTOMFCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<M2mDriverDTOMF> specification = createSpecification(criteria);
        return m2mDriverDTOMFRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<M2mDriverDTOMF> createSpecification(M2mDriverDTOMFCriteria criteria) {
        Specification<M2mDriverDTOMF> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), M2mDriverDTOMF_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), M2mDriverDTOMF_.name));
            }
            if (criteria.getM2mCarDTOMFId() != null) {
                specification = specification.and(buildSpecification(criteria.getM2mCarDTOMFId(),
                    root -> root.join(M2mDriverDTOMF_.m2mCarDTOMFS, JoinType.LEFT).get(M2mCarDTOMF_.id)));
            }
        }
        return specification;
    }
}
