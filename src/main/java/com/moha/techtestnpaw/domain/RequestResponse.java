package com.moha.techtestnpaw.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "q")
public class RequestResponse implements Serializable {

    private static final long serialVersionUID = -2165348254717812579L;

    private String host;
    private Integer pingTime;
    private String code;

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
}
