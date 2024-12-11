package com.example.Practice_SpringBoot.controller;

import com.example.Practice_SpringBoot.entity.ProgressReport;
import com.example.Practice_SpringBoot.payload.ProgressRequest;
import com.example.Practice_SpringBoot.service.ProgressReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/progress-reports")
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend
public class ProgressReportController {

    @Autowired
    private ProgressReportService progressReportService;



    // Get progress reports by course
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ProgressReport>> getReportsByCourseId(@PathVariable Long courseId) {
        List<ProgressReport> reports = progressReportService.getProgressReportsByCourseId(courseId);
        return ResponseEntity.ok(reports);
    }

    // Get progress reports by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProgressReport>> getReportsByUserId(@PathVariable Long userId) {
        List<ProgressReport> reports = progressReportService.getProgressReportsByUserId(userId);
        return ResponseEntity.ok(reports);
    }

    // Get average progress for a course
    @GetMapping("/course/{courseId}/average")
    public ResponseEntity<Double> getAverageProgressByCourseId(@PathVariable Long courseId) {
        Double averageProgress = progressReportService.getAverageProgressByCourseId(courseId);
        return ResponseEntity.ok(averageProgress);
    }

    @PostMapping("save")
    public ResponseEntity<Map<String, String>> saveProgress(@RequestBody ProgressRequest progressRequest) {
        progressReportService.saveProgress(
                progressRequest.getUserId(),
                progressRequest.getCourseId(),
                progressRequest.getModuleId(),
                progressRequest.getProgressPercentage()
        );

        Map<String, String> response = new HashMap<>();
        response.put("message", "Progress saved successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/average/{userId}")
    public ResponseEntity<Double> getAverageProgress(@PathVariable Long userId) {
        Double averageProgress = progressReportService.getAverageProgress(userId);
        if (averageProgress == null) {
            return ResponseEntity.notFound().build(); // No progress data for the user
        }
        return ResponseEntity.ok(averageProgress);
    }



}

