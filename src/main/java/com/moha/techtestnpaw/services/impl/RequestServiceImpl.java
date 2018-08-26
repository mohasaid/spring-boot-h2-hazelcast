package com.moha.techtestnpaw.services.impl;

import com.moha.techtestnpaw.domain.request.Request;
import com.moha.techtestnpaw.repository.RequestRepository;
import com.moha.techtestnpaw.services.RequestService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.moha.techtestnpaw.config.HazelcastConfig.HAZELCAST_REQUEST_CONFIG;

@Service
@CacheConfig(cacheNames = HAZELCAST_REQUEST_CONFIG)
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    @Cacheable
    public List<Request> findByAccountCode(final String accountCode) {
        return requestRepository.findByAccountCode(accountCode);
    }

    @Override
    public Request save(final Request request) {
        return requestRepository.save(request);
    }

    @Override
    public void delete(String accountCode, String targetDevice, String pluginVersion) {
        requestRepository.deleteByAccountCodeAndTargetDeviceAndPluginVersion(accountCode, targetDevice, pluginVersion);
    }
}
