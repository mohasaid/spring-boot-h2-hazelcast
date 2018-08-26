package com.moha.techtestnpaw.domain;

import java.io.Serializable;
import java.util.Objects;

public class Host implements Serializable {

    private static final long serialVersionUID = 847321702951316123L;

    private String name;
    private String percentageLoad;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPercentageLoad() {
        return percentageLoad;
    }

    public void setPercentageLoad(String percentageLoad) {
        this.percentageLoad = percentageLoad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Host host = (Host) o;
        return Objects.equals(name, host.name) &&
                Objects.equals(percentageLoad, host.percentageLoad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, percentageLoad);
    }

    @Override
    public String toString() {
        return "Host{" +
                "name='" + name + '\'' +
                ", percentageLoad='" + percentageLoad + '\'' +
                '}';
    }
}
