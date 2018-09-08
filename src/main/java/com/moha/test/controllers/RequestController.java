package com.moha.test.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moha.test.domain.host.Host;
import com.moha.test.domain.request.Request;
import com.moha.test.domain.request.RequestBuilder;
import com.moha.test.domain.request.RequestId;
import com.moha.test.exceptions.BalancerException;
import com.moha.test.services.BalancerService;
import com.moha.test.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class RequestController {

    private final RequestService requestService;
    private final BalancerService balancerService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public RequestController(RequestService requestService, BalancerService balancerService) {
        this.requestService = requestService;
        this.balancerService = balancerService;
    }

    @GetMapping(value = "/getData", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity getData(@RequestParam(value = "accountCode") String accountCode,
                                  @RequestParam(value = "targetDevice") String targetDevice,
                                  @RequestParam(value = "pluginVersion") String pluginVersion) throws BalancerException {

        Optional<Request> request = requestService.findById(new RequestId(accountCode, targetDevice, pluginVersion));

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE);

        if (!request.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity
                    .ok()
                    .headers(responseHeaders)
                    .body(balancerService.balanceRequest(request.get()));
        }
    }

    @PostMapping(value = "/addData")
    public void addData(@RequestParam(value = "accountCode") String accountCode,
                        @RequestParam(value = "targetDevice") String targetDevice,
                        @RequestParam(value = "pluginVersion") String pluginVersion,
                        @RequestParam(value = "pingTime") String pingTime,
                        @RequestParam(value = "hosts") String hosts) throws IOException {

        List<Host> hostList = Arrays.asList(objectMapper.readValue(hosts, Host[].class));
        final Request request = RequestBuilder.aRequest()
                .withAccountCode(accountCode)
                .withTargetDevice(targetDevice)
                .withPluginVersion(pluginVersion)
                .withPingTime(Integer.valueOf(pingTime))
                .withHosts(hostList)
                .build();

        requestService.save(request);
    }

    @PostMapping(value = "/deleteData")
    public void deleteData(@RequestParam(value = "accountCode") String accountCode,
                           @RequestParam(value = "targetDevice") String targetDevice,
                           @RequestParam(value = "pluginVersion") String pluginVersion) {
        requestService.deleteById(new RequestId(accountCode, targetDevice, pluginVersion));
    }
}
