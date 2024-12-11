package com.example.Practice_SpringBoot.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnrollmentRequest {
    private Long userId;
    private Long courseId;
}
