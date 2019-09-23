package com.sa.sa.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A To2mCar.
 */
@Entity
@Table(name = "to2m_car")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "to2mcar")
public class To2mCar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("to2mCars")
    private To2mPerson to2mOwner;

    @ManyToOne
    @JsonIgnoreProperties("to2mCars")
    private To2mPerson to2mDriver;

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

    public To2mCar name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public To2mPerson getTo2mOwner() {
        return to2mOwner;
    }

    public To2mCar to2mOwner(To2mPerson to2mPerson) {
        this.to2mOwner = to2mPerson;
        return this;
    }

    public void setTo2mOwner(To2mPerson to2mPerson) {
        this.to2mOwner = to2mPerson;
    }

    public To2mPerson getTo2mDriver() {
        return to2mDriver;
    }

    public To2mCar to2mDriver(To2mPerson to2mPerson) {
        this.to2mDriver = to2mPerson;
        return this;
    }

    public void setTo2mDriver(To2mPerson to2mPerson) {
        this.to2mDriver = to2mPerson;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof To2mCar)) {
            return false;
        }
        return id != null && id.equals(((To2mCar) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "To2mCar{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
