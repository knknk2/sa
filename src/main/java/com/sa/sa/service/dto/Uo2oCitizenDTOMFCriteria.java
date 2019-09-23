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
 * Criteria class for the {@link com.sa.sa.domain.Uo2oCitizenDTOMF} entity. This class is used
 * in {@link com.sa.sa.web.rest.Uo2oCitizenDTOMFResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /uo-2-o-citizen-dtomfs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class Uo2oCitizenDTOMFCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter uo2oPassportDTOMFId;

    public Uo2oCitizenDTOMFCriteria(){
    }

    public Uo2oCitizenDTOMFCriteria(Uo2oCitizenDTOMFCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.uo2oPassportDTOMFId = other.uo2oPassportDTOMFId == null ? null : other.uo2oPassportDTOMFId.copy();
    }

    @Override
    public Uo2oCitizenDTOMFCriteria copy() {
        return new Uo2oCitizenDTOMFCriteria(this);
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

    public LongFilter getUo2oPassportDTOMFId() {
        return uo2oPassportDTOMFId;
    }

    public void setUo2oPassportDTOMFId(LongFilter uo2oPassportDTOMFId) {
        this.uo2oPassportDTOMFId = uo2oPassportDTOMFId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Uo2oCitizenDTOMFCriteria that = (Uo2oCitizenDTOMFCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(uo2oPassportDTOMFId, that.uo2oPassportDTOMFId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        uo2oPassportDTOMFId
        );
    }

    @Override
    public String toString() {
        return "Uo2oCitizenDTOMFCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (uo2oPassportDTOMFId != null ? "uo2oPassportDTOMFId=" + uo2oPassportDTOMFId + ", " : "") +
            "}";
    }

}
