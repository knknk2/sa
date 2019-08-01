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
 * Criteria class for the {@link com.sa.sa.domain.To2mCar} entity. This class is used
 * in {@link com.sa.sa.web.rest.To2mCarResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /to-2-m-cars?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class To2mCarCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter to2mOwnerId;

    private LongFilter to2mDriverId;

    public To2mCarCriteria(){
    }

    public To2mCarCriteria(To2mCarCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.to2mOwnerId = other.to2mOwnerId == null ? null : other.to2mOwnerId.copy();
        this.to2mDriverId = other.to2mDriverId == null ? null : other.to2mDriverId.copy();
    }

    @Override
    public To2mCarCriteria copy() {
        return new To2mCarCriteria(this);
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

    public LongFilter getTo2mOwnerId() {
        return to2mOwnerId;
    }

    public void setTo2mOwnerId(LongFilter to2mOwnerId) {
        this.to2mOwnerId = to2mOwnerId;
    }

    public LongFilter getTo2mDriverId() {
        return to2mDriverId;
    }

    public void setTo2mDriverId(LongFilter to2mDriverId) {
        this.to2mDriverId = to2mDriverId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final To2mCarCriteria that = (To2mCarCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(to2mOwnerId, that.to2mOwnerId) &&
            Objects.equals(to2mDriverId, that.to2mDriverId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        to2mOwnerId,
        to2mDriverId
        );
    }

    @Override
    public String toString() {
        return "To2mCarCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (to2mOwnerId != null ? "to2mOwnerId=" + to2mOwnerId + ", " : "") +
                (to2mDriverId != null ? "to2mDriverId=" + to2mDriverId + ", " : "") +
            "}";
    }

}
