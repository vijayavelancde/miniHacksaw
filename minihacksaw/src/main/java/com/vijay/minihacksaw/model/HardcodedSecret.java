package com.vijay.minihacksaw.model;

public class HardcodedSecret {

    private String id;
    private String title;
    private String description;
    private String severity;
    private String productId;

    public HardcodedSecret() {}

    public HardcodedSecret(String id,
                           String title,
                           String description,
                           String severity,
                           String productId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}