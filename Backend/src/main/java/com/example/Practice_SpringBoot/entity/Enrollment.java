package com.example.Practice_SpringBoot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;

    @Column(nullable = false)
    private Long courseId;


    @Column(nullable = false)
    private Long userId;
    @Setter
    @Getter
    @Column(nullable = false)
    private LocalDateTime enrollmentDate;

    private String status;

}
