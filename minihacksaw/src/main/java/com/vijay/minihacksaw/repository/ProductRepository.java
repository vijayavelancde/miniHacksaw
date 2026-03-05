package com.vijay.minihacksaw.repository;

import java.util.List;
import com.vijay.minihacksaw.model.Product;

public interface ProductRepository {

    void save(Product product);

    List<Product> findAll();

    Product findById(String id);

    void delete(String id);
}