package com.vijay.minihacksaw.model;

public class VulnerableComponent {

    private String productId;
    private String dependencyName;
    private String currentVersion;
    private String cveId;
    private String severity;
    private String fixVersion;
    private String status;

    public VulnerableComponent(String productId,
                               String dependencyName,
                               String currentVersion,
                               String cveId,
                               String severity,
                               String fixVersion,
                               String status) {

        this.productId = productId;
        this.dependencyName = dependencyName;
        this.currentVersion = currentVersion;
        this.cveId = cveId;
        this.severity = severity;
        this.fixVersion = fixVersion;
        this.status = status;
    }

    public String getProductId() { return productId; }
    public String getDependencyName() { return dependencyName; }
    public String getCurrentVersion() { return currentVersion; }
    public String getCveId() { return cveId; }
    public String getSeverity() { return severity; }
    public String getFixVersion() { return fixVersion; }
    public String getStatus() { return status; }
    public void setStatus(String status) {
        this.status = status;
    }
}