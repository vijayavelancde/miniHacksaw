package com.vijay.minihacksaw.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vijay.minihacksaw.model.Product;
import com.vijay.minihacksaw.model.Vulnerability;
import com.vijay.minihacksaw.model.Build;
import com.vijay.minihacksaw.repository.VulnerabilityRepository;
import com.vijay.minihacksaw.repository.BuildRepository;
import com.vijay.minihacksaw.service.HardcodedSecretService;

@Service
public class ScanService {

    @Autowired
    private VulnerabilityRepository vulnerabilityRepository;
    
    @Autowired
    private MisconfigurationService misconfigurationService;

    @Autowired
    private BuildRepository buildRepository;
    
    @Autowired
    private HardcodedSecretService hardcodedSecretService;

    public void scanBuild(Product product, MultipartFile file)
            throws IOException {

        if (file == null || file.isEmpty()) {
            return;
        }

        String buildId = UUID.randomUUID().toString();
        String fileName = file.getOriginalFilename().toLowerCase();

        if (fileName.endsWith(".xml")
                || fileName.endsWith(".gradle")) {
            scanTextBased(product, file, buildId);
        }

        if (fileName.endsWith(".jar")) {
            scanJar(product, file, buildId);
        }

        // 🔥 After scan, calculate summary for this build
        int totalFindings =
                vulnerabilityRepository.countByBuildId(buildId);

        int criticalCount =
                vulnerabilityRepository.countByBuildIdAndSeverity(
                        buildId, "CRITICAL");

        String status = (criticalCount > 0) ? "BLOCKED" : "SAFE";

        Build build = new Build(
                buildId,
                product.getProductId(),
                LocalDateTime.now(),
                status,
                totalFindings,
                criticalCount
        );

        buildRepository.save(build);
    }
    
    private void scanForMisconfigurations(Product product, String content) {

        // 1️⃣ Debug Mode
        if (content.contains("debug=true")) {
            misconfigurationService.addMisconfiguration(
                    product,
                    "Debug Mode Enabled",
                    "Application is running with debug mode enabled.",
                    "HIGH");
        }

        // 2️⃣ Hardcoded Password
        if (content.contains("password=")) {
            misconfigurationService.addMisconfiguration(
                    product,
                    "Hardcoded Password",
                    "Sensitive credentials found in configuration file.",
                    "CRITICAL");
        }

        // 3️⃣ Default Credentials
        if (content.contains("admin:admin") || content.contains("root:root")) {
            misconfigurationService.addMisconfiguration(
                    product,
                    "Default Credentials Detected",
                    "Default credentials detected in configuration.",
                    "CRITICAL");
        }

        // 4️⃣ Insecure HTTP Usage
        if (content.contains("http://")) {
            misconfigurationService.addMisconfiguration(
                    product,
                    "Insecure HTTP Protocol",
                    "Insecure HTTP protocol detected. HTTPS is recommended.",
                    "MEDIUM");
        }

        // 5️⃣ Open CORS Policy
        if (content.contains("allowed-origins=*")
            || content.contains("access-control-allow-origin: *")) {
            misconfigurationService.addMisconfiguration(
                    product,
                    "Open CORS Policy",
                    "CORS policy allows all origins (*).",
                    "HIGH");
        }

        // 6️⃣ Exposed API Key
        if (content.contains("apikey=") 
            || content.contains("api_key=")) {
            misconfigurationService.addMisconfiguration(
                    product,
                    "Exposed API Key",
                    "Potential API key found in configuration.",
                    "CRITICAL");
        }

        // 7️⃣ Disabled SSL Verification
        if (content.contains("ssl.verify=false")
            || content.contains("verify_ssl=false")) {
            misconfigurationService.addMisconfiguration(
                    product,
                    "SSL Verification Disabled",
                    "SSL certificate verification is disabled.",
                    "HIGH");
        }

        // 8️⃣ Hardcoded IP Address
        if (content.matches(".*\\b(\\d{1,3}\\.){3}\\d{1,3}\\b.*")) {
            misconfigurationService.addMisconfiguration(
                    product,
                    "Hardcoded IP Address",
                    "Hardcoded IP address detected in configuration.",
                    "LOW");
        }
    }

