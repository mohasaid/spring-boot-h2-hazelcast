package com.moha.techtestnpaw.repository;

import com.moha.techtestnpaw.domain.request.Request;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequestRepository extends CrudRepository<Request, String> {

    List<Request> findByAccountCode(final String accountCode);

    Request save(final Request request);

    void deleteByAccountCodeAndTargetDeviceAndPluginVersion(final String accountCode,
                                                            final String targetDevice,
                                                            final String pluginVersion);
}
