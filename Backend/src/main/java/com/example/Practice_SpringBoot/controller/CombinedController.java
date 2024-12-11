package com.example.Practice_SpringBoot.controller;

import com.example.Practice_SpringBoot.service.CombinedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/combined")
public class CombinedController {
    @Autowired
    private CombinedService combinedService;

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<Map<String, Object>>> getCoursesWithEnrollmentCounts(@PathVariable Long instructorId) {
        List<Map<String, Object>> result = combinedService.getCoursesWithEnrollmentCounts(instructorId);
        return ResponseEntity.ok(result);
    }
}
