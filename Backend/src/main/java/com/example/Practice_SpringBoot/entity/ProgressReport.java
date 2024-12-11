package com.example.Practice_SpringBoot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ProgressReports")
public class ProgressReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long moduleId;

    @Column(nullable = false)
    private Long courseId;

    @Column(nullable = false)
    private double progressPercentage;

    @Column(nullable = false)
    private LocalDateTime generatedDate;

    public void setUpdatedDate(LocalDateTime now) {
        generatedDate=now;
    }
}

