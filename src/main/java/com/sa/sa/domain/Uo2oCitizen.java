package com.sa.sa.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Uo2oCitizen.
 */
@Entity
@Table(name = "uo2o_citizen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "uo2ocitizen")
public class Uo2oCitizen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(optional = false)    @NotNull

    @MapsId
    @JoinColumn(name = "id")
    private Uo2oPassport uo2oPassport;

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

    public Uo2oCitizen name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uo2oPassport getUo2oPassport() {
        return uo2oPassport;
    }

    public Uo2oCitizen uo2oPassport(Uo2oPassport uo2oPassport) {
        this.uo2oPassport = uo2oPassport;
        return this;
    }

    public void setUo2oPassport(Uo2oPassport uo2oPassport) {
        this.uo2oPassport = uo2oPassport;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Uo2oCitizen)) {
            return false;
        }
        return id != null && id.equals(((Uo2oCitizen) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Uo2oCitizen{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
