package com.moha.techtestnpaw.domain.requestresponse;

import javax.xml.bind.JAXBException;

public interface RequestResponseWriter {

    String getResponse(final RequestResponse requestResponse) throws JAXBException;
}
