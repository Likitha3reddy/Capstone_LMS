package com.example.Practice_SpringBoot.repository;

import com.example.Practice_SpringBoot.entity.ProgressReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressReportRepository extends JpaRepository<ProgressReport, Long> {

    // Fetch progress reports for a specific course
    List<ProgressReport> findByCourseId(Long courseId);

    // Fetch progress reports for a specific user
    List<ProgressReport> findByUserId(Long userId);

    // Fetch average progress for a course
    @Query("SELECT AVG(pr.progressPercentage) FROM ProgressReport pr WHERE pr.courseId = :courseId")
    Double findAverageProgressByCourseId(Long courseId);

    Optional<ProgressReport> findByUserIdAndCourseId(Long userId, Long courseId);

    Optional<ProgressReport> findByUserIdAndCourseIdAndModuleId(Long userId, Long courseId, Long moduleId);

    @Query("SELECT AVG(p.progressPercentage) FROM ProgressReport p WHERE p.userId = :userId")
    Double findAverageProgressByUserId(@Param("userId") Long userId);
}

