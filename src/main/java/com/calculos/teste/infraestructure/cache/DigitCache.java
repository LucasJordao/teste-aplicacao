package com.calculos.teste.infraestructure.cache;

import com.calculos.teste.core.domain.model.DigitResult;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class DigitCache {

    private final Map<String, DigitResult> cache =
            Collections.synchronizedMap(new LinkedHashMap<>(10, 0.75f, true) {

                @Override
                protected boolean removeEldestEntry(Map.Entry<String, DigitResult> eldest) {
                    return size() > 10;
                }
            });

    public boolean exists(String key) {
        return cache.containsKey(key);
    }

    public DigitResult get(String key) {
        return cache.get(key);
    }

    public void put(String key, DigitResult value) {
        cache.put(key, value);
    }
}