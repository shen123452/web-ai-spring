package com.hhh.utils;

public final class UserContext {
    private static final ThreadLocal<String> CURRENT_USERNAME = new ThreadLocal<>();

    private UserContext() {
    }

    public static void setUsername(String username) {
        CURRENT_USERNAME.set(username);
    }

    public static String getUsername() {
        return CURRENT_USERNAME.get();
    }

    public static String getUsernameOrDefault(String defaultValue) {
        String username = getUsername();
        return username == null || username.isBlank() ? defaultValue : username;
    }

    public static void clear() {
        CURRENT_USERNAME.remove();
    }
}
