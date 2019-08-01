package com.sa.sa.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A M2mCar.
 */
@Entity
@Table(name = "m2m_car")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "m2mcar")
public class M2mCar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "m2m_car_m2m_driver",
               joinColumns = @JoinColumn(name = "m2m_car_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "m2m_driver_id", referencedColumnName = "id"))
    private Set<M2mDriver> m2mDrivers = new HashSet<>();

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

    public M2mCar name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<M2mDriver> getM2mDrivers() {
        return m2mDrivers;
    }

    public M2mCar m2mDrivers(Set<M2mDriver> m2mDrivers) {
        this.m2mDrivers = m2mDrivers;
        return this;
    }

    public M2mCar addM2mDriver(M2mDriver m2mDriver) {
        this.m2mDrivers.add(m2mDriver);
        m2mDriver.getM2mCars().add(this);
        return this;
    }

    public M2mCar removeM2mDriver(M2mDriver m2mDriver) {
        this.m2mDrivers.remove(m2mDriver);
        m2mDriver.getM2mCars().remove(this);
        return this;
    }

    public void setM2mDrivers(Set<M2mDriver> m2mDrivers) {
        this.m2mDrivers = m2mDrivers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof M2mCar)) {
            return false;
        }
        return id != null && id.equals(((M2mCar) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "M2mCar{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
