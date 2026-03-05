package com.vijay.minihacksaw.controller;

import com.vijay.minihacksaw.model.Build;
import com.vijay.minihacksaw.repository.BuildRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/builds")
public class BuildController {

    @Autowired
    private BuildRepository buildRepository;

    @GetMapping
    public String viewAllBuilds(Model model) {

        // Get all builds
        List<Build> builds = buildRepository.findAll();

        model.addAttribute("builds", builds);

        // Summary metrics
        long blockedCount = builds.stream()
                .filter(b -> "BLOCKED".equalsIgnoreCase(b.getStatus()))
                .count();

        long safeCount = builds.stream()
                .filter(b -> "SAFE".equalsIgnoreCase(b.getStatus()))
                .count();

        model.addAttribute("blockedCount", blockedCount);
        model.addAttribute("safeCount", safeCount);

        return "build-history";
    }
}