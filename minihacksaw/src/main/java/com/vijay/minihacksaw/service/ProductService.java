package com.vijay.minihacksaw.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.vijay.minihacksaw.repository.VulnerabilityRepository;

import com.vijay.minihacksaw.model.Product;
import com.vijay.minihacksaw.repository.ProductRepository;
import com.vijay.minihacksaw.service.MisconfigurationService;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private VulnerabilityRepository vulnerabilityRepository;

    @Autowired
    private ScanService scanService;
    
    

    public String generateProductId() {
        return "HSW-" + UUID.randomUUID()
                .toString()
                .substring(0, 5)
                .toUpperCase();
    }

    // 🔥 Unified Save Method (ADD + EDIT)
    public void saveProduct(Product product, MultipartFile file) {

        boolean isNew = (product.getProductId() == null
                || product.getProductId().isBlank());

        if (isNew) {
            product.setProductId(generateProductId());
            product.setScanStatus("PENDING");
        } else {
            Product existing = repository.findById(product.getProductId());
            if (existing != null) {
                product.setScanStatus(existing.getScanStatus());
            }
        }

        // 🔥 ONLY SAVE ONCE IF NO FILE
        if (file == null || file.isEmpty()) {
            repository.save(product);
            return;
        }
        
        if (file != null && !file.isEmpty()) {
            product.setBuildFileName(file.getOriginalFilename());
        }

        // If file exists → execute scan (which handles saves)
        executeScan(product, file);
    }

    private void executeScan(Product product, MultipartFile file) {

        try {
            product.setScanStatus("SCANNING");
            repository.save(product);

            scanService.scanBuild(product, file);

            product.setScanStatus("COMPLETED");

        } catch (Exception e) {
            product.setScanStatus("FAILED");
            e.printStackTrace();
        }

        repository.save(product);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public void deleteProduct(String id) {
    	
    	vulnerabilityRepository.deleteByProductId(id);
    	
        repository.delete(id);
    }

    public Product getProductById(String id) {
        return repository.findById(id);
    }
    
   
}