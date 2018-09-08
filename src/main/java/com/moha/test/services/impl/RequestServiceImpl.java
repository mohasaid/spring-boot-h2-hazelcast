package com.moha.test.services.impl;

import com.moha.test.domain.request.Request;
import com.moha.test.domain.request.RequestId;
import com.moha.test.repository.RequestRepository;
import com.moha.test.services.RequestService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.moha.test.config.HazelcastConfig.HAZELCAST_REQUEST_CONFIG;

@Service
@CacheConfig(cacheNames = HAZELCAST_REQUEST_CONFIG)
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    @Cacheable
    public Optional<Request> findById(final RequestId requestId) {
        return requestRepository.findById(requestId);
    }

    @CacheEvict
    @Override
    public Request save(final Request request) {
        return requestRepository.save(request);
    }

    @CacheEvict
    @Override
    public void deleteById(final RequestId requestId) {
        requestRepository.deleteById(requestId);
    }
}
