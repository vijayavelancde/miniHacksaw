package com.vijay.minihacksaw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

import com.vijay.minihacksaw.service.ProductService;
import com.vijay.minihacksaw.repository.VulnerabilityRepository;

@Controller
public class DashboardController {

    @Autowired
    private VulnerabilityRepository vulnerabilityRepository;

    @Autowired
    private ProductService productService;  // 🔥 THIS WAS MISSING

    @GetMapping("/")
    public String dashboard(
            @RequestParam(required = false) String productId,
            Model model) {

        var products = productService.getAllProducts();
        model.addAttribute("products", products);

        if ((productId == null || productId.isBlank())
                && products != null && !products.isEmpty()) {

            productId = products.get(0).getProductId();
        }

        model.addAttribute("selectedProductId", productId);

        if (productId != null && !productId.isBlank()) {
            model.addAttribute("totalVulnerabilities", 5);
            model.addAttribute("criticalCount", 1);
        }

        return "dashboard";
    }
}