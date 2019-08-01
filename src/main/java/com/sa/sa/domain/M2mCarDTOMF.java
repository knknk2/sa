package com.sa.sa.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A M2mCarDTOMF.
 */
@Entity
@Table(name = "m2m_car")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "m2mcardtomf")
public class M2mCarDTOMF implements Serializable {

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
    @JoinTable(name = "m2m_car_m2m_driverdtomf",
               joinColumns = @JoinColumn(name = "m2m_cardtomf_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "m2m_driverdtomf_id", referencedColumnName = "id"))
    private Set<M2mDriverDTOMF> m2mDriverDTOMFS = new HashSet<>();

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

    public M2mCarDTOMF name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<M2mDriverDTOMF> getM2mDriverDTOMFS() {
        return m2mDriverDTOMFS;
    }

    public M2mCarDTOMF m2mDriverDTOMFS(Set<M2mDriverDTOMF> m2mDriverDTOMFS) {
        this.m2mDriverDTOMFS = m2mDriverDTOMFS;
        return this;
    }

    public M2mCarDTOMF addM2mDriverDTOMF(M2mDriverDTOMF m2mDriverDTOMF) {
        this.m2mDriverDTOMFS.add(m2mDriverDTOMF);
        m2mDriverDTOMF.getM2mCarDTOMFS().add(this);
        return this;
    }

    public M2mCarDTOMF removeM2mDriverDTOMF(M2mDriverDTOMF m2mDriverDTOMF) {
        this.m2mDriverDTOMFS.remove(m2mDriverDTOMF);
        m2mDriverDTOMF.getM2mCarDTOMFS().remove(this);
        return this;
    }

    public void setM2mDriverDTOMFS(Set<M2mDriverDTOMF> m2mDriverDTOMFS) {
        this.m2mDriverDTOMFS = m2mDriverDTOMFS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof M2mCarDTOMF)) {
            return false;
        }
        return id != null && id.equals(((M2mCarDTOMF) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "M2mCarDTOMF{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
