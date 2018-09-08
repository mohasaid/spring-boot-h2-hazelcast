package com.moha.test.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moha.test.domain.host.Host;
import com.moha.test.domain.request.Request;
import com.moha.test.domain.request.RequestBuilder;
import com.moha.test.domain.request.RequestId;
import com.moha.test.domain.requestresponse.RequestResponse;
import com.moha.test.exceptions.BalancerException;
import com.moha.test.services.BalancerService;
import com.moha.test.services.RequestService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@Api(tags = "Requests controller", value = "possible operations for requests")
public class RequestController {

    private final RequestService requestService;
    private final BalancerService balancerService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public RequestController(RequestService requestService, BalancerService balancerService) {
        this.requestService = requestService;
        this.balancerService = balancerService;
    }

    @RequestMapping("/")
    @ApiIgnore
    public void home(HttpServletResponse httpResponse) throws IOException {
        httpResponse.sendRedirect("/swagger-ui.html");
    }

    @ApiOperation(
            value = "Returns data from a request",
            notes = "Returns a xml which specifies the view code, the ping time and the cluster that will receive the information sent by the plugin.",
            response = RequestResponse.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 204, message = "Data not found in database"),
            @ApiResponse(code = 405, message = "Invalid input"),
    })
    @GetMapping(value = "/getData", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity getData(@ApiParam(value = "account code of the request", required = true, example = "clienteA") @RequestParam(value = "accountCode") String accountCode,
                                  @ApiParam(value = "target device of the request", required = true, example = "XBox") @RequestParam(value = "targetDevice") String targetDevice,
                                  @ApiParam(value = "plugin version that is using", required = true, example = "3.3.1") @RequestParam(value = "pluginVersion") String pluginVersion)
            throws BalancerException {

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

    @ApiOperation(
            value = "Adds a new request to the database",
            notes = "Request object that needs to be added to the database."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 405, message = "Invalid input")
    })
    @PostMapping(value = "/addData")
    @ResponseStatus
    public ResponseEntity addData(@ApiParam(value = "account code of the request", required = true, example = "clienteC") @RequestParam(value = "accountCode") String accountCode,
                                  @ApiParam(value = "target device of the request", required = true, example = "XBox") @RequestParam(value = "targetDevice") String targetDevice,
                                  @ApiParam(value = "plugin version of the request", required = true, example = "3.3.1") @RequestParam(value = "pluginVersion") String pluginVersion,
                                  @ApiParam(value = "ping time of the request", required = true, example = "10") @RequestParam(value = "pingTime") String pingTime,
                                  @ApiParam(value = "hosts of the request", required = true, example = "[{\"name\":\"host-test\",\"load\":10}]") @RequestParam(value = "hosts") String hosts)
            throws IOException {

        List<Host> hostList = Arrays.asList(objectMapper.readValue(hosts, Host[].class));
        final Request request = RequestBuilder.aRequest()
                .withAccountCode(accountCode)
                .withTargetDevice(targetDevice)
                .withPluginVersion(pluginVersion)
                .withPingTime(Integer.valueOf(pingTime))
                .withHosts(hostList)
                .build();

        requestService.save(request);

        return new ResponseEntity(HttpStatus.CREATED);
    }


    @ApiOperation(
            value = "Deletes request data from database",
            notes = "Request identifier that needs to be deleted from database."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Invalid input")}
    )
    @PostMapping(value = "/deleteData")
    public ResponseEntity deleteData(@ApiParam(value = "account code of the request", required = true, example = "clienteA") @RequestParam(value = "accountCode") String accountCode,
                                     @ApiParam(value = "target device of the request", required = true, example = "XBox") @RequestParam(value = "targetDevice") String targetDevice,
                                     @ApiParam(value = "plugin version of the request", required = true, example = "3.3.1") @RequestParam(value = "pluginVersion") String pluginVersion) {
        requestService.deleteById(new RequestId(accountCode, targetDevice, pluginVersion));
        return ResponseEntity.ok().build();
    }
}
