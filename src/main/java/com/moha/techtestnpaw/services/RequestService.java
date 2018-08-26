package com.moha.techtestnpaw.services;

import com.moha.techtestnpaw.domain.request.Request;

import java.util.List;

public interface RequestService {

    List<Request> findByAccountCode(final String accountCode);

    Request save(final Request request);

    void delete(final String accountCode,
               final String targetDevice,
               final String pluginVersion);
}
