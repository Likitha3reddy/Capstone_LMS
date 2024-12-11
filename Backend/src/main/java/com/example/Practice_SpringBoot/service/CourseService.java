package com.example.Practice_SpringBoot.service;

import com.example.Practice_SpringBoot.Exception.ResourceNotFoundException;
import com.example.Practice_SpringBoot.entity.Course;
import com.example.Practice_SpringBoot.payload.CourseDto;
import com.example.Practice_SpringBoot.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(Course course) {
        course.setCreatedDate(LocalDateTime.now());
        return courseRepository.save(course);
    }

    public Course getCourse(Long courseId) throws ResourceNotFoundException {
        // Check if the course ID is valid
        if (courseId == null) {
            throw new ResourceNotFoundException("Course ID cannot be null");
        }

        // Fetch the course from the repository
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course with ID " + courseId + " not found"));

        return course;
    }

    /*
    get all courses creatted by a instrtor by id
     */
    public List<CourseDto> getCourseById(Long instructor_id){
        List<Course> courses = courseRepository.findByInstructorId(instructor_id);
        return courses.stream().map(course -> {
            CourseDto courseDTO = new CourseDto();
            courseDTO.setCourseId(course.getCourseId());
            courseDTO.setTitle(course.getTitle());
            courseDTO.setDescription(course.getDescription());
            courseDTO.setCategory(course.getCategory());
            courseDTO.setInstructorId(course.getInstructorId());
            courseDTO.setCreatedDate(course.getCreatedDate());
            courseDTO.setStatus(course.getStatus());
            courseDTO.setImageUrl(course.getImageUrl());
            return courseDTO;
        }).collect(Collectors.toList());
    }

    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();

        // Map Course entities to CourseDTOs
        return courses.stream().map(course -> {
            CourseDto courseDTO = new CourseDto();
            courseDTO.setCourseId(course.getCourseId());
            courseDTO.setTitle(course.getTitle());
            courseDTO.setDescription(course.getDescription());
            courseDTO.setCategory(course.getCategory());
            courseDTO.setInstructorId(course.getInstructorId());
            courseDTO.setCreatedDate(course.getCreatedDate());
            courseDTO.setStatus(course.getStatus());
            courseDTO.setImageUrl(course.getImageUrl());
            return courseDTO;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getAverageProgressByCourseId(Long courseId) {
        return courseRepository.findAverageProgressWithUserDetails(courseId);
    }
}
