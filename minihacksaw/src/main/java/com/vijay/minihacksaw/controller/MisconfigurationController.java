package com.vijay.minihacksaw.controller;

import com.vijay.minihacksaw.model.Misconfiguration;
import com.vijay.minihacksaw.model.Product;
import com.vijay.minihacksaw.service.MisconfigurationService;
import com.vijay.minihacksaw.service.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MisconfigurationController {

    @Autowired
    private ProductService productService;

    @Autowired
    private MisconfigurationService misconfigurationService;

    @GetMapping("/misconfigurations")
    public String showMisconfigurations(
            @RequestParam(required = false) String productId,
            Model model) {

        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        // Auto-select first product if none selected
        if ((productId == null || productId.isBlank()) && !products.isEmpty()) {
            productId = products.get(0).getProductId();
        }

        if (productId != null) {

            Product selectedProduct =
                    productService.getProductById(productId);

            model.addAttribute("selectedProduct", selectedProduct);

            // Get issues
            List<Misconfiguration> issues =
                    misconfigurationService.getByProduct(productId);

            model.addAttribute("misconfigurations", issues);

            // Calculate counts
            long criticalCount = issues.stream()
                    .filter(i -> "CRITICAL".equalsIgnoreCase(i.getSeverity()))
                    .count();

            long highCount = issues.stream()
                    .filter(i -> "HIGH".equalsIgnoreCase(i.getSeverity()))
                    .count();

            long mediumCount = issues.stream()
                    .filter(i -> "MEDIUM".equalsIgnoreCase(i.getSeverity()))
                    .count();

            long lowCount = issues.stream()
                    .filter(i -> "LOW".equalsIgnoreCase(i.getSeverity()))
                    .count();

            model.addAttribute("criticalCount", criticalCount);
            model.addAttribute("highCount", highCount);
            model.addAttribute("mediumCount", mediumCount);
            model.addAttribute("lowCount", lowCount);
        }

        return "misconfigurations";
    }
}