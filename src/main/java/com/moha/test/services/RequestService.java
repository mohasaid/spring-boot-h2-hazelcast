package com.moha.test.services;

import com.moha.test.domain.request.Request;
import com.moha.test.domain.request.RequestId;

import java.util.Optional;

public interface RequestService {

    Optional<Request> findById(final RequestId requestId);

    Request save(final Request request);

    void deleteById(final RequestId requestId);
}
