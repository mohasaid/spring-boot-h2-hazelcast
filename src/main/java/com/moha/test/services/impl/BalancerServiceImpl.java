package com.moha.test.services.impl;

import com.moha.test.domain.host.Host;
import com.moha.test.domain.request.Request;
import com.moha.test.domain.request.RequestId;
import com.moha.test.domain.requestresponse.RequestResponse;
import com.moha.test.domain.requestresponse.RequestResponseWriter;
import com.moha.test.exceptions.BalancerException;
import com.moha.test.services.BalancerService;
import com.moha.test.utils.ViewCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BalancerServiceImpl implements BalancerService {

    private final RequestResponseWriter requestResponseWriter;
    private final Map<RequestId, Request> requestCache = new HashMap<>();

    @Autowired
    public BalancerServiceImpl(RequestResponseWriter requestResponseWriter) {
        this.requestResponseWriter = requestResponseWriter;
    }

    public String balanceRequest(Request request) throws BalancerException {

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

        try {
            return requestResponseWriter.getResponse(requestResponse);
        } catch (JAXBException exception) {
            throw new BalancerException("Error while getting xml string of request response", exception);
        }
    }
}
