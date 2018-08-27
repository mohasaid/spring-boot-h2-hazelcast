package com.moha.techtestnpaw.domain.host;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class HostListConverter implements AttributeConverter<List<Host>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Host> hosts) {
        try {
            return objectMapper.writeValueAsString(hosts);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    @Override
    public List<Host> convertToEntityAttribute(String hosts) {
        try {
            return Arrays.asList(objectMapper.readValue(hosts, Host[].class));
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
