package com.sa.sa.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.sa.sa.domain.M2mDriverDTOMF} entity. This class is used
 * in {@link com.sa.sa.web.rest.M2mDriverDTOMFResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /m-2-m-driver-dtomfs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class M2mDriverDTOMFCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter m2mCarDTOMFId;

    public M2mDriverDTOMFCriteria(){
    }

    public M2mDriverDTOMFCriteria(M2mDriverDTOMFCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.m2mCarDTOMFId = other.m2mCarDTOMFId == null ? null : other.m2mCarDTOMFId.copy();
    }

    @Override
    public M2mDriverDTOMFCriteria copy() {
        return new M2mDriverDTOMFCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LongFilter getM2mCarDTOMFId() {
        return m2mCarDTOMFId;
    }

    public void setM2mCarDTOMFId(LongFilter m2mCarDTOMFId) {
        this.m2mCarDTOMFId = m2mCarDTOMFId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final M2mDriverDTOMFCriteria that = (M2mDriverDTOMFCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(m2mCarDTOMFId, that.m2mCarDTOMFId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        m2mCarDTOMFId
        );
    }

    @Override
    public String toString() {
        return "M2mDriverDTOMFCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (m2mCarDTOMFId != null ? "m2mCarDTOMFId=" + m2mCarDTOMFId + ", " : "") +
            "}";
    }

}
