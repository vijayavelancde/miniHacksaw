package com.vijay.minihacksaw.model;

public class Violation {

    private String productId;   // 🔥 MATCH Product model
    private String title;
    private String severity;
    private String description;
    private String status;

    public Violation(String productId,
                     String title,
                     String severity,
                     String description,
                     String status) {

        this.productId = productId;
        this.title = title;
        this.severity = severity;
        this.description = description;
        this.status = status;
    }

    public String getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public String getSeverity() {
        return severity;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}