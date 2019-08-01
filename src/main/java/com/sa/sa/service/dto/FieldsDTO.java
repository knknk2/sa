package com.sa.sa.service.dto;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.Duration;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Lob;
import com.sa.sa.domain.enumeration.Enum1;

/**
 * A DTO for the {@link com.sa.sa.domain.Fields} entity.
 */
public class FieldsDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 255)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String str;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer num1;

    @NotNull
    @Min(value = 0L)
    @Max(value = 1000L)
    private Long num2;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Float num3;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private Double num4;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    private BigDecimal num5;

    @NotNull
    private LocalDate date1;

    @NotNull
    private Instant date2;

    @NotNull
    private ZonedDateTime date3;

    @NotNull
    private Duration date4;

    @NotNull
    private UUID uuid;

    @NotNull
    private Boolean bool;

    @NotNull
    private Enum1 enumeration;

    
    @Lob
    private byte[] blob;

    private String blobContentType;
    
    @Lob
    private byte[] blob2;

    private String blob2ContentType;
    @Size(min = 2, max = 255)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Lob
    private String blob3;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Integer getNum1() {
        return num1;
    }

    public void setNum1(Integer num1) {
        this.num1 = num1;
    }

    public Long getNum2() {
        return num2;
    }

    public void setNum2(Long num2) {
        this.num2 = num2;
    }

    public Float getNum3() {
        return num3;
    }

    public void setNum3(Float num3) {
        this.num3 = num3;
    }

    public Double getNum4() {
        return num4;
    }

    public void setNum4(Double num4) {
        this.num4 = num4;
    }

    public BigDecimal getNum5() {
        return num5;
    }

    public void setNum5(BigDecimal num5) {
        this.num5 = num5;
    }

    public LocalDate getDate1() {
        return date1;
    }

    public void setDate1(LocalDate date1) {
        this.date1 = date1;
    }

    public Instant getDate2() {
        return date2;
    }

    public void setDate2(Instant date2) {
        this.date2 = date2;
    }

    public ZonedDateTime getDate3() {
        return date3;
    }

    public void setDate3(ZonedDateTime date3) {
        this.date3 = date3;
    }

    public Duration getDate4() {
        return date4;
    }

    public void setDate4(Duration date4) {
        this.date4 = date4;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Boolean isBool() {
        return bool;
    }

    public void setBool(Boolean bool) {
        this.bool = bool;
    }

    public Enum1 getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(Enum1 enumeration) {
        this.enumeration = enumeration;
    }

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }

    public String getBlobContentType() {
        return blobContentType;
    }

    public void setBlobContentType(String blobContentType) {
        this.blobContentType = blobContentType;
    }

    public byte[] getBlob2() {
        return blob2;
    }

    public void setBlob2(byte[] blob2) {
        this.blob2 = blob2;
    }

    public String getBlob2ContentType() {
        return blob2ContentType;
    }

    public void setBlob2ContentType(String blob2ContentType) {
        this.blob2ContentType = blob2ContentType;
    }

    public String getBlob3() {
        return blob3;
    }

    public void setBlob3(String blob3) {
        this.blob3 = blob3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FieldsDTO fieldsDTO = (FieldsDTO) o;
        if (fieldsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fieldsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FieldsDTO{" +
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
            ", blob2='" + getBlob2() + "'" +
            ", blob3='" + getBlob3() + "'" +
            "}";
    }
}
