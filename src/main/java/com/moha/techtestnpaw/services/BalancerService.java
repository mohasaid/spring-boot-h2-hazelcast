package com.moha.techtestnpaw.services;

import com.moha.techtestnpaw.domain.host.Host;
import com.moha.techtestnpaw.domain.request.Request;
import com.moha.techtestnpaw.domain.request.RequestId;
import com.moha.techtestnpaw.domain.requestresponse.RequestResponse;
import com.moha.techtestnpaw.domain.requestresponse.RequestResponseWriter;
import com.moha.techtestnpaw.utils.ViewCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BalancerService {

    private final RequestResponseWriter requestResponseWriter;
    private final Map<RequestId, Request> requestCache = new HashMap<>();

    @Autowired
    public BalancerService(RequestResponseWriter requestResponseWriter) {
        this.requestResponseWriter = requestResponseWriter;
    }

    public String balanceRequest(Request request) throws JAXBException {

        RequestId requestId = request.getId();

        if (requestCache.containsKey(requestId)) {
            request = requestCache.get(requestId);
        } else {
            requestCache.put(requestId, request);
        }

        Optional<Host> optionalHost = request.getHosts().stream().max(Comparator.comparingInt(Host::getPercentageAccum));

        RequestResponse requestResponse = null;
        if (optionalHost.isPresent()) {
            Host host = optionalHost.get();
            requestResponse = new RequestResponse(host.getName(), request.getPingTime(), ViewCodeGenerator.getViewCode());
            Integer percentageAccum = Math.abs(100 - host.getPercentageAccum());
            host.setPercentageAccum(percentageAccum);
        }

        request.getHosts().forEach(
                host -> host.setPercentageAccum(host.getPercentageAccum() + host.getPercentageLoad())
        );

        return requestResponseWriter.getResponse(requestResponse);
    }
}