    private void scanTextBased(Product product,
                               MultipartFile file,
                               String buildId)
            throws IOException {

        String content =
                new String(file.getBytes(),
                        StandardCharsets.UTF_8)
                        .toLowerCase();
        
        scanForMisconfigurations(product, content);
        
        scanForHardcodedSecrets(product, content);

        if (content.contains("log4j")
                && content.contains("2.14.0")) {

            addVulnerability(product, buildId,
                    "Log4j RCE",
                    "CRITICAL",
                    "Log4j 2.14.0 vulnerable to Log4Shell");
        }

        if (content.contains("spring-boot")
                && content.contains("2.0.0")) {

            addVulnerability(product, buildId,
                    "Outdated Spring Boot",
                    "HIGH",
                    "Spring Boot 2.0.0 is outdated");
        }

        if (content.contains("password=")
                || content.contains("apikey=")
                || content.contains("secret=")) {

            addVulnerability(product, buildId,
                    "Hardcoded Secret",
                    "MEDIUM",
                    "Possible credentials found in build file");
        }

        if (content.contains("allowall=true")
                || content.contains("disablesecurity=true")) {

            addVulnerability(product, buildId,
                    "Security Misconfiguration",
                    "HIGH",
                    "Unsafe security flag detected");
        }
    }

    private void scanJar(Product product,
                         MultipartFile file,
                         String buildId)
            throws IOException {

        ZipInputStream zip =
                new ZipInputStream(file.getInputStream());

        ZipEntry entry;

        while ((entry = zip.getNextEntry()) != null) {

            String name =
                    entry.getName().toLowerCase();

            if (name.contains("log4j")) {
                addVulnerability(product, buildId,
                        "Log4j Library Present",
                        "CRITICAL",
                        "Log4j detected inside JAR");
            }

            if (name.contains("commons-collections")) {
                addVulnerability(product, buildId,
                        "Commons Collections Found",
                        "HIGH",
                        "Potential gadget chain library");
            }

            if (name.contains("spring")) {
                addVulnerability(product, buildId,
                        "Spring Framework Present",
                        "MEDIUM",
                        "Spring framework inside JAR");
            }
        }

        zip.close();
    }

    private void addVulnerability(Product product,
                                  String buildId,
                                  String title,
                                  String severity,
                                  String description) {

        Vulnerability vulnerability =
                new Vulnerability(
                        UUID.randomUUID().toString(),
                        title,
                        severity,
                        description,
                        product.getProductId(),
                        buildId   // 🔥 link to build
                );

        vulnerabilityRepository.save(vulnerability);
    }
    
    private void scanForHardcodedSecrets(Product product, String content) {

        if(content.contains("api_key=") || content.contains("apikey=")) {

            hardcodedSecretService.addSecret(
                    product,
                    "API Key Exposed",
                    "Hardcoded API key found in configuration.",
                    "CRITICAL");
        }

        if(content.contains("access_token=")) {

            hardcodedSecretService.addSecret(
                    product,
                    "Access Token Exposed",
                    "Access token found in configuration.",
                    "CRITICAL");
        }

        if(content.contains("-----BEGIN PRIVATE KEY-----")) {

            hardcodedSecretService.addSecret(
                    product,
                    "Private Key Exposed",
                    "Private key detected in build file.",
                    "CRITICAL");
        }

        if(content.contains("AKIA")) {

            hardcodedSecretService.addSecret(
                    product,
                    "AWS Access Key",
                    "Possible AWS access key detected.",
                    "CRITICAL");
        }
    }
    
    private int extractNumber(String content, String tag) {
        try {
            int start = content.indexOf("<" + tag + ">");
            int end = content.indexOf("</" + tag + ">");

            if (start != -1 && end != -1) {
                String value = content.substring(
                        start + tag.length() + 2, end);
                return Integer.parseInt(value.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
}