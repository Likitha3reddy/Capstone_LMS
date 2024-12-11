package com.example.Practice_SpringBoot.service;

import com.example.Practice_SpringBoot.entity.Course;
import com.example.Practice_SpringBoot.entity.Enrollment;
import com.example.Practice_SpringBoot.entity.UserDetails;
import com.example.Practice_SpringBoot.repository.CourseRepository;
import com.example.Practice_SpringBoot.repository.EnrollmentRepository;
import com.example.Practice_SpringBoot.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {
    @Autowired
    private UserDetailsRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public void createEnrollment(Enrollment enrollment) {

        if (enrollmentRepository.existsByUserIdAndCourseId(enrollment.getUserId(), enrollment.getCourseId())) {
            throw new RuntimeException("User is already enrolled in this course.");
        }else{
            // Proceed to save the enrollment if it doesn't exist
            Enrollment dbenrollment = new Enrollment();
            dbenrollment.setUserId(enrollment.getUserId());
            dbenrollment.setCourseId(enrollment.getCourseId());
            dbenrollment.setEnrollmentDate(LocalDateTime.now());
            System.out.println("Setting enrollmentDate: " + dbenrollment.getEnrollmentDate());
            dbenrollment.setStatus("Active");

            enrollmentRepository.save(dbenrollment);
        }


    }

    public List<Course> getCoursesByUserId(Long userId) {
        // Get all course IDs enrolled by the user
        List<Long> courseIds = enrollmentRepository.findCourseIdsByUserId(userId);

        // Fetch courses by IDs from the course repository
        return courseRepository.findAllById(courseIds);
    }
    public List<Long> getCourseIdsByUserId(Long userId) {
        List<Enrollment> enrollments = enrollmentRepository.findByUserId(userId);
        return enrollments.stream().map(Enrollment::getCourseId).collect(Collectors.toList());
    }


    public Long getEnrollmentCountByCourseId(Long courseId) {
        return enrollmentRepository.countByCourseId(courseId);
    }
}

