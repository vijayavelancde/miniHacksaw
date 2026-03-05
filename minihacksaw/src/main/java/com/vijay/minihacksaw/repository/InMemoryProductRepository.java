package com.vijay.minihacksaw.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.vijay.minihacksaw.model.Product;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> products = new ArrayList<>();

    @Override
    public void save(Product product) {

        if (product == null || product.getProductId() == null) {
            return; // Safety guard
        }

        // Try to update existing product
        for (int i = 0; i < products.size(); i++) {

            if (products.get(i).getProductId()
                    .equals(product.getProductId())) {

                products.set(i, product); // Replace existing
                return;
            }
        }

        // If not found, add as new
        products.add(product);
    }

    @Override
    public List<Product> findAll() {
        return products.stream()
                .filter(p -> !p.isDeleted())
                .toList();
    }

    @Override
    public Product findById(String id) {
        return products.stream()
                .filter(p -> p.getProductId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(String id) {
        Product product = findById(id);
        if (product != null) {
            product.setDeleted(true);
        }
    }
}