package com.example.Practice_SpringBoot.repository;

import com.example.Practice_SpringBoot.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findByInstructorId(Long InstructorId);
    long countByInstructorId(Long instructorId);

    @Query("SELECT new map(u.userName as userName, AVG(pr.progressPercentage) as averageProgress) " +
            "FROM ProgressReport pr JOIN UserDetails u ON pr.userId = u.userId " +
            "WHERE pr.courseId = :courseId " +
            "GROUP BY u.userName")
    List<Map<String, Object>> findAverageProgressWithUserDetails(Long courseId);
}
