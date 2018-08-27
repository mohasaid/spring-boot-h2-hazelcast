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
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class BalancerService {

    private final RequestCacheService requestCacheService;
    private final RequestResponseWriter requestResponseWriter;

    @Autowired
    public BalancerService(RequestCacheService requestCacheService, RequestResponseWriter requestResponseWriter) {
        this.requestCacheService = requestCacheService;
        this.requestResponseWriter = requestResponseWriter;
    }

    public String balanceRequest(Request request) throws JAXBException {

        RequestId requestId = request.getId();

        Optional<Request> optionalRequest = getFromCache(requestId);
        if (!optionalRequest.isPresent()) {
            addToCache(request);
        } else {
            request = optionalRequest.get();
        }

        // Get maximum percentageLoad
        Optional<Host> optionalHost = request.getHosts().stream().max(Comparator.comparingInt(Host::getPercentageLoad));
        RequestResponse requestResponse = null;
        if (optionalHost.isPresent()) {
            Host host = optionalHost.get();
            requestResponse = new RequestResponse(host.getName(), request.getPingTime(), ViewCodeGenerator.getViewCode());
            // Substract 100 from percentageAccum
            Integer percentageAccum = 100 - host.getPercentageAccum();
            host.setPercentageAccum(percentageAccum);
        }

        // Add percentageLoad to percentageAccum for all hosts
        request.getHosts().forEach(
                host -> host.setPercentageAccum(host.getPercentageAccum() + host.getPercentageLoad())
        );

        return requestResponseWriter.getResponse(requestResponse);
    }

    private Optional<Request> getFromCache(final RequestId requestId) {
        return requestCacheService.get(RequestCacheService.CacheType.REQUESTS, requestId);
    }

    private void addToCache(final Request request) {
        requestCacheService.put(RequestCacheService.CacheType.REQUESTS, request.getId(), request, 5, TimeUnit.MINUTES);
    }

}
