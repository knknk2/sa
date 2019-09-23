package com.sa.sa.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A To2mCarInf.
 */
@Entity
@Table(name = "to2m_car")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "to2mcarinf")
public class To2mCarInf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("to2mCarInfs")
    private To2mPersonInf to2mOwnerInf;

    @ManyToOne
    @JsonIgnoreProperties("to2mCarInfs")
    private To2mPersonInf to2mDriverInf;

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

    public To2mCarInf name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public To2mPersonInf getTo2mOwnerInf() {
        return to2mOwnerInf;
    }

    public To2mCarInf to2mOwnerInf(To2mPersonInf to2mPersonInf) {
        this.to2mOwnerInf = to2mPersonInf;
        return this;
    }

    public void setTo2mOwnerInf(To2mPersonInf to2mPersonInf) {
        this.to2mOwnerInf = to2mPersonInf;
    }

    public To2mPersonInf getTo2mDriverInf() {
        return to2mDriverInf;
    }

    public To2mCarInf to2mDriverInf(To2mPersonInf to2mPersonInf) {
        this.to2mDriverInf = to2mPersonInf;
        return this;
    }

    public void setTo2mDriverInf(To2mPersonInf to2mPersonInf) {
        this.to2mDriverInf = to2mPersonInf;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof To2mCarInf)) {
            return false;
        }
        return id != null && id.equals(((To2mCarInf) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "To2mCarInf{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
