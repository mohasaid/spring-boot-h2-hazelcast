package com.moha.test.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {

    private static final String HAZELCAST_INSTANCE_NAME = "hazelcast-instance";
    public static final String HAZELCAST_REQUEST_CONFIG = "findRequests";

    @Bean
    public Config hazelCastConfig() {
        return new Config()
                .setInstanceName(HAZELCAST_INSTANCE_NAME)
                .addMapConfig(
                        new MapConfig()
                                .setName(HAZELCAST_REQUEST_CONFIG)
                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(20));
    }
}