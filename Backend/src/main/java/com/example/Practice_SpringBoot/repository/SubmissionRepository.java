package com.example.Practice_SpringBoot.repository;

import com.example.Practice_SpringBoot.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByCourseId(Long courseId); // Correct property name
    List<Submission> findByUserId(Long userId);     // Matches `user_id`
    List<Submission> findByAssignmentId(Long assignmentId); // Matches `assignment_id`
    List<Submission> findByAssignmentIdAndUserId(Long assignmentId, Long userId);

    boolean existsByAssignmentIdAndUserId(Long assignmentId, Long userId);

    @Query("SELECT s FROM Submission s WHERE s.assignmentId IN :assignmentIds")
    List<Submission> findByAssignmentIds(@Param("assignmentIds") List<Long> assignmentIds);
}
