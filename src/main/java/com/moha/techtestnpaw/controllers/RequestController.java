package com.moha.techtestnpaw.controllers;

import com.moha.techtestnpaw.domain.Request;
import com.moha.techtestnpaw.services.RequestService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RequestController {

    private RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping(value = "/getData", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity getData(@RequestParam(value = "accountCode") String accountCode,
                                  @RequestParam(value = "targetDevice") String targetDevice,
                                  @RequestParam(value = "pluginVersion") String pluginVersion) {

        List<Request> requestList = requestService.findRequest(accountCode);

        if (requestList.isEmpty()) {
            return ResponseEntity.ok().body("Ok");
        }

        String message = "GetData GET request!\n"
                + "accountCode = " + accountCode + "\n"
                + "targetDevice = " + targetDevice + "\n"
                + "pluginVersion = " + pluginVersion + "\n";

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE);
        responseHeaders.set("MyResponseHeader", message);

        return ResponseEntity
                .ok()
                .headers(responseHeaders)
                .build();
    }
}
