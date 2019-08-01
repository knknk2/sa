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

import com.sa.sa.domain.Uo2oCitizenDTOMF;
import com.sa.sa.domain.*; // for static metamodels
import com.sa.sa.repository.Uo2oCitizenDTOMFRepository;
import com.sa.sa.repository.search.Uo2oCitizenDTOMFSearchRepository;
import com.sa.sa.service.dto.Uo2oCitizenDTOMFCriteria;

/**
 * Service for executing complex queries for {@link Uo2oCitizenDTOMF} entities in the database.
 * The main input is a {@link Uo2oCitizenDTOMFCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Uo2oCitizenDTOMF} or a {@link Page} of {@link Uo2oCitizenDTOMF} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class Uo2oCitizenDTOMFQueryService extends QueryService<Uo2oCitizenDTOMF> {

    private final Logger log = LoggerFactory.getLogger(Uo2oCitizenDTOMFQueryService.class);

    private final Uo2oCitizenDTOMFRepository uo2oCitizenDTOMFRepository;

    private final Uo2oCitizenDTOMFSearchRepository uo2oCitizenDTOMFSearchRepository;

    public Uo2oCitizenDTOMFQueryService(Uo2oCitizenDTOMFRepository uo2oCitizenDTOMFRepository, Uo2oCitizenDTOMFSearchRepository uo2oCitizenDTOMFSearchRepository) {
        this.uo2oCitizenDTOMFRepository = uo2oCitizenDTOMFRepository;
        this.uo2oCitizenDTOMFSearchRepository = uo2oCitizenDTOMFSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Uo2oCitizenDTOMF} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Uo2oCitizenDTOMF> findByCriteria(Uo2oCitizenDTOMFCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Uo2oCitizenDTOMF> specification = createSpecification(criteria);
        return uo2oCitizenDTOMFRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Uo2oCitizenDTOMF} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Uo2oCitizenDTOMF> findByCriteria(Uo2oCitizenDTOMFCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Uo2oCitizenDTOMF> specification = createSpecification(criteria);
        return uo2oCitizenDTOMFRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(Uo2oCitizenDTOMFCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Uo2oCitizenDTOMF> specification = createSpecification(criteria);
        return uo2oCitizenDTOMFRepository.count(specification);
    }

    /**
     * Function to convert ConsumerCriteria to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */    
    protected Specification<Uo2oCitizenDTOMF> createSpecification(Uo2oCitizenDTOMFCriteria criteria) {
        Specification<Uo2oCitizenDTOMF> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Uo2oCitizenDTOMF_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Uo2oCitizenDTOMF_.name));
            }
            if (criteria.getUo2oPassportDTOMFId() != null) {
                specification = specification.and(buildSpecification(criteria.getUo2oPassportDTOMFId(),
                    root -> root.join(Uo2oCitizenDTOMF_.uo2oPassportDTOMF, JoinType.LEFT).get(Uo2oPassportDTOMF_.id)));
            }
        }
        return specification;
    }
}
