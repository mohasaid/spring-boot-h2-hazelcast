package com.moha.techtestnpaw.dao;

import com.moha.techtestnpaw.domain.request.Request;

import java.util.List;

public interface RequestDAO {

    List<Request> findRequest(final String accountCode);

    void save(final Request request);

    void delete(final String accountCode, final String targetDevice, final String pluginVersion);

}
