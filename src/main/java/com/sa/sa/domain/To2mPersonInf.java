package com.sa.sa.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A To2mPersonInf.
 */
@Entity
@Table(name = "to2m_person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "to2mpersoninf")
public class To2mPersonInf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "to2mOwnerInf")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<To2mCarInf> to2mOwnedCarInfs = new HashSet<>();

    @OneToMany(mappedBy = "to2mDriverInf")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<To2mCarInf> to2mDrivedCarInfs = new HashSet<>();

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

    public To2mPersonInf name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<To2mCarInf> getTo2mOwnedCarInfs() {
        return to2mOwnedCarInfs;
    }

    public To2mPersonInf to2mOwnedCarInfs(Set<To2mCarInf> to2mCarInfs) {
        this.to2mOwnedCarInfs = to2mCarInfs;
        return this;
    }

    public To2mPersonInf addTo2mOwnedCarInf(To2mCarInf to2mCarInf) {
        this.to2mOwnedCarInfs.add(to2mCarInf);
        to2mCarInf.setTo2mOwnerInf(this);
        return this;
    }

    public To2mPersonInf removeTo2mOwnedCarInf(To2mCarInf to2mCarInf) {
        this.to2mOwnedCarInfs.remove(to2mCarInf);
        to2mCarInf.setTo2mOwnerInf(null);
        return this;
    }

    public void setTo2mOwnedCarInfs(Set<To2mCarInf> to2mCarInfs) {
        this.to2mOwnedCarInfs = to2mCarInfs;
    }

    public Set<To2mCarInf> getTo2mDrivedCarInfs() {
        return to2mDrivedCarInfs;
    }

    public To2mPersonInf to2mDrivedCarInfs(Set<To2mCarInf> to2mCarInfs) {
        this.to2mDrivedCarInfs = to2mCarInfs;
        return this;
    }

    public To2mPersonInf addTo2mDrivedCarInf(To2mCarInf to2mCarInf) {
        this.to2mDrivedCarInfs.add(to2mCarInf);
        to2mCarInf.setTo2mDriverInf(this);
        return this;
    }

    public To2mPersonInf removeTo2mDrivedCarInf(To2mCarInf to2mCarInf) {
        this.to2mDrivedCarInfs.remove(to2mCarInf);
        to2mCarInf.setTo2mDriverInf(null);
        return this;
    }

    public void setTo2mDrivedCarInfs(Set<To2mCarInf> to2mCarInfs) {
        this.to2mDrivedCarInfs = to2mCarInfs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof To2mPersonInf)) {
            return false;
        }
        return id != null && id.equals(((To2mPersonInf) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "To2mPersonInf{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
