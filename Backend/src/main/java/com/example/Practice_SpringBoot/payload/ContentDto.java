package com.example.Practice_SpringBoot.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContentDto {
    private Long contentId;
    private String contentType; // VIDEO or PDF
    private String urlOrPath;
}
