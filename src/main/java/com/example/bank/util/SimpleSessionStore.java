package com.example.bank.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class SimpleSessionStore {
    private static final Map<String,String> tokenToUserId = new ConcurrentHashMap<>();

    public static void put(String token, String userId) {
        tokenToUserId.put(token, userId);
    }

    public static String get(String token) {
        return tokenToUserId.get(token);
    }

    public static void remove(String token) {
        tokenToUserId.remove(token);
    }
}
