package com.moha.techtestnpaw;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moha.techtestnpaw.domain.Host;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HostMapperTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void hostMappedCorrectlyWithoutHost() throws IOException {
        JSONArray jsonArray = new JSONArray();
        List<Host> hostList = Arrays.asList(objectMapper.readValue(jsonArray.toString(), Host[].class));
        assertEquals(0, hostList.size(), 0);
    }

    @Test
    public void hostMappedCorrectlyOneHost() throws JSONException, IOException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "hmoha");
        jsonObject.put("load", "10");
        jsonArray.put(jsonObject);
        List<Host> hostList = Arrays.asList(objectMapper.readValue(jsonArray.toString(), Host[].class));
        assertEquals(1, hostList.size(), 0);
        assertTrue(hostList.stream().allMatch(Objects::nonNull));
    }

    @Test
    public void hostMappedCorrectlyMultipleHosts() throws JSONException, IOException {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "hmoha");
        jsonObject.put("load", "10");
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("name", "hmoha1");
        jsonObject1.put("load", "101");
        jsonArray.put(jsonObject);
        jsonArray.put(jsonObject1);
        List<Host> hostList = Arrays.asList(objectMapper.readValue(jsonArray.toString(), Host[].class));
        assertEquals(2, hostList.size(), 0);
        assertTrue(hostList.stream().allMatch(Objects::nonNull));
    }

    @Test
    public void hostMappedCorrectlyEndPoint() throws JSONException, IOException {
        String hosts = "[{\"name\":\"h-moha\",\"load\":10},{\"name\":\"h-moha1\",\"load\":101}]";
        List<Host> hostList = Arrays.asList(objectMapper.readValue(hosts, Host[].class));
        assertEquals(2, hostList.size(), 0);
        assertTrue(hostList.stream().allMatch(Objects::nonNull));
    }

    @Test
    public void hostMappedCorrectlyEndPointOneHost() throws JSONException, IOException {
        String hosts = "[{\"name\":\"h-moha\",\"load\":10}]";
        List<Host> hostList = Arrays.asList(objectMapper.readValue(hosts, Host[].class));
        assertEquals(1, hostList.size(), 0);
        assertTrue(hostList.stream().allMatch(Objects::nonNull));
    }

    @Test
    public void hostMappedCorrectlyWriteValue() throws JSONException, IOException {
        String hosts = "[{\"name\":\"h-moha\",\"load\":10},{\"name\":\"h-moha1\",\"load\":101}]";
        List<Host> hostList = Arrays.asList(objectMapper.readValue(hosts, Host[].class));
        assertEquals(2, hostList.size(), 0);
        String test = objectMapper.writeValueAsString(hostList);
        assertEquals(hosts, test);
        assertTrue(hostList.stream().allMatch(Objects::nonNull));
    }

}
