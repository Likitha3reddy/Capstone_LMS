package com.example.Practice_SpringBoot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "modules")
@Getter
@Setter
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moduleId;

    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)

    @JsonBackReference
    private Course course;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Prevent infinite recursion
    private List<Content> contents = new ArrayList<>();
}