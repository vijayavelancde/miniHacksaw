package com.vijay.minihacksaw.controller;

import com.vijay.minihacksaw.model.HardcodedSecret;
import com.vijay.minihacksaw.model.Product;
import com.vijay.minihacksaw.service.HardcodedSecretService;
import com.vijay.minihacksaw.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HardcodedSecretController {

    @Autowired
    private ProductService productService;

    @Autowired
    private HardcodedSecretService hardcodedSecretService;

    @GetMapping("/secrets")
    public String showSecrets(
            @RequestParam(required = false) String productId,
            Model model) {

        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        // If no productId provided, select first product automatically
        if ((productId == null || productId.isBlank()) && !products.isEmpty()) {
            productId = products.get(0).getProductId();
        }

        if (productId != null) {

            Product selectedProduct = productService.getProductById(productId);
            model.addAttribute("selectedProduct", selectedProduct);

            List<HardcodedSecret> secrets =
                    hardcodedSecretService.getByProduct(productId);

            model.addAttribute("secrets", secrets);

            long totalSecrets = secrets.size();

            long criticalCount = secrets.stream()
                    .filter(s -> "CRITICAL".equalsIgnoreCase(s.getSeverity()))
                    .count();

            model.addAttribute("totalSecrets", totalSecrets);
            model.addAttribute("criticalCount", criticalCount);
        }

        return "hardcoded-secrets";
    }
}