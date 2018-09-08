package com.moha.test.services;

import com.moha.test.domain.request.Request;
import com.moha.test.exceptions.BalancerException;

public interface BalancerService {

    String balanceRequest(Request request) throws BalancerException;
}
