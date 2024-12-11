package com.example.Practice_SpringBoot.repository;

import com.example.Practice_SpringBoot.entity.Course;
import com.example.Practice_SpringBoot.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    @Query("SELECT c FROM Course c JOIN Enrollment e ON c.courseId = e.courseId WHERE e.userId = :userId")
    List<Course> findCoursesByUserId(@Param("userId") Long userId);

    @Query("SELECT e.courseId FROM Enrollment e WHERE e.userId = :userId")
    List<Long> findCourseIdsByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.courseId = :courseId")
    Long countByCourseId(@Param("courseId") Long courseId);

    List<Enrollment> findByUserId(Long userId);

}
