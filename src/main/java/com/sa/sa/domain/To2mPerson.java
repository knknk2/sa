package com.sa.sa.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A To2mPerson.
 */
@Entity
@Table(name = "to2m_person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "to2mperson")
public class To2mPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "to2mOwner")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<To2mCar> to2mOwnedCars = new HashSet<>();

    @OneToMany(mappedBy = "to2mDriver")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<To2mCar> to2mDrivedCars = new HashSet<>();

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

    public To2mPerson name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<To2mCar> getTo2mOwnedCars() {
        return to2mOwnedCars;
    }

    public To2mPerson to2mOwnedCars(Set<To2mCar> to2mCars) {
        this.to2mOwnedCars = to2mCars;
        return this;
    }

    public To2mPerson addTo2mOwnedCar(To2mCar to2mCar) {
        this.to2mOwnedCars.add(to2mCar);
        to2mCar.setTo2mOwner(this);
        return this;
    }

    public To2mPerson removeTo2mOwnedCar(To2mCar to2mCar) {
        this.to2mOwnedCars.remove(to2mCar);
        to2mCar.setTo2mOwner(null);
        return this;
    }

    public void setTo2mOwnedCars(Set<To2mCar> to2mCars) {
        this.to2mOwnedCars = to2mCars;
    }

    public Set<To2mCar> getTo2mDrivedCars() {
        return to2mDrivedCars;
    }

    public To2mPerson to2mDrivedCars(Set<To2mCar> to2mCars) {
        this.to2mDrivedCars = to2mCars;
        return this;
    }

    public To2mPerson addTo2mDrivedCar(To2mCar to2mCar) {
        this.to2mDrivedCars.add(to2mCar);
        to2mCar.setTo2mDriver(this);
        return this;
    }

    public To2mPerson removeTo2mDrivedCar(To2mCar to2mCar) {
        this.to2mDrivedCars.remove(to2mCar);
        to2mCar.setTo2mDriver(null);
        return this;
    }

    public void setTo2mDrivedCars(Set<To2mCar> to2mCars) {
        this.to2mDrivedCars = to2mCars;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof To2mPerson)) {
            return false;
        }
        return id != null && id.equals(((To2mPerson) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "To2mPerson{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
