package com.example.Practice_SpringBoot.service;

import com.example.Practice_SpringBoot.Exception.ResourceNotFoundException;
import com.example.Practice_SpringBoot.entity.Dashboard;
import com.example.Practice_SpringBoot.repository.CourseRepository;
import com.example.Practice_SpringBoot.repository.DashboardRepository;
import com.example.Practice_SpringBoot.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;


    public Dashboard getDashboardByUserId(Long userId) {
        return dashboardRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Dashboard not found for user ID " + userId));
    }

    public Dashboard updateDashboard(Long userId, String updatedData) {
        Dashboard dashboard = dashboardRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Dashboard not found for user ID " + userId));
        dashboard.setData(updatedData); // Update the JSON data
        return dashboardRepository.save(dashboard);
    }

    public Dashboard createDashboard(Dashboard dashboard) {
        return dashboardRepository.save(dashboard);
    }

//    public Enrollment createEnrollment(Long courseId, Long userId) {
//        // Fetch Course and User
//        Course course = courseRepository.findById(courseId)
//                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
//        UserDetails user = userDetailsRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//        // Create Enrollment
//        Enrollment enrollment = new Enrollment();
//        enrollment.setCourse(course);
//        enrollment.setUser(user); // This now matches the type
//
//        enrollment.setStatus("PENDING");
//        enrollment.setEnrollmentDate(LocalDateTime.now());
//
//        // Save and return the enrollment
//        return enrollmentRepository.save(enrollment);
//    }
//
//
//    public List<Enrollment> getEnrollments(Long userId, Long courseId) {
//        if (userId != null && courseId != null) {
//            return enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
//        } else if (userId != null) {
//            return enrollmentRepository.findByUserId(userId);
//        } else if (courseId != null) {
//            return enrollmentRepository.findByCourseId(courseId);
//        } else {
//            return enrollmentRepository.findAll();
//        }
//    }
}
