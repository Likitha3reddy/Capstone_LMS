package com.example.Practice_SpringBoot.controller;


import com.example.Practice_SpringBoot.entity.Assignment;
import com.example.Practice_SpringBoot.entity.Submission;
import com.example.Practice_SpringBoot.service.AssignmentService;
import com.example.Practice_SpringBoot.service.EnrollmentService;
import com.example.Practice_SpringBoot.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private SubmissionService submissionService;

    @GetMapping("/{userId}/assignments")
    public ResponseEntity<List<Assignment>> getAssignmentsForStudent(@PathVariable Long userId) {
        List<Long> courseIds = enrollmentService.getCourseIdsByUserId(userId);
        List<Assignment> assignments = assignmentService.getAssignmentsByCourseIds(courseIds);
        return ResponseEntity.ok(assignments);
    }

    @PostMapping("/submit")
    public ResponseEntity<Submission> submitAssignment(@RequestBody Submission submission) {
        Submission savedSubmission = submissionService.saveSubmission(submission);
        return ResponseEntity.ok(savedSubmission);
    }
}

