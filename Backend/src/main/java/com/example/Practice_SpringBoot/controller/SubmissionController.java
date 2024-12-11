package com.example.Practice_SpringBoot.controller;

import com.example.Practice_SpringBoot.entity.Assignment;
import com.example.Practice_SpringBoot.entity.Submission;
import com.example.Practice_SpringBoot.repository.SubmissionRepository;
import com.example.Practice_SpringBoot.service.AssignmentService;
import com.example.Practice_SpringBoot.service.EnrollmentService;
import com.example.Practice_SpringBoot.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private AssignmentService assignmentService;



    @PostMapping(value = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> submitAssignment(
            @RequestPart("file") MultipartFile file,
            @RequestParam("assignmentId") Long assignmentId,
            @RequestParam("courseId") Long courseId,
            @RequestParam("userId") Long userId,
            @RequestParam("status") String status) {

        try {
            // Create submission object
            Submission submission = new Submission();
            submission.setAssignmentId(assignmentId);
            submission.setCourseId(courseId);
            submission.setUserId(userId);
            submission.setStatus(status);
            submission.setSubmissionFile(file.getBytes());

            // Date will be automatically set in the entity (default value)
            // Save submission
            Submission savedSubmission = submissionService.saveSubmission(submission);
            return ResponseEntity.ok(savedSubmission);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the file.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data provided.");
        }
    }



    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Submission>> getSubmissionsByCourse(@PathVariable Long courseId) {
        List<Submission> submissions = submissionService.getSubmissionsByCourse(courseId);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Submission>> getSubmissionsByUser(@PathVariable Long userId) {
        List<Submission> submissions = submissionService.getSubmissionsByUserId(userId);
        return ResponseEntity.ok(submissions);
    }
    @GetMapping("/{userId}/all-assignments")
    public ResponseEntity<List<Assignment>> getAllAssignmentsForStudent(@PathVariable Long userId) {
        List<Long> courseIds = enrollmentService.getCourseIdsByUserId(userId);
        List<Assignment> assignments = assignmentService.getAssignmentsByCourseIds(courseIds);

        List<Assignment> unsubmittedAssignments = assignments.stream()
                .filter(assignment -> !submissionService.isSubmitted(assignment.getAssignmentId(), userId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(unsubmittedAssignments);
    }
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<Submission>> getSubmissionsByUserId(@PathVariable Long userId) {
//        List<Submission> submissions = submissionService.getSubmissionsByUserId(userId);
//        return ResponseEntity.ok(submissions);
//    }
        @PostMapping("/by-assignments")
        public ResponseEntity<List<Submission>> getSubmissionsByAssignmentIds(@RequestBody List<Long> assignmentIds) {
            List<Submission> submissions = submissionService.getSubmissionsByAssignmentIds(assignmentIds);
            if (submissions.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(submissions);
        }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<List<Map<String, Object>>> getSubmissionsByInstructorId(@PathVariable Long instructorId) {
        List<Assignment> assignments = assignmentService.getAssignmentsByInstructorId(instructorId);

        if (assignments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<Long> assignmentIds = assignments.stream()
                .map(Assignment::getAssignmentId)
                .collect(Collectors.toList());

        List<Submission> submissions = submissionService.getSubmissionsByAssignmentIds(assignmentIds);

        if (submissions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Map submissions with assignment titles
        List<Map<String, Object>> response = submissions.stream()
                .map(submission -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("submissionId", submission.getSubmissionId());
                    map.put("assignmentId", submission.getAssignmentId());
                    map.put("courseId", submission.getCourseId());
                    map.put("grade", submission.getGrade());
                    map.put("status", submission.getStatus());
                    map.put("date", submission.getDate());
                    map.put("submissionFile", submission.getSubmissionFile());

                    // Fetch assignment title from the assignments list
                    String assignmentTitle = assignments.stream()
                            .filter(a -> a.getAssignmentId().equals(submission.getAssignmentId()))
                            .map(Assignment::getTitle)
                            .findFirst()
                            .orElse("Unknown Assignment");

                    map.put("assignmentTitle", assignmentTitle);
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{submissionId}/grade")
    public ResponseEntity<Submission> updateGrade(
            @PathVariable Long submissionId,
            @RequestParam String grade) {
        Optional<Submission> submissionOptional = submissionService.findById(submissionId);

        if (submissionOptional.isPresent()) {
            Submission submission = submissionOptional.get();
            submission.setGrade(grade);
            submission.setStatus("GRADED");
            submissionService.save(submission);
            return ResponseEntity.ok(submission);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/average-grade/user/{userId}")
    public ResponseEntity<Object> getAverageGradeByUserId(@PathVariable Long userId) {
        List<Submission> submissions = submissionService.getSubmissionsByUserId(userId);
        if (submissions.isEmpty()) {
            // Return a meaningful message instead of 0.0
            return ResponseEntity.ok("No submissions available");
        }
        double average = submissions.stream()
                .filter(submission -> submission.getGrade() != null) // Ensure grade is not null
                .mapToDouble(submission -> Double.parseDouble(submission.getGrade()))
                .average()
                .orElse(0.0);
        return ResponseEntity.ok(average);
    }



}
