package com.sa.sa.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Um2oCar.
 */
@Entity
@Table(name = "um2o_car")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "um2ocar")
public class Um2oCar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("um2oCars")
    private Um2oOwner um2oOwner;

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

    public Um2oCar name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Um2oOwner getUm2oOwner() {
        return um2oOwner;
    }

    public Um2oCar um2oOwner(Um2oOwner um2oOwner) {
        this.um2oOwner = um2oOwner;
        return this;
    }

    public void setUm2oOwner(Um2oOwner um2oOwner) {
        this.um2oOwner = um2oOwner;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Um2oCar)) {
            return false;
        }
        return id != null && id.equals(((Um2oCar) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Um2oCar{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
