package com.moha.techtestnpaw.services;

import com.moha.techtestnpaw.domain.request.Request;
import com.moha.techtestnpaw.domain.request.RequestId;

import java.util.Optional;

public interface RequestService {

    Optional<Request> findById(final RequestId requestId);

    Request save(final Request request);

    void deleteById(final RequestId requestId);
}
