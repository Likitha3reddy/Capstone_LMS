package com.example.Practice_SpringBoot.repository;

import com.example.Practice_SpringBoot.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module,Long> {
    List<Module> findByCourseCourseId(Long courseId);

    List<Module> findByCourse_CourseId(Long courseId);
}
