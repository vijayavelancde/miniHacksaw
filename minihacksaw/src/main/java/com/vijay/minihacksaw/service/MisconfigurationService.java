package com.vijay.minihacksaw.service;

import com.vijay.minihacksaw.model.Misconfiguration;
import com.vijay.minihacksaw.model.Product;
import com.vijay.minihacksaw.repository.MisconfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MisconfigurationService {

    @Autowired
    private MisconfigurationRepository repository;

    public void addMisconfiguration(Product product,
                                    String title,
                                    String description,
                                    String severity) {

        Misconfiguration issue = new Misconfiguration();

        issue.setId(UUID.randomUUID().toString());
        issue.setTitle(title);
        issue.setDescription(description);
        issue.setSeverity(severity);
        issue.setProductId(product.getProductId());

        repository.save(issue);
    }

    public List<Misconfiguration> getByProduct(String productId) {
        return repository.findByProductId(productId);
    }

    public void clearByProduct(String productId) {
        repository.deleteByProductId(productId);
    }
}