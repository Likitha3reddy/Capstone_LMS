package com.example.Practice_SpringBoot.service;

import com.example.Practice_SpringBoot.entity.Course;
import com.example.Practice_SpringBoot.repository.CourseRepository;
import com.example.Practice_SpringBoot.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CombinedService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<Map<String, Object>> getCoursesWithEnrollmentCounts(Long instructorId) {
        List<Course> courses = courseRepository.findByInstructorId(instructorId);
        List<Map<String, Object>> result = new ArrayList<>();

        for (Course course : courses) {
            Long enrollmentCount = enrollmentRepository.countByCourseId(course.getCourseId());

            Map<String, Object> courseData = new HashMap<>();
            courseData.put("courseId", course.getCourseId());
            courseData.put("title", course.getTitle());
            courseData.put("description", course.getDescription());
            courseData.put("enrollmentCount", enrollmentCount);

            result.add(courseData);
        }
        return result;
    }
}
