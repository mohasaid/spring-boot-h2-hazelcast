package com.moha.techtestnpaw.utils;

import java.util.UUID;

public final class ViewCodeGenerator {

    private ViewCodeGenerator() {
        throw new AssertionError("Ensuring noninstaintability");
    }

    public static String getViewCode() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
