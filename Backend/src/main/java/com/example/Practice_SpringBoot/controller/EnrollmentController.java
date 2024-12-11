package com.example.Practice_SpringBoot.controller;

import com.example.Practice_SpringBoot.entity.Course;
import com.example.Practice_SpringBoot.entity.Enrollment;
import com.example.Practice_SpringBoot.entity.EnrollmentRequest;
import com.example.Practice_SpringBoot.repository.EnrollmentRepository;
import com.example.Practice_SpringBoot.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;



    @PostMapping("/api/enrollments")
    public ResponseEntity<Map<String, String>> createEnrollment(@RequestBody EnrollmentRequest enrollmentRequest) {
        Map<String, String> response = new HashMap<>();

        try {
            // Dynamically generate the status (true for enrollment, false for rejection)
            boolean canEnroll = Math.random() < 0.85; // 80% chance to allow enrollment

            if (canEnroll) {
                // Create and save the enrollment
                Enrollment enrollment = new Enrollment();
                enrollment.setUserId(enrollmentRequest.getUserId());
                enrollment.setCourseId(enrollmentRequest.getCourseId());
                enrollment.setStatus("Enrolled"); // Set status to "Enrolled"

                enrollmentService.createEnrollment(enrollment);

                // Return success response
                response.put("message", "Enrollment successful!");
                return ResponseEntity.ok(response);
            } else {
                // Return rejection response
                response.put("message", "Enrollment rejected by the system.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (RuntimeException e) {
            // Handle known runtime errors gracefully
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            // Handle unexpected errors
            response.put("message", "An unexpected error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/api/enrollments/courses/{userId}")
    public ResponseEntity<List<Course>> getCoursesByUserId(@PathVariable Long userId) {
        List<Course> courses = enrollmentService.getCoursesByUserId(userId);
        return ResponseEntity.ok(courses);
    }



//    }
//    @GetMapping("/check-enrollment/{userId}/{courseId}")
//    public ResponseEntity<Boolean> checkEnrollment(@PathVariable Long userId, @PathVariable Long courseId) {
//        boolean exists = enrollmentRepository.existsByUserIdAndCourseId(userId, courseId);
//        return ResponseEntity.ok(exists);
//    }

    @GetMapping("/api/enrollments/count/{courseId}")
    public ResponseEntity<Long> getEnrollmentCount(@PathVariable Long courseId) {
        Long count = enrollmentService.getEnrollmentCountByCourseId(courseId);
        return ResponseEntity.ok(count);
    }

}

