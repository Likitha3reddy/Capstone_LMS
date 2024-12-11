package com.example.Practice_SpringBoot.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private Long courseId;
    private String title;
    private String description;
    private String category;
    private Long instructorId;
    private LocalDateTime createdDate;
    private String status;
    private String imageUrl;
    private List<ModuleDto> modules; // List of module DTOs
}
