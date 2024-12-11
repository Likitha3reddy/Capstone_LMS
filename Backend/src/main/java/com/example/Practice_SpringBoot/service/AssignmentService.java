package com.example.Practice_SpringBoot.service;


import com.example.Practice_SpringBoot.entity.Assignment;
import com.example.Practice_SpringBoot.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;

    public Assignment createAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public List<Assignment> getAssignmentsByCourse(Long courseId) {
        return assignmentRepository.findByCourseId(courseId);
    }

    public List<Assignment> getAssignmentsByInstructor(Long instructorId) {
        return assignmentRepository.findByInstructorId(instructorId);
    }

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }
    public List<Assignment> getAssignmentsByCourseIds(List<Long> courseIds) {
        return assignmentRepository.findByCourseIdIn(courseIds);
    }

    public List<Assignment> getAssignmentsByInstructorId(Long instructorId) {
        return assignmentRepository.findByInstructorId(instructorId);
    }
}

