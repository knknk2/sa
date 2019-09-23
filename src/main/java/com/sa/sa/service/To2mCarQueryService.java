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

import com.sa.sa.domain.To2mCar;
import com.sa.sa.domain.*; // for static metamodels
import com.sa.sa.repository.To2mCarRepository;
import com.sa.sa.repository.search.To2mCarSearchRepository;
import com.sa.sa.service.dto.To2mCarCriteria;

/**
 * Service for executing complex queries for {@link To2mCar} entities in the database.
 * The main input is a {@link To2mCarCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link To2mCar} or a {@link Page} of {@link To2mCar} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class To2mCarQueryService extends QueryService<To2mCar> {

    private final Logger log = LoggerFactory.getLogger(To2mCarQueryService.class);

    private final To2mCarRepository to2mCarRepository;

    private final To2mCarSearchRepository to2mCarSearchRepository;

    public To2mCarQueryService(To2mCarRepository to2mCarRepository, To2mCarSearchRepository to2mCarSearchRepository) {
        this.to2mCarRepository = to2mCarRepository;
        this.to2mCarSearchRepository = to2mCarSearchRepository;
    }

    /**
     * Return a {@link List} of {@link To2mCar} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<To2mCar> findByCriteria(To2mCarCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<To2mCar> specification = createSpecification(criteria);
        return to2mCarRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link To2mCar} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<To2mCar> findByCriteria(To2mCarCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<To2mCar> specification = createSpecification(criteria);
        return to2mCarRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(To2mCarCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<To2mCar> specification = createSpecification(criteria);
        return to2mCarRepository.count(specification);
    }

    /**
     * Function to convert {@link To2mCarCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<To2mCar> createSpecification(To2mCarCriteria criteria) {
        Specification<To2mCar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), To2mCar_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), To2mCar_.name));
            }
            if (criteria.getTo2mOwnerId() != null) {
                specification = specification.and(buildSpecification(criteria.getTo2mOwnerId(),
                    root -> root.join(To2mCar_.to2mOwner, JoinType.LEFT).get(To2mPerson_.id)));
            }
            if (criteria.getTo2mDriverId() != null) {
                specification = specification.and(buildSpecification(criteria.getTo2mDriverId(),
                    root -> root.join(To2mCar_.to2mDriver, JoinType.LEFT).get(To2mPerson_.id)));
            }
        }
        return specification;
    }
}
