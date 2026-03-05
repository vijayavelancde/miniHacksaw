package com.vijay.minihacksaw.controller;

import com.vijay.minihacksaw.model.VulnerableComponent;
import com.vijay.minihacksaw.service.ProductService;
import com.vijay.minihacksaw.service.VulnerableComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class VulnerableComponentController {

    @Autowired
    private ProductService productService;

    @Autowired
    private VulnerableComponentService componentService;

    @GetMapping("/components")
    public String components(
            @RequestParam(required = false) String productId,
            @RequestParam(required = false) String severity,
            @RequestParam(required = false) String status,
            Model model) {

        var products = productService.getAllProducts();
        model.addAttribute("products", products);

        // 🔥 AUTO SELECT FIRST PRODUCT IF NONE SELECTED
        if ((productId == null || productId.isBlank())
                && products != null && !products.isEmpty()) {

            productId = products.get(0).getProductId();
        }

        List<VulnerableComponent> components = new ArrayList<>();

        if (productId != null && !productId.isBlank()) {

            componentService.generateScanResults(productId);

            components = componentService.getByProduct(productId);

            // Apply severity filter
            if (severity != null && !severity.isBlank()) {
                components = components.stream()
                        .filter(c -> c.getSeverity().equalsIgnoreCase(severity))
                        .toList();
            }

            // Apply status filter
            if (status != null && !status.isBlank()) {
                components = components.stream()
                        .filter(c -> c.getStatus().equalsIgnoreCase(status))
                        .toList();
            }
        }

        model.addAttribute("components", components);
        model.addAttribute("selectedProduct", productId);
        model.addAttribute("selectedSeverity", severity);
        model.addAttribute("selectedStatus", status);

        return "vulnerable-components";
    }
    
    @PostMapping("/components/patch")
    public String patch(@RequestParam String productId,
                        @RequestParam String cveId) {

        componentService.markAsPatched(productId, cveId);

        return "redirect:/components?productId=" + productId;
    }
    
}