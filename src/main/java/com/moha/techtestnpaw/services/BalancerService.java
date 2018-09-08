package com.moha.techtestnpaw.services;

import com.moha.techtestnpaw.domain.request.Request;
import com.moha.techtestnpaw.exceptions.BalancerException;

public interface BalancerService {

    String balanceRequest(Request request) throws BalancerException;
}
