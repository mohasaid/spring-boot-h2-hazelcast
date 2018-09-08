package com.moha.test.services.impl;

import com.moha.test.domain.request.Request;
import com.moha.test.domain.request.RequestBuilder;
import com.moha.test.repository.RequestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RequestServiceImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RequestRepository requestRepository;

    @Test
    public void givenRequest_whenFindByName_thenReturnRequest() {
        // given
        Request request = RequestBuilder.aRequest()
                .withAccountCode("acc")
                .withTargetDevice("td")
                .withPluginVersion("3.3.1")
                .withPingTime(10)
                .build();

        entityManager.persist(request);
        entityManager.flush();

        // when
        Optional<Request> found = requestRepository.findById(request.getId());

        // then
        assertTrue(found.isPresent());
        assertNotNull(found.get());
        assertEquals(found.get().getAccountCode(), request.getAccountCode());
    }

    @Test
    public void givenRequest_whenSave_thenRequestReturnOk() {
        // given
        Request request = RequestBuilder.aRequest()
                .withAccountCode("acc")
                .withTargetDevice("td")
                .withPluginVersion("3.3.1")
                .withPingTime(10)
                .build();

        // when
        requestRepository.save(request);

        // then
        Optional<Request> found = requestRepository.findById(request.getId());
        assertTrue(found.isPresent());
        assertNotNull(found.get());
        assertEquals(found.get().getAccountCode(), request.getAccountCode());
    }

    @Test
    public void givenRequest_whenDelete_thenRequestReturnNoOk() {
        // given
        Request request = RequestBuilder.aRequest()
                .withAccountCode("acc")
                .withTargetDevice("td")
                .withPluginVersion("3.3.1")
                .withPingTime(10)
                .build();

        entityManager.persist(request);
        entityManager.flush();

        // when
        requestRepository.deleteById(request.getId());

        // then
        Optional<Request> found = requestRepository.findById(request.getId());
        assertFalse(found.isPresent());
    }
}