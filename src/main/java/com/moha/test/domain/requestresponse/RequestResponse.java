package com.moha.test.domain.requestresponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement(name = "q")
@XmlType(propOrder = {"host", "pingTime", "code"})
public class RequestResponse implements Serializable {

    private static final long serialVersionUID = -2165348254717812579L;

    private String host;
    private Integer pingTime = 5;
    private String code;

    public RequestResponse() {
    }

    public RequestResponse(String host, Integer pingTime, String code) {
        this.host = host;
        this.pingTime = pingTime;
        this.code = code;
    }

    @XmlElement(name = "h")
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @XmlElement(name = "pt")
    public Integer getPingTime() {
        return pingTime;
    }

    public void setPingTime(Integer pingTime) {
        this.pingTime = pingTime;
    }

    @XmlElement(name = "c")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestResponse that = (RequestResponse) o;
        return Objects.equals(host, that.host) &&
                Objects.equals(pingTime, that.pingTime) &&
                Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, pingTime, code);
    }
}
