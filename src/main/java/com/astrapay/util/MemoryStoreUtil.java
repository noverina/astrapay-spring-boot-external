package com.astrapay.util;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemoryStoreUtil {
    private final Map<String, Object> store = new ConcurrentHashMap<>();

    public void save(String key, Object data) {
        store.put(key, data);
    }

    public Object get(String key) {
        return store.get(key);
    }

    public void delete(String key) {
        store.remove(key);
    }

    public Collection<Object> getAll() {
        return store.values();
    }
}
