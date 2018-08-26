package com.moha.techtestnpaw.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moha.techtestnpaw.dao.RequestDAO;
import com.moha.techtestnpaw.domain.Host;
import com.moha.techtestnpaw.domain.request.Request;
import com.moha.techtestnpaw.domain.request.RequestBuilder;
import com.moha.techtestnpaw.utils.dao.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static com.moha.techtestnpaw.config.HazelcastConfig.HAZELCAST_REQUEST_CONFIG;

@Service
@CacheConfig(cacheNames = HAZELCAST_REQUEST_CONFIG)
public class RequestDAOImpl implements RequestDAO {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public RequestDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Cacheable
    public List<Request> findRequest(final String accountCode) {
        final StringBuilder selectQuery = new StringBuilder();
        selectQuery.append("SELECT * ")
                .append(" FROM ").append(RequestUtils.TABLE)
                .append(" WHERE ").append(RequestUtils.DB_FIELD_ACCOUNT_CODE).append(" = ?");
        return jdbcTemplate.query(selectQuery.toString(),
                new Object[]{accountCode},
                new RequestMapper());
    }

    @Override
    public void save(final Request request) {
    }

    @Override
    public void delete(final String accountCode, final String targetDevice, final String pluginVersion) {
    }

    class RequestMapper implements RowMapper<Request> {

        @Override
        public Request mapRow(ResultSet resultSet, int i) throws SQLException {
            String hosts = resultSet.getString(RequestUtils.DB_FIELD_HOSTS);
            List<Host> hostList = null;
            try {
                hostList = Arrays.asList(objectMapper.readValue(hosts, Host[].class));
            } catch (IOException e) {
                logger.error("There was an error while converting hosts");
            }

            return RequestBuilder.aRequest()
                    .withAccountCode(resultSet.getString(RequestUtils.DB_FIELD_ACCOUNT_CODE))
                    .withTargetDevice(resultSet.getString(RequestUtils.DB_FIELD_TARGET_DEVICE))
                    .withPluginVersion(resultSet.getString(RequestUtils.DB_FIELD_PLUGIN_VERSION))
                    .withPingTime(resultSet.getInt(RequestUtils.DB_FIELD_PING_TIME))
                    .withHosts(hostList)
                    .build();
        }
    }

}
