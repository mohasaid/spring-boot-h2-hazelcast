package com.moha.techtestnpaw.domain.host;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Objects;

public class Host implements Serializable {

    private static final long serialVersionUID = 847321702951316123L;

    @JsonProperty("name")
    private String name;
    @JsonProperty("load")
    private Integer percentageLoad;
    @Transient
    @JsonIgnore
    private Integer percentageAccum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPercentageLoad() {
        return percentageLoad;
    }

    public void setPercentageLoad(Integer percentageLoad) {
        this.percentageLoad = percentageLoad;
    }

    public Integer getPercentageAccum() {
        return percentageAccum;
    }

    public void setPercentageAccum(Integer percentageAccum) {
        this.percentageAccum = percentageAccum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Host host = (Host) o;
        return Objects.equals(name, host.name) &&
                Objects.equals(percentageLoad, host.percentageLoad) &&
                Objects.equals(percentageAccum, host.percentageAccum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, percentageLoad, percentageAccum);
    }

    @Override
    public String toString() {
        return "Host{" +
                "name='" + name + '\'' +
                ", percentageLoad=" + percentageLoad +
                ", percentageAccum=" + percentageAccum +
                '}';
    }
}
