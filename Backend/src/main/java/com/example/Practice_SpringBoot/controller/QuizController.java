package com.example.Practice_SpringBoot.controller;

import com.example.Practice_SpringBoot.entity.QuizData;
import com.example.Practice_SpringBoot.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<?> createQuiz(@RequestBody List<QuizData> quizDataList) {
        for (QuizData quizData : quizDataList) {
            // Log each quiz data for debugging
            System.out.println("Received: " + quizData);
        }

        quizService.saveQuiz(quizDataList);

        // Return a JSON response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Quiz created successfully!");
        return ResponseEntity.ok(response);
    }
    // Endpoint to fetch quizzes by courseId and moduleId
    @GetMapping("/{courseId}/{moduleId}")
    public ResponseEntity<?> getQuizzesByCourseAndModule(@PathVariable Long courseId, @PathVariable Long moduleId) {
        List<QuizData> quizzes = quizService.getQuizzesByCourseAndModule(courseId, moduleId);
        if (quizzes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quizzes);
    }

}

