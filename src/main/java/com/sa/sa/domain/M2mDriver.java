package com.sa.sa.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A M2mDriver.
 */
@Entity
@Table(name = "m2m_driver")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "m2mdriver")
public class M2mDriver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "m2mDrivers")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<M2mCar> m2mCars = new HashSet<>();

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

    public M2mDriver name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<M2mCar> getM2mCars() {
        return m2mCars;
    }

    public M2mDriver m2mCars(Set<M2mCar> m2mCars) {
        this.m2mCars = m2mCars;
        return this;
    }

    public M2mDriver addM2mCar(M2mCar m2mCar) {
        this.m2mCars.add(m2mCar);
        m2mCar.getM2mDrivers().add(this);
        return this;
    }

    public M2mDriver removeM2mCar(M2mCar m2mCar) {
        this.m2mCars.remove(m2mCar);
        m2mCar.getM2mDrivers().remove(this);
        return this;
    }

    public void setM2mCars(Set<M2mCar> m2mCars) {
        this.m2mCars = m2mCars;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof M2mDriver)) {
            return false;
        }
        return id != null && id.equals(((M2mDriver) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "M2mDriver{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
