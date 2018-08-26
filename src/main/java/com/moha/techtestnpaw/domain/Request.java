package com.moha.techtestnpaw.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RedisHash("requests")
public class Request implements Serializable {

    private static final long serialVersionUID = -3872293878329119057L;

    @Id
    private String accountCode;
    @Id
    private String targetDevice;
    @Id
    private String pluginVersion;
    private Integer pingTime;
    private List<Host> hosts = new ArrayList<>();

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getTargetDevice() {
        return targetDevice;
    }

    public void setTargetDevice(String targetDevice) {
        this.targetDevice = targetDevice;
    }

    public String getPluginVersion() {
        return pluginVersion;
    }

    public void setPluginVersion(String pluginVersion) {
        this.pluginVersion = pluginVersion;
    }

    public Integer getPingTime() {
        return pingTime;
    }

    public void setPingTime(Integer pingTime) {
        this.pingTime = pingTime;
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(accountCode, request.accountCode) &&
                Objects.equals(targetDevice, request.targetDevice) &&
                Objects.equals(pluginVersion, request.pluginVersion) &&
                Objects.equals(pingTime, request.pingTime) &&
                Objects.equals(hosts, request.hosts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountCode, targetDevice, pluginVersion, pingTime, hosts);
    }

    @Override
    public String toString() {
        return "Request{" +
                "accountCode='" + accountCode + '\'' +
                ", targetDevice='" + targetDevice + '\'' +
                ", pluginVersion='" + pluginVersion + '\'' +
                ", pingTime=" + pingTime +
                ", hosts=" + hosts +
                '}';
    }
}
