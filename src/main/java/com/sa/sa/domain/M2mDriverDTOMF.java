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
 * A M2mDriverDTOMF.
 */
@Entity
@Table(name = "m2m_driver")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "m2mdriverdtomf")
public class M2mDriverDTOMF implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "m2mDriverDTOMFS")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<M2mCarDTOMF> m2mCarDTOMFS = new HashSet<>();

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

    public M2mDriverDTOMF name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<M2mCarDTOMF> getM2mCarDTOMFS() {
        return m2mCarDTOMFS;
    }

    public M2mDriverDTOMF m2mCarDTOMFS(Set<M2mCarDTOMF> m2mCarDTOMFS) {
        this.m2mCarDTOMFS = m2mCarDTOMFS;
        return this;
    }

    public M2mDriverDTOMF addM2mCarDTOMF(M2mCarDTOMF m2mCarDTOMF) {
        this.m2mCarDTOMFS.add(m2mCarDTOMF);
        m2mCarDTOMF.getM2mDriverDTOMFS().add(this);
        return this;
    }

    public M2mDriverDTOMF removeM2mCarDTOMF(M2mCarDTOMF m2mCarDTOMF) {
        this.m2mCarDTOMFS.remove(m2mCarDTOMF);
        m2mCarDTOMF.getM2mDriverDTOMFS().remove(this);
        return this;
    }

    public void setM2mCarDTOMFS(Set<M2mCarDTOMF> m2mCarDTOMFS) {
        this.m2mCarDTOMFS = m2mCarDTOMFS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof M2mDriverDTOMF)) {
            return false;
        }
        return id != null && id.equals(((M2mDriverDTOMF) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "M2mDriverDTOMF{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
