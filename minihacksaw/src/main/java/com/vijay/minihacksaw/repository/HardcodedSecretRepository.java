package com.vijay.minihacksaw.repository;

import com.vijay.minihacksaw.model.HardcodedSecret;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HardcodedSecretRepository {

    private Map<String, List<HardcodedSecret>> storage = new HashMap<>();

    public void save(HardcodedSecret secret) {

        storage
                .computeIfAbsent(secret.getProductId(),
                        k -> new ArrayList<>())
                .add(secret);
    }

    public List<HardcodedSecret> findByProductId(String productId) {

        return storage.getOrDefault(productId, new ArrayList<>());
    }

    public void deleteByProductId(String productId) {
        storage.remove(productId);
    }
}