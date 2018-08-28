package com.moha.techtestnpaw.domain.request;

import java.io.Serializable;
import java.util.Objects;

public class RequestId implements Serializable {

    private static final long serialVersionUID = 1095922479976975297L;

    private String accountCode;
    private String targetDevice;
    private String pluginVersion;

    public RequestId() {
    }

    public RequestId(String accountCode, String targetDevice, String pluginVersion) {
        this.accountCode = accountCode;
        this.targetDevice = targetDevice;
        this.pluginVersion = pluginVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestId requestId = (RequestId) o;
        return Objects.equals(accountCode, requestId.accountCode) &&
                Objects.equals(targetDevice, requestId.targetDevice) &&
                Objects.equals(pluginVersion, requestId.pluginVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountCode, targetDevice, pluginVersion);
    }

    @Override
    public String toString() {
        return "RequestId{" +
                "accountCode='" + accountCode + '\'' +
                ", targetDevice='" + targetDevice + '\'' +
                ", pluginVersion='" + pluginVersion + '\'' +
                '}';
    }
}
