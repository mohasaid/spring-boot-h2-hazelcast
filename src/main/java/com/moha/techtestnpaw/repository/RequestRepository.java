package com.moha.techtestnpaw.repository;

import com.moha.techtestnpaw.domain.request.Request;
import com.moha.techtestnpaw.domain.request.RequestId;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RequestRepository extends CrudRepository<Request, RequestId> {

    Optional<Request> findById(final RequestId requestId);

    Request save(final Request request);

    void deleteById(final RequestId requestId);
}
