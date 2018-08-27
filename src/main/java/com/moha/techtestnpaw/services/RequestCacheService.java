package com.moha.techtestnpaw.services;


import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface RequestCacheService {

    enum CacheType {REQUESTS}

    <K, V> Optional<V> get(CacheType type, K id);

    <K, V> void put(CacheType type, K id, V o, long time, TimeUnit timeUnit);

}
