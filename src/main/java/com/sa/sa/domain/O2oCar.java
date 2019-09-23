package com.sa.sa.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A O2oCar.
 */
@Entity
@Table(name = "o2o_car")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "o2ocar")
public class O2oCar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(optional = false)    @NotNull

    @MapsId
    @JoinColumn(name = "id")
    private O2oDriver o2oDriver;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public O2oCar name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public O2oDriver getO2oDriver() {
        return o2oDriver;
    }

    public O2oCar o2oDriver(O2oDriver o2oDriver) {
        this.o2oDriver = o2oDriver;
        return this;
    }

    public void setO2oDriver(O2oDriver o2oDriver) {
        this.o2oDriver = o2oDriver;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof O2oCar)) {
            return false;
        }
        return id != null && id.equals(((O2oCar) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "O2oCar{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
