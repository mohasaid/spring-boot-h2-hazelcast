package com.moha.test.domain.request;

import com.moha.test.domain.host.Host;

import java.util.List;

public final class RequestBuilder {

    private Request request;

    private RequestBuilder() {
        request = new Request();
    }

    public static RequestBuilder aRequest() {
        return new RequestBuilder();
    }

    public RequestBuilder withAccountCode(String accountCode) {
        request.setAccountCode(accountCode);
        return this;
    }

    public RequestBuilder withTargetDevice(String targetDevice) {
        request.setTargetDevice(targetDevice);
        return this;
    }

    public RequestBuilder withPluginVersion(String pluginVersion) {
        request.setPluginVersion(pluginVersion);
        return this;
    }

    public RequestBuilder withPingTime(Integer pingTime) {
        request.setPingTime(pingTime);
        return this;
    }

    public RequestBuilder withHosts(List<Host> hosts) {
        request.setHosts(hosts);
        return this;
    }

    public Request build() {
        return request;
    }
}
