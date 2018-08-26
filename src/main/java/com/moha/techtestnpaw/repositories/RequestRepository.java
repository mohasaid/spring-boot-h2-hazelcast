package com.moha.techtestnpaw.repositories;

import com.moha.techtestnpaw.domain.Request;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequestRepository extends CrudRepository<Request, String> {

    List<Request> findByAccountCode(String accountCode);
}
