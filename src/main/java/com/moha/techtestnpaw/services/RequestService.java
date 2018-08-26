package com.moha.techtestnpaw.services;

import com.moha.techtestnpaw.domain.Request;

import java.util.List;

public interface RequestService {

    List<Request> findRequest(String accountCode);

    void save(Request request);

}
