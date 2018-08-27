package com.moha.techtestnpaw.services.impl;


import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.moha.techtestnpaw.services.RequestCacheService;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class RequestCacheServiceImpl implements RequestCacheService {

    private final HazelcastInstance client;

    private RequestCacheServiceImpl() {
        this.client = HazelcastClient.newHazelcastClient();
    }

    @PreDestroy
    private void clean() {
        client.shutdown();
    }

    @Override
    public <K, V> Optional<V> get(CacheType type, K id) {
        IMap<K, V> map = client.getMap(type.name());
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public <K, V> void put(CacheType type, K id, V o, long time, TimeUnit timeUnit) {
        client.getMap(type.name()).set(id, o, time, timeUnit);
    }
}
