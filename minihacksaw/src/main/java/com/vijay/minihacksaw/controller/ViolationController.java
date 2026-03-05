package com.vijay.minihacksaw.controller;

import com.vijay.minihacksaw.service.ProductService;
import com.vijay.minihacksaw.service.ViolationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViolationController {

    @Autowired
    private ViolationService violationService;

    @Autowired
    private ProductService productService;   // 🔥 ADD THIS

    @GetMapping("/violations")
    public String viewViolations(
            @RequestParam(required = false) String productId,
            Model model) {

        var products = productService.getAllProducts();
        model.addAttribute("products", products);

        // 🔥 AUTO SELECT FIRST PRODUCT IF EXISTS
        if ((productId == null || productId.isBlank())
                && products != null && !products.isEmpty()) {

            productId = products.get(0).getProductId();
        }

        model.addAttribute("selectedProduct", productId);

        if (productId != null && !productId.isBlank()) {
            model.addAttribute("violations",
                    violationService.getViolationsByProduct(productId));
        } else {
            model.addAttribute("violations", List.of());
        }

        return "violations";
    }
}