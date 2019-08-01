package com.sa.sa.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.sa.sa.domain.enumeration.Enum1;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.DurationFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.UUIDFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.sa.sa.domain.Fields} entity. This class is used
 * in {@link com.sa.sa.web.rest.FieldsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fields?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FieldsCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Enum1
     */
    public static class Enum1Filter extends Filter<Enum1> {

        public Enum1Filter() {
        }

        public Enum1Filter(Enum1Filter filter) {
            super(filter);
        }

        @Override
        public Enum1Filter copy() {
            return new Enum1Filter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter str;

    private IntegerFilter num1;

    private LongFilter num2;

    private FloatFilter num3;

    private DoubleFilter num4;

    private BigDecimalFilter num5;

    private LocalDateFilter date1;

    private InstantFilter date2;

    private ZonedDateTimeFilter date3;

    private DurationFilter date4;

    private UUIDFilter uuid;

    private BooleanFilter bool;

    private Enum1Filter enumeration;

    public FieldsCriteria(){
    }

    public FieldsCriteria(FieldsCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.str = other.str == null ? null : other.str.copy();
        this.num1 = other.num1 == null ? null : other.num1.copy();
        this.num2 = other.num2 == null ? null : other.num2.copy();
        this.num3 = other.num3 == null ? null : other.num3.copy();
        this.num4 = other.num4 == null ? null : other.num4.copy();
        this.num5 = other.num5 == null ? null : other.num5.copy();
        this.date1 = other.date1 == null ? null : other.date1.copy();
        this.date2 = other.date2 == null ? null : other.date2.copy();
        this.date3 = other.date3 == null ? null : other.date3.copy();
        this.date4 = other.date4 == null ? null : other.date4.copy();
        this.uuid = other.uuid == null ? null : other.uuid.copy();
        this.bool = other.bool == null ? null : other.bool.copy();
        this.enumeration = other.enumeration == null ? null : other.enumeration.copy();
    }

    @Override
    public FieldsCriteria copy() {
        return new FieldsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStr() {
        return str;
    }

    public void setStr(StringFilter str) {
        this.str = str;
    }

    public IntegerFilter getNum1() {
        return num1;
    }

    public void setNum1(IntegerFilter num1) {
        this.num1 = num1;
    }

    public LongFilter getNum2() {
        return num2;
    }

    public void setNum2(LongFilter num2) {
        this.num2 = num2;
    }

    public FloatFilter getNum3() {
        return num3;
    }

    public void setNum3(FloatFilter num3) {
        this.num3 = num3;
    }

    public DoubleFilter getNum4() {
        return num4;
    }

    public void setNum4(DoubleFilter num4) {
        this.num4 = num4;
    }

    public BigDecimalFilter getNum5() {
        return num5;
    }

    public void setNum5(BigDecimalFilter num5) {
        this.num5 = num5;
    }

    public LocalDateFilter getDate1() {
        return date1;
    }

    public void setDate1(LocalDateFilter date1) {
        this.date1 = date1;
    }

    public InstantFilter getDate2() {
        return date2;
    }

    public void setDate2(InstantFilter date2) {
        this.date2 = date2;
    }

    public ZonedDateTimeFilter getDate3() {
        return date3;
    }

    public void setDate3(ZonedDateTimeFilter date3) {
        this.date3 = date3;
    }

    public DurationFilter getDate4() {
        return date4;
    }

    public void setDate4(DurationFilter date4) {
        this.date4 = date4;
    }

    public UUIDFilter getUuid() {
        return uuid;
    }

    public void setUuid(UUIDFilter uuid) {
        this.uuid = uuid;
    }

    public BooleanFilter getBool() {
        return bool;
    }

    public void setBool(BooleanFilter bool) {
        this.bool = bool;
    }

    public Enum1Filter getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(Enum1Filter enumeration) {
        this.enumeration = enumeration;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FieldsCriteria that = (FieldsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(str, that.str) &&
            Objects.equals(num1, that.num1) &&
            Objects.equals(num2, that.num2) &&
            Objects.equals(num3, that.num3) &&
            Objects.equals(num4, that.num4) &&
            Objects.equals(num5, that.num5) &&
            Objects.equals(date1, that.date1) &&
            Objects.equals(date2, that.date2) &&
            Objects.equals(date3, that.date3) &&
            Objects.equals(date4, that.date4) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(bool, that.bool) &&
            Objects.equals(enumeration, that.enumeration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        str,
        num1,
        num2,
        num3,
        num4,
        num5,
        date1,
        date2,
        date3,
        date4,
        uuid,
        bool,
        enumeration
        );
    }

    @Override
    public String toString() {
        return "FieldsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (str != null ? "str=" + str + ", " : "") +
                (num1 != null ? "num1=" + num1 + ", " : "") +
                (num2 != null ? "num2=" + num2 + ", " : "") +
                (num3 != null ? "num3=" + num3 + ", " : "") +
                (num4 != null ? "num4=" + num4 + ", " : "") +
                (num5 != null ? "num5=" + num5 + ", " : "") +
                (date1 != null ? "date1=" + date1 + ", " : "") +
                (date2 != null ? "date2=" + date2 + ", " : "") +
                (date3 != null ? "date3=" + date3 + ", " : "") +
                (date4 != null ? "date4=" + date4 + ", " : "") +
                (uuid != null ? "uuid=" + uuid + ", " : "") +
                (bool != null ? "bool=" + bool + ", " : "") +
                (enumeration != null ? "enumeration=" + enumeration + ", " : "") +
            "}";
    }

}
