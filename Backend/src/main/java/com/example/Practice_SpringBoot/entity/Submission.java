package com.example.Practice_SpringBoot.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Submission") // Matches the table name in your database
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id") // Matches the database column name
    private Long submissionId;

    @Column(name = "assignment_id") // Matches the database column name
    private Long assignmentId;

    @Column(name = "course_id") // Matches the database column name
    private Long courseId;

    @Column(name = "date") // Matches the database column name
    private LocalDateTime date = LocalDateTime.now();

    @Column(name = "grade") // Matches the database column name
    private String grade;

    @Column(name = "status") // Matches the database column name
    private String status = "CREATED";

    @Lob
    @Column(name = "submission_file") // Matches the database column name
    private byte[] submissionFile;

    @Column(name = "user_id") // Matches the database column name
    private Long userId;
}
