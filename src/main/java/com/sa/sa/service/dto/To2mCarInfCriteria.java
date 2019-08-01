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
 * Criteria class for the {@link com.sa.sa.domain.To2mCarInf} entity. This class is used
 * in {@link com.sa.sa.web.rest.To2mCarInfResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /to-2-m-car-infs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class To2mCarInfCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter to2mOwnerInfId;

    private LongFilter to2mDriverInfId;

    public To2mCarInfCriteria(){
    }

    public To2mCarInfCriteria(To2mCarInfCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.to2mOwnerInfId = other.to2mOwnerInfId == null ? null : other.to2mOwnerInfId.copy();
        this.to2mDriverInfId = other.to2mDriverInfId == null ? null : other.to2mDriverInfId.copy();
    }

    @Override
    public To2mCarInfCriteria copy() {
        return new To2mCarInfCriteria(this);
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

    public LongFilter getTo2mOwnerInfId() {
        return to2mOwnerInfId;
    }

    public void setTo2mOwnerInfId(LongFilter to2mOwnerInfId) {
        this.to2mOwnerInfId = to2mOwnerInfId;
    }

    public LongFilter getTo2mDriverInfId() {
        return to2mDriverInfId;
    }

    public void setTo2mDriverInfId(LongFilter to2mDriverInfId) {
        this.to2mDriverInfId = to2mDriverInfId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final To2mCarInfCriteria that = (To2mCarInfCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(to2mOwnerInfId, that.to2mOwnerInfId) &&
            Objects.equals(to2mDriverInfId, that.to2mDriverInfId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        to2mOwnerInfId,
        to2mDriverInfId
        );
    }

    @Override
    public String toString() {
        return "To2mCarInfCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (to2mOwnerInfId != null ? "to2mOwnerInfId=" + to2mOwnerInfId + ", " : "") +
                (to2mDriverInfId != null ? "to2mDriverInfId=" + to2mDriverInfId + ", " : "") +
            "}";
    }

}
