package com.moha.techtestnpaw.domain.requestresponse;

import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Component
public class RequestResponseToXML implements RequestResponseWriter {

    @Override
    public String getResponse(RequestResponse requestResponse) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(RequestResponse.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        // REMOVE STANDALONE = YES DEL XML
        StringWriter sw = new StringWriter();
        marshaller.marshal(requestResponse, sw);
        return sw.toString();
    }

}
