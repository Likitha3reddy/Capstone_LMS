package com.example.Practice_SpringBoot.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentId;

    private String title;
    private String description;

    private Long courseId;        // ID of the course the assignment belongs to
    private Long instructorId;    // ID of the instructor who created it

    private LocalDate createdDate = LocalDate.now(); // Default to the current date
    private LocalDate dueDate;    // Optional due date
}

