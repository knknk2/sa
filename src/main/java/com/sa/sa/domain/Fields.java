package com.sa.sa.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.Duration;
import java.util.UUID;

import com.sa.sa.domain.enumeration.Enum1;

/**
 * A Fields.
 */
@Entity
@Table(name = "fields")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "fields")
public class Fields implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Size(min = 2, max = 255)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(name = "str", length = 255, nullable = false, unique = true)
    private String str;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "num_1", nullable = false, unique = true)
    private Integer num1;

    @NotNull
    @Min(value = 0L)
    @Max(value = 1000L)
    @Column(name = "num_2", nullable = false, unique = true)
    private Long num2;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "num_3", nullable = false, unique = true)
    private Float num3;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "num_4", nullable = false, unique = true)
    private Double num4;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "num_5", precision = 21, scale = 2, nullable = false, unique = true)
    private BigDecimal num5;

    @NotNull
    @Column(name = "date_1", nullable = false, unique = true)
    private LocalDate date1;

    @NotNull
    @Column(name = "date_2", nullable = false, unique = true)
    private Instant date2;

    @NotNull
    @Column(name = "date_3", nullable = false, unique = true)
    private ZonedDateTime date3;

    @NotNull
    @Column(name = "date_4", nullable = false, unique = true)
    private Duration date4;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private UUID uuid;

    @NotNull
    @Column(name = "bool", nullable = false)
    private Boolean bool;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "enumeration", nullable = false)
    private Enum1 enumeration;

    
    @Lob
    @Column(name = "blob", nullable = false)
    private byte[] blob;

    @Column(name = "blob_content_type", nullable = false)
    private String blobContentType;

    
    @Lob
    @Column(name = "blob_2", nullable = false)
    private byte[] blob2;

    @Column(name = "blob_2_content_type", nullable = false)
    private String blob2ContentType;

    @Size(min = 2, max = 255)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "blob_3", length = 255, nullable = false, unique = true)
    private String blob3;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public Fields str(String str) {
        this.str = str;
        return this;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Integer getNum1() {
        return num1;
    }

    public Fields num1(Integer num1) {
        this.num1 = num1;
        return this;
    }

    public void setNum1(Integer num1) {
        this.num1 = num1;
    }

    public Long getNum2() {
        return num2;
    }

    public Fields num2(Long num2) {
        this.num2 = num2;
        return this;
    }

    public void setNum2(Long num2) {
        this.num2 = num2;
    }

    public Float getNum3() {
        return num3;
    }

    public Fields num3(Float num3) {
        this.num3 = num3;
        return this;
    }

    public void setNum3(Float num3) {
        this.num3 = num3;
    }

    public Double getNum4() {
        return num4;
    }

    public Fields num4(Double num4) {
        this.num4 = num4;
        return this;
    }

    public void setNum4(Double num4) {
        this.num4 = num4;
    }

    public BigDecimal getNum5() {
        return num5;
    }

    public Fields num5(BigDecimal num5) {
        this.num5 = num5;
        return this;
    }

    public void setNum5(BigDecimal num5) {
        this.num5 = num5;
    }

    public LocalDate getDate1() {
        return date1;
    }

    public Fields date1(LocalDate date1) {
        this.date1 = date1;
        return this;
    }

    public void setDate1(LocalDate date1) {
        this.date1 = date1;
    }

    public Instant getDate2() {
        return date2;
    }

    public Fields date2(Instant date2) {
        this.date2 = date2;
        return this;
    }

    public void setDate2(Instant date2) {
        this.date2 = date2;
    }

    public ZonedDateTime getDate3() {
        return date3;
    }

    public Fields date3(ZonedDateTime date3) {
        this.date3 = date3;
        return this;
    }

    public void setDate3(ZonedDateTime date3) {
        this.date3 = date3;
    }

    public Duration getDate4() {
        return date4;
    }

    public Fields date4(Duration date4) {
        this.date4 = date4;
        return this;
    }

    public void setDate4(Duration date4) {
        this.date4 = date4;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Fields uuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Boolean isBool() {
        return bool;
    }

    public Fields bool(Boolean bool) {
        this.bool = bool;
        return this;
    }

    public void setBool(Boolean bool) {
        this.bool = bool;
    }

    public Enum1 getEnumeration() {
        return enumeration;
    }

    public Fields enumeration(Enum1 enumeration) {
        this.enumeration = enumeration;
        return this;
    }

    public void setEnumeration(Enum1 enumeration) {
        this.enumeration = enumeration;
    }

    public byte[] getBlob() {
        return blob;
    }

    public Fields blob(byte[] blob) {
        this.blob = blob;
        return this;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }

    public String getBlobContentType() {
        return blobContentType;
    }

    public Fields blobContentType(String blobContentType) {
        this.blobContentType = blobContentType;
        return this;
    }

    public void setBlobContentType(String blobContentType) {
        this.blobContentType = blobContentType;
    }

    public byte[] getBlob2() {
        return blob2;
    }

    public Fields blob2(byte[] blob2) {
        this.blob2 = blob2;
        return this;
    }

    public void setBlob2(byte[] blob2) {
        this.blob2 = blob2;
    }

    public String getBlob2ContentType() {
        return blob2ContentType;
    }

    public Fields blob2ContentType(String blob2ContentType) {
        this.blob2ContentType = blob2ContentType;
        return this;
    }

    public void setBlob2ContentType(String blob2ContentType) {
        this.blob2ContentType = blob2ContentType;
    }

    public String getBlob3() {
        return blob3;
    }

    public Fields blob3(String blob3) {
        this.blob3 = blob3;
        return this;
    }

    public void setBlob3(String blob3) {
        this.blob3 = blob3;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fields)) {
            return false;
        }
        return id != null && id.equals(((Fields) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Fields{" +
            "id=" + getId() +
            ", str='" + getStr() + "'" +
            ", num1=" + getNum1() +
            ", num2=" + getNum2() +
            ", num3=" + getNum3() +
            ", num4=" + getNum4() +
            ", num5=" + getNum5() +
            ", date1='" + getDate1() + "'" +
            ", date2='" + getDate2() + "'" +
            ", date3='" + getDate3() + "'" +
            ", date4='" + getDate4() + "'" +
            ", uuid='" + getUuid() + "'" +
            ", bool='" + isBool() + "'" +
            ", enumeration='" + getEnumeration() + "'" +
            ", blob='" + getBlob() + "'" +
            ", blobContentType='" + getBlobContentType() + "'" +
            ", blob2='" + getBlob2() + "'" +
            ", blob2ContentType='" + getBlob2ContentType() + "'" +
            ", blob3='" + getBlob3() + "'" +
            "}";
    }
}
