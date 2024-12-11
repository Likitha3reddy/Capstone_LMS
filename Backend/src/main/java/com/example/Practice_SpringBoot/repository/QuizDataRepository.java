package com.example.Practice_SpringBoot.repository;

import com.example.Practice_SpringBoot.entity.QuizData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizDataRepository extends JpaRepository<QuizData,Long> {
    List<QuizData> findByCourseIdAndModuleId(Long courseId, Long moduleId);
}
