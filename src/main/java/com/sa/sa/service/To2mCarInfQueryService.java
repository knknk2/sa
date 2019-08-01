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

import com.sa.sa.domain.To2mCarInf;
import com.sa.sa.domain.*; // for static metamodels
import com.sa.sa.repository.To2mCarInfRepository;
import com.sa.sa.repository.search.To2mCarInfSearchRepository;
import com.sa.sa.service.dto.To2mCarInfCriteria;

/**
 * Service for executing complex queries for {@link To2mCarInf} entities in the database.
 * The main input is a {@link To2mCarInfCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link To2mCarInf} or a {@link Page} of {@link To2mCarInf} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class To2mCarInfQueryService extends QueryService<To2mCarInf> {

    private final Logger log = LoggerFactory.getLogger(To2mCarInfQueryService.class);

    private final To2mCarInfRepository to2mCarInfRepository;

    private final To2mCarInfSearchRepository to2mCarInfSearchRepository;

    public To2mCarInfQueryService(To2mCarInfRepository to2mCarInfRepository, To2mCarInfSearchRepository to2mCarInfSearchRepository) {
        this.to2mCarInfRepository = to2mCarInfRepository;
        this.to2mCarInfSearchRepository = to2mCarInfSearchRepository;
    }

    /**
     * Return a {@link List} of {@link To2mCarInf} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<To2mCarInf> findByCriteria(To2mCarInfCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<To2mCarInf> specification = createSpecification(criteria);
        return to2mCarInfRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link To2mCarInf} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<To2mCarInf> findByCriteria(To2mCarInfCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<To2mCarInf> specification = createSpecification(criteria);
        return to2mCarInfRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(To2mCarInfCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<To2mCarInf> specification = createSpecification(criteria);
        return to2mCarInfRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<To2mCarInf> createSpecification(To2mCarInfCriteria criteria) {
        Specification<To2mCarInf> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), To2mCarInf_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), To2mCarInf_.name));
            }
            if (criteria.getTo2mOwnerInfId() != null) {
                specification = specification.and(buildSpecification(criteria.getTo2mOwnerInfId(),
                    root -> root.join(To2mCarInf_.to2mOwnerInf, JoinType.LEFT).get(To2mPersonInf_.id)));
            }
            if (criteria.getTo2mDriverInfId() != null) {
                specification = specification.and(buildSpecification(criteria.getTo2mDriverInfId(),
                    root -> root.join(To2mCarInf_.to2mDriverInf, JoinType.LEFT).get(To2mPersonInf_.id)));
            }
        }
        return specification;
    }
}
