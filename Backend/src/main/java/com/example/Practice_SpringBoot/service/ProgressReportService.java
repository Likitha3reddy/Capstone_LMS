package com.example.Practice_SpringBoot.service;

import com.example.Practice_SpringBoot.entity.ProgressReport;
import com.example.Practice_SpringBoot.repository.ProgressReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProgressReportService {

    @Autowired
    private ProgressReportRepository progressReportRepository;

    // Save or update a progress report
    public void saveProgress(Long userId, Long courseId, Long moduleId, Integer progressPercentage) {
        // Use the injected repository instance (progressReportRepository)
        ProgressReport progress = progressReportRepository
                .findByUserIdAndCourseIdAndModuleId(userId, courseId, moduleId)
                .orElse(new ProgressReport());

        // Update the fields of the ProgressReport entity
        progress.setUserId(userId);
        progress.setCourseId(courseId);
        progress.setModuleId(moduleId);
        progress.setProgressPercentage(progressPercentage);
        progress.setUpdatedDate(LocalDateTime.now());

        // Save the progress report back to the repository
        progressReportRepository.save(progress);
    }

    // Get progress reports by course
    public List<ProgressReport> getProgressReportsByCourseId(Long courseId) {
        return progressReportRepository.findByCourseId(courseId);
    }

    // Get progress reports by user
    public List<ProgressReport> getProgressReportsByUserId(Long userId) {
        return progressReportRepository.findByUserId(userId);
    }

    // Get average progress for a course
    public Double getAverageProgressByCourseId(Long courseId) {
        return progressReportRepository.findAverageProgressByCourseId(courseId);
    }
    public Double getAverageProgress(Long userId) {
        return progressReportRepository.findAverageProgressByUserId(userId);
    }
}
