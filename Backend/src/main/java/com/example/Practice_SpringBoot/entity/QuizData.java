package com.example.Practice_SpringBoot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QuizData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String quizTitle;
    private Long instructorId;
    private Long moduleId;
    private Long courseId;
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int correctOption;

    // Getters and Setters
}