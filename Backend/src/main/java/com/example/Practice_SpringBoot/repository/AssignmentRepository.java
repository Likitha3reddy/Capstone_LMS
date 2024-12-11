package com.example.Practice_SpringBoot.repository;

import com.example.Practice_SpringBoot.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByCourseId(Long courseId);
    List<Assignment> findByCourseIdIn(List<Long> courseIds);
    @Query("SELECT a FROM Assignment a WHERE a.instructorId = :instructorId")
    List<Assignment> findByInstructorId(@Param("instructorId") Long instructorId);

}
