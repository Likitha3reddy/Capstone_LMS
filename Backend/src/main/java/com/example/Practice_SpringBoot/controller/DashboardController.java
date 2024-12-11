package com.example.Practice_SpringBoot.controller;

import com.example.Practice_SpringBoot.entity.Dashboard;
import com.example.Practice_SpringBoot.service.DashboardService;
import com.example.Practice_SpringBoot.service.ModuleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private DashboardService dashboardService;

    @PostMapping("")
    public ResponseEntity<Dashboard> createDashboard(@RequestBody Dashboard dashboard) {
        Dashboard createdDashboard = dashboardService.createDashboard(dashboard);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDashboard);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Dashboard> getDashboard(@PathVariable Long userId) {
        Dashboard dashboard = dashboardService.getDashboardByUserId(userId);
        return ResponseEntity.ok(dashboard);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Dashboard> updateDashboard(@PathVariable Long userId, @RequestBody Map<String, Object> updatedData) throws JsonProcessingException {
        String updatedDataJson = new ObjectMapper().writeValueAsString(updatedData); // Convert Map to JSON string
        Dashboard updatedDashboard = dashboardService.updateDashboard(userId, updatedDataJson);
        return ResponseEntity.ok(updatedDashboard);
    }
}
