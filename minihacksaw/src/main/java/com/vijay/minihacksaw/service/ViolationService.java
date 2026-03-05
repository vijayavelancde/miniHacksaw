package com.vijay.minihacksaw.service;

import com.vijay.minihacksaw.model.Violation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ViolationService {

    public List<Violation> getViolationsByProduct(String productId) {

        if (productId == null || productId.isBlank()) {
            return List.of();
        }

        List<Violation> violations = new ArrayList<>();

        // 🔥 Dynamically generate violations for ANY selected product
        violations.add(new Violation(
                productId,
                "SQL Injection Risk",
                "HIGH",
                "Unsanitized input detected in LoginController.java",
                "OPEN"
        ));

        violations.add(new Violation(
                productId,
                "Hardcoded Credentials",
                "CRITICAL",
                "Plaintext password found in DatabaseConfig.java",
                "OPEN"
        ));

        violations.add(new Violation(
                productId,
                "Cross Site Scripting",
                "MEDIUM",
                "User input rendered without encoding in profile.html",
                "IN_PROGRESS"
        ));

        return violations;
    }
}