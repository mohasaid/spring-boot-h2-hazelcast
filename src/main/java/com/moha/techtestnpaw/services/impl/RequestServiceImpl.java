package com.moha.techtestnpaw.services.impl;

import com.moha.techtestnpaw.domain.Request;
import com.moha.techtestnpaw.repositories.RequestRepository;
import com.moha.techtestnpaw.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    private RequestRepository requestRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    @Cacheable("findRequests")
    public List<Request> findRequest(String accountCode) {
        return requestRepository.findByAccountCode(accountCode);
    }

    @Override
    public void save(Request request) {
        requestRepository.save(request);
    }

}
