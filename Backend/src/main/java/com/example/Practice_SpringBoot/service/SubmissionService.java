package com.example.Practice_SpringBoot.service;

import com.example.Practice_SpringBoot.entity.Submission;
import com.example.Practice_SpringBoot.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepository;

    public Submission saveSubmission(Submission submission) {
        return submissionRepository.save(submission);
    }

    public List<Submission> getSubmissionsByCourse(Long courseId) {
        return submissionRepository.findByCourseId(courseId);
    }

    public List<Submission> getSubmissionsByUserId(Long userId) {
        return submissionRepository.findByUserId(userId);
    }
    public boolean isSubmitted(Long assignmentId, Long userId) {
        return submissionRepository.findByAssignmentIdAndUserId(assignmentId, userId)
                .stream()
                .anyMatch(submission -> "SUBMITTED".equals(submission.getStatus()));
    }

    public Submission gradeSubmission(Long submissionId, String grade) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));
        submission.setGrade(grade);
        submission.setStatus("GRADED");
        return submissionRepository.save(submission);
    }

    public List<Submission> getSubmissionsByAssignmentIdAndUserId(Long assignmentId, Long userId) {
        return submissionRepository.findByAssignmentIdAndUserId(assignmentId, userId);
    }

    public List<Submission> getSubmissionsByAssignmentId(Long assignmentId) {
        return submissionRepository.findByAssignmentId(assignmentId);
    }
    public List<Submission> getSubmissionsByAssignmentIds(List<Long> assignmentIds) {
        return submissionRepository.findByAssignmentIds(assignmentIds);
    }
    public Optional<Submission> findById(Long submissionId) {
        return submissionRepository.findById(submissionId);
    }

    public Submission save(Submission submission) {
        return submissionRepository.save(submission);
    }




}

