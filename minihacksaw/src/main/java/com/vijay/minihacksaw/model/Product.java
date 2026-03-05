package com.vijay.minihacksaw.model;

public class Product {

    private String productId;
    private String name;
    private String brand;
    private String productType;
    private String description;
    private String scanStatus;
    
    private String buildFormat;
    private String binaryName;
    private String languages;
    private String repositoryUrl;
    private String buildFileName;

    private boolean wafEnabled;

    private boolean sastScan;
    private boolean dependencyScan;
    private boolean secretScan;
    private boolean configScan;

    private boolean deleted = false;

    public Product() {
    }

    // Getters & Setters

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBuildFormat() {
        return buildFormat;
    }

    public void setBuildFormat(String buildFormat) {
        this.buildFormat = buildFormat;
    }

    public String getBinaryName() {
        return binaryName;
    }

    public void setBinaryName(String binaryName) {
        this.binaryName = binaryName;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public boolean isWafEnabled() {
        return wafEnabled;
    }

    public void setWafEnabled(boolean wafEnabled) {
        this.wafEnabled = wafEnabled;
    }

    public boolean isSastScan() {
        return sastScan;
    }

    public void setSastScan(boolean sastScan) {
        this.sastScan = sastScan;
    }

    public boolean isDependencyScan() {
        return dependencyScan;
    }

    public void setDependencyScan(boolean dependencyScan) {
        this.dependencyScan = dependencyScan;
    }

    public boolean isSecretScan() {
        return secretScan;
    }

    public void setSecretScan(boolean secretScan) {
        this.secretScan = secretScan;
    }

    public boolean isConfigScan() {
        return configScan;
    }

    public void setConfigScan(boolean configScan) {
        this.configScan = configScan;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    public String getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(String scanStatus) {
        this.scanStatus = scanStatus;
    }
    
    public String getBuildFileName() {
        return buildFileName;
    }

    public void setBuildFileName(String buildFileName) {
        this.buildFileName = buildFileName;
    }
    
}