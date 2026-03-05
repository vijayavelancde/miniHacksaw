package com.vijay.minihacksaw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

import com.vijay.minihacksaw.model.Product;
import com.vijay.minihacksaw.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    // LIST PRODUCTS
    @GetMapping
    public String listProducts(Model model, HttpServletRequest request) {
        model.addAttribute("products", service.getAllProducts());
        model.addAttribute("currentPath", request.getRequestURI());
        return "product-list";
    }

    // SHOW ADD FORM
    @GetMapping("/add")
    public String showAddForm(Model model, HttpServletRequest request) {
        model.addAttribute("product", new Product());
        model.addAttribute("currentPath", request.getRequestURI());
        return "add-product";
    }

    // SHOW EDIT FORM
    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable String id,
                              Model model,
                              HttpServletRequest request) {

        Product existingProduct = service.getProductById(id);

        model.addAttribute("product", existingProduct);
        model.addAttribute("currentPath", request.getRequestURI());

        return "add-product"; // reuse same form
    }

    // SAVE (ADD + UPDATE)
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product,
                              @RequestParam("buildFile") MultipartFile file,
                              Model model,
                              HttpServletRequest request) {

        // 🔥 Mandatory for both Add & Edit
        if (file == null || file.isEmpty()) {

            model.addAttribute("product", product);
            model.addAttribute("currentPath", request.getRequestURI());
            model.addAttribute("errorMessage",
                    "Upload Build File is mandatory.");

            return "add-product";
        }

        service.saveProduct(product, file);

        return "redirect:/products";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        service.deleteProduct(id);
        return "redirect:/products";
    }
}