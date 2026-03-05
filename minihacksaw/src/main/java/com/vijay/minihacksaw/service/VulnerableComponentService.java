package com.vijay.minihacksaw.service;

import com.vijay.minihacksaw.model.VulnerableComponent;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VulnerableComponentService {

    // 🔥 In-memory storage
    private final Map<String, List<VulnerableComponent>> storage = new HashMap<>();

    // Get components for product
    public List<VulnerableComponent> getByProduct(String productId) {
        return storage.getOrDefault(productId, new ArrayList<>());
    }

    // Simulate scan result creation
    public void generateScanResults(String productId) {

        if (storage.containsKey(productId)) {
            return; // prevent duplicate generation
        }

        List<VulnerableComponent> components = new ArrayList<>();

        components.add(new VulnerableComponent(
                productId,
                "log4j",
                "2.14.0",
                "CVE-2021-44228",
                "CRITICAL",
                "2.17.1",
                "OPEN"
        ));

        components.add(new VulnerableComponent(
                productId,
                "spring-core",
                "5.2.1",
                "CVE-2022-22965",
                "HIGH",
                "5.3.18",
                "OPEN"
        ));

        storage.put(productId, components);
    }

    // Patch workflow
    public void markAsPatched(String productId, String cveId) {

        List<VulnerableComponent> components = storage.get(productId);
        if (components == null) return;

        for (VulnerableComponent c : components) {
            if (c.getCveId().equals(cveId)) {
                c.setStatus("RESOLVED");
            }
        }
    }
}