package com.example.Practice_SpringBoot.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@AllArgsConstructor
@Setter
@Getter
@Table(name = "courses")

public class Course {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long courseId;

        private String title;
        private String description;
        private String category;
        private Long instructorId;
        private LocalDateTime createdDate;
        private String status;
        @Column(name="imageUrl")
        private String imageUrl;

        @Transient
        private List<Module> modules;
//        @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
//        @JsonManagedReference
//        private List<Module> modules = new ArrayList<>();

//        @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
//        @JsonManagedReference
//        private List<Enrollment> enrollments;




}
