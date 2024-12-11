package com.example.Practice_SpringBoot.controller;

import com.example.Practice_SpringBoot.entity.Course;
import com.example.Practice_SpringBoot.payload.CourseDto;
import com.example.Practice_SpringBoot.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @GetMapping("/{id}")
    public Course getCourse(@PathVariable Long id) {
        return courseService.getCourse(id);
    }
    @GetMapping
    public List<CourseDto> getAllCourses(){
        return courseService.getAllCourses();
    }
    @GetMapping("/instructor/{instructor_id}")
    public List<CourseDto> getAllCoursesInstructorId(@PathVariable Long instructor_id) {
        return courseService.getCourseById(instructor_id);
    }


    @GetMapping("/{courseId}/average")
    public ResponseEntity<List<Map<String, Object>>> getAverageProgressByCourseId(@PathVariable Long courseId) {
        List<Map<String, Object>> averageProgress = courseService.getAverageProgressByCourseId(courseId);
        return ResponseEntity.ok(averageProgress);
    }


}
