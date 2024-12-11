package com.example.Practice_SpringBoot.service;

import com.example.Practice_SpringBoot.entity.QuizData;
import com.example.Practice_SpringBoot.repository.QuizDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizDataRepository quizDataRepository;

    public void saveQuiz(List<QuizData> quizDataList) {
        quizDataRepository.saveAll(quizDataList);
    }

    public List<QuizData> getQuizzesByCourseAndModule(Long courseId, Long moduleId) {
        return quizDataRepository.findByCourseIdAndModuleId(courseId, moduleId);
    }
}
