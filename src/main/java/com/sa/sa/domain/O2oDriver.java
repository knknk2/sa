package com.sa.sa.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A O2oDriver.
 */
@Entity
@Table(name = "o2o_driver")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "o2odriver")
public class O2oDriver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "o2oDriver")
    @JsonIgnore
    private O2oCar o2oCar;

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

    public O2oDriver name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public O2oCar getO2oCar() {
        return o2oCar;
    }

    public O2oDriver o2oCar(O2oCar o2oCar) {
        this.o2oCar = o2oCar;
        return this;
    }

    public void setO2oCar(O2oCar o2oCar) {
        this.o2oCar = o2oCar;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof O2oDriver)) {
            return false;
        }
        return id != null && id.equals(((O2oDriver) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "O2oDriver{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
