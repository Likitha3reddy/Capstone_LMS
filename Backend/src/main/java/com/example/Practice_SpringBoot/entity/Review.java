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
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false)
    private Long courseId; // Use courseId directly without a ManyToOne relationship

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int rating; // Use int for a 1-5 scale rating

    @Column(length = 500)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime dateSubmitted;
}
