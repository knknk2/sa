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

import com.sa.sa.domain.Fields;
import com.sa.sa.domain.*; // for static metamodels
import com.sa.sa.repository.FieldsRepository;
import com.sa.sa.repository.search.FieldsSearchRepository;
import com.sa.sa.service.dto.FieldsCriteria;
import com.sa.sa.service.dto.FieldsDTO;
import com.sa.sa.service.mapper.FieldsMapper;

/**
 * Service for executing complex queries for {@link Fields} entities in the database.
 * The main input is a {@link FieldsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FieldsDTO} or a {@link Page} of {@link FieldsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FieldsQueryService extends QueryService<Fields> {

    private final Logger log = LoggerFactory.getLogger(FieldsQueryService.class);

    private final FieldsRepository fieldsRepository;

    private final FieldsMapper fieldsMapper;

    private final FieldsSearchRepository fieldsSearchRepository;

    public FieldsQueryService(FieldsRepository fieldsRepository, FieldsMapper fieldsMapper, FieldsSearchRepository fieldsSearchRepository) {
        this.fieldsRepository = fieldsRepository;
        this.fieldsMapper = fieldsMapper;
        this.fieldsSearchRepository = fieldsSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FieldsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FieldsDTO> findByCriteria(FieldsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Fields> specification = createSpecification(criteria);
        return fieldsMapper.toDto(fieldsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FieldsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FieldsDTO> findByCriteria(FieldsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Fields> specification = createSpecification(criteria);
        return fieldsRepository.findAll(specification, page)
            .map(fieldsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FieldsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Fields> specification = createSpecification(criteria);
        return fieldsRepository.count(specification);
    }

    /**
     * Function to convert {@link FieldsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Fields> createSpecification(FieldsCriteria criteria) {
        Specification<Fields> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Fields_.id));
            }
            if (criteria.getStr() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStr(), Fields_.str));
            }
            if (criteria.getNum1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNum1(), Fields_.num1));
            }
            if (criteria.getNum2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNum2(), Fields_.num2));
            }
            if (criteria.getNum3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNum3(), Fields_.num3));
            }
            if (criteria.getNum4() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNum4(), Fields_.num4));
            }
            if (criteria.getNum5() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNum5(), Fields_.num5));
            }
            if (criteria.getDate1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate1(), Fields_.date1));
            }
            if (criteria.getDate2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate2(), Fields_.date2));
            }
            if (criteria.getDate3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate3(), Fields_.date3));
            }
            if (criteria.getDate4() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate4(), Fields_.date4));
            }
            if (criteria.getUuid() != null) {
                specification = specification.and(buildSpecification(criteria.getUuid(), Fields_.uuid));
            }
            if (criteria.getBool() != null) {
                specification = specification.and(buildSpecification(criteria.getBool(), Fields_.bool));
            }
            if (criteria.getEnumeration() != null) {
                specification = specification.and(buildSpecification(criteria.getEnumeration(), Fields_.enumeration));
            }
        }
        return specification;
    }
}
