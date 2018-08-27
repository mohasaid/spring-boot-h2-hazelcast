package com.moha.techtestnpaw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moha.techtestnpaw.domain.host.Host;
import com.moha.techtestnpaw.domain.request.Request;
import com.moha.techtestnpaw.domain.request.RequestBuilder;
import com.moha.techtestnpaw.domain.request.RequestId;
import com.moha.techtestnpaw.services.BalancerService;
import com.moha.techtestnpaw.services.RequestService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class RequestController {

    private final RequestService requestService;
    private final BalancerService balancerService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RequestController(RequestService requestService, BalancerService balancerService) {
        this.requestService = requestService;
        this.balancerService = balancerService;
    }
    
    @GetMapping(value = "/getData", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity getData(@RequestParam(value = "accountCode") String accountCode,
                                  @RequestParam(value = "targetDevice") String targetDevice,
                                  @RequestParam(value = "pluginVersion") String pluginVersion) throws JAXBException {

        RequestId requestId = new RequestId(accountCode, targetDevice, pluginVersion);
        Optional<Request> request = requestService.findById(requestId);

        String message = "GetData GET request!\n"
                + "accountCode = " + accountCode + "\n"
                + "targetDevice = " + targetDevice + "\n"
                + "pluginVersion = " + pluginVersion + "\n";

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE);
        responseHeaders.set("MyResponseHeader", message);

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
        RequestId requestId = new RequestId(accountCode, targetDevice, pluginVersion);
        requestService.deleteById(requestId);
    }
}
