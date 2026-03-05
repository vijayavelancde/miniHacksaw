package com.vijay.minihacksaw.service;

import com.vijay.minihacksaw.model.HardcodedSecret;
import com.vijay.minihacksaw.model.Product;
import com.vijay.minihacksaw.repository.HardcodedSecretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HardcodedSecretService {

    @Autowired
    private HardcodedSecretRepository repository;

    public void addSecret(Product product,
                          String title,
                          String description,
                          String severity) {

        HardcodedSecret secret = new HardcodedSecret();

        secret.setId(UUID.randomUUID().toString());
        secret.setTitle(title);
        secret.setDescription(description);
        secret.setSeverity(severity);
        secret.setProductId(product.getProductId());

        repository.save(secret);
    }

    public List<HardcodedSecret> getByProduct(String productId) {
        return repository.findByProductId(productId);
    }

    public void clearByProduct(String productId) {
        repository.deleteByProductId(productId);
    }
}