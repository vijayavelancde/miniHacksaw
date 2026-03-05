package com.vijay.minihacksaw.model;

import java.time.LocalDateTime;

public class Build {

    private String buildId;
    private String productId;
    private LocalDateTime scanTime;
    private String status; // COMPLETED / FAILED
    private int totalFindings;
    private int criticalCount;

    public Build(String buildId, String productId,
                 LocalDateTime scanTime, String status,
                 int totalFindings, int criticalCount) {
        this.buildId = buildId;
        this.productId = productId;
        this.scanTime = scanTime;
        this.status = status;
        this.totalFindings = totalFindings;
        this.criticalCount = criticalCount;
    }

    public String getBuildId() { return buildId; }
    public String getProductId() { return productId; }
    public LocalDateTime getScanTime() { return scanTime; }
    public String getStatus() { return status; }
    public int getTotalFindings() { return totalFindings; }
    public int getCriticalCount() { return criticalCount; }
}