package com.moha.test.controllers;

import com.moha.test.domain.requestresponse.RequestResponse;
import com.moha.test.domain.requestresponse.RequestResponseWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class RequestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RequestResponseWriter requestResponseWriter;
    @MockBean
    private RequestController requestController;

    @Test
    public void givenRequest_whenGetData_ReturnsXMLRequestResponse() throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("accountCode", "clienteA");
        params.add("targetDevice", "XBox");
        params.add("pluginVersion", "3.3.1");

        final RequestResponse requestResponse = new RequestResponse("host", 10, "codeview");
        final String bodyResponse = requestResponseWriter.getResponse(requestResponse);

        given(requestController.getData("clienteA", "XBox", "3.3.1"))
                .willReturn(ResponseEntity.ok().body(bodyResponse));

        mockMvc.perform(get("/getData").params(params))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/xml;charset=UTF-8"))
                .andExpect(content().string((bodyResponse)));
    }

    @Test
    public void givenRequest_whenAddData_ReturnsCreated() throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("accountCode", "clienteA");
        params.add("targetDevice", "XBox");
        params.add("pluginVersion", "3.3.1");
        params.add("pingTime", "10");
        params.add("hosts", "[{\"name\":\"host-test\",\"load\":10}]");

        given(requestController.addData("clienteA", "XBox", "3.3.1", "10", "[{\"name\":\"host-test\",\"load\":10}]"))
                .willReturn(new ResponseEntity(HttpStatus.CREATED));

        mockMvc.perform(post("/addData").params(params))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenRequest_whenDeleteData_ReturnsOk() throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("accountCode", "clienteA");
        params.add("targetDevice", "XBox");
        params.add("pluginVersion", "3.3.1");

        given(requestController.deleteData("clienteA", "XBox", "3.3.1"))
                .willReturn(ResponseEntity.ok().build());

        mockMvc.perform(post("/deleteData").params(params))
                .andExpect(status().isOk());
    }
}