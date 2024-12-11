package com.example.Practice_SpringBoot.controller;


import com.example.Practice_SpringBoot.entity.Assignment;
import com.example.Practice_SpringBoot.repository.AssignmentRepository;
import com.example.Practice_SpringBoot.service.AssignmentService;
import com.example.Practice_SpringBoot.service.EnrollmentService;
import com.example.Practice_SpringBoot.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private AssignmentRepository assignmentRepository;

    /**
     * Endpoint to create a new assignment by an instructor.
     */
    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment) {
        Assignment createdAssignment = assignmentService.createAssignment(assignment);
        return ResponseEntity.ok(createdAssignment);
    }

    /**
     * Endpoint to get all assignments for a specific course.
     */
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Assignment>> getAssignmentsByCourse(@PathVariable Long courseId) {
        List<Assignment> assignments = assignmentService.getAssignmentsByCourse(courseId);
        return ResponseEntity.ok(assignments);
    }



    /**
     * Endpoint to get all assignments created by a specific instructor.
     */
    @GetMapping("/assignments/instructor/{instructorId}")
    public ResponseEntity<List<Assignment>> getAssignmentsByInstructorId(@PathVariable Long instructorId) {
        List<Assignment> assignments = assignmentRepository.findByInstructorId(instructorId);
        if (assignments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(assignments);
    }

}

