package com.moha.techtestnpaw.utils.dao;

public final class RequestUtils {

    private RequestUtils() {
        throw new AssertionError("Ensuring noninstaintability");
    }

    public static final String TABLE = "requests";

    public static final String DB_FIELD_ACCOUNT_CODE = "account_code";
    public static final String DB_FIELD_TARGET_DEVICE = "target_device";
    public static final String DB_FIELD_PLUGIN_VERSION = "plugin_version";
    public static final String DB_FIELD_PING_TIME = "ping_time";
    public static final String DB_FIELD_HOSTS = "hosts";
}
