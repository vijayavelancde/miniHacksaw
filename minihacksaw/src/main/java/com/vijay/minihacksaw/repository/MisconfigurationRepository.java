package com.vijay.minihacksaw.repository;

import com.vijay.minihacksaw.model.Misconfiguration;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MisconfigurationRepository {

    private Map<String, List<Misconfiguration>> storage = new HashMap<>();

    public void save(Misconfiguration issue) {
        storage
                .computeIfAbsent(issue.getProductId(), k -> new ArrayList<>())
                .add(issue);
    }

    public List<Misconfiguration> findByProductId(String productId) {
        return storage.getOrDefault(productId, new ArrayList<>());
    }

    public void deleteByProductId(String productId) {
        storage.remove(productId);
    }
}