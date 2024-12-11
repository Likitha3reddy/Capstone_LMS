package com.example.Practice_SpringBoot.service;

import com.example.Practice_SpringBoot.Exception.ResourceNotFoundException;
import com.example.Practice_SpringBoot.entity.*;
import com.example.Practice_SpringBoot.entity.Module;
import com.example.Practice_SpringBoot.payload.ContentDto;
import com.example.Practice_SpringBoot.payload.ModuleDto;
import com.example.Practice_SpringBoot.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModuleService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    public ContentRepository contentRepository;



    @Autowired
    private DashboardRepository dashboardRepository;


    public Module addModuleToCourse(Long courseId, Module module) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            module.setCourse(course); // Link the module to the course
            return moduleRepository.save(module); // Save the module
        } else {
            // Handle the case where the course is not found
            throw new ResourceNotFoundException("Course with ID " + courseId + " not found");
        }
    }


    public Content addContentToModule(Long moduleId, MultipartFile file, String contentType) {
        // Fetch the module
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module with ID " + moduleId + " not found"));

        try {
            // Validate content type
            ContentType validatedContentType = validateContentType(contentType);

            // Create Content entity
            Content content = new Content();
            content.setContentType(validatedContentType); // Use the provided content type
            content.setUrlOrPath(file.getBytes()); // Save the file as a byte array
            content.setModule(module); // Link the module

            // Save content to the database
            return contentRepository.save(content);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }
    }

    // Helper to validate the provided content type
    private ContentType validateContentType(String contentType) {
        try {
            return ContentType.valueOf(contentType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid content type: " + contentType + ". Allowed values are VIDEO or PDF.");
        }
    }



    public Optional<Content> getContentById(Long contentId) {
        return contentRepository.findById(contentId);

    }















    public List<ModuleDto> getModulesByCourseId(Long courseId) {
        List<Module> modules = moduleRepository.findByCourse_CourseId(courseId);
        return modules.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    private ModuleDto convertToDTO(Module module) {
        ModuleDto dto = new ModuleDto();
        dto.setModuleId(module.getModuleId());
        dto.setTitle(module.getTitle());
        dto.setDescription(module.getDescription());

        // Ensure module.getContents() is not null before streaming
        if (module.getContents() != null) {
            List<ContentDto> list = new ArrayList<>();
            for (Content content : module.getContents()) {
                ContentDto contentDto = convertContentToDTO(content);
                list.add(contentDto);
            }
            dto.setContents(list);
        } else {
            dto.setContents(new ArrayList<>()); // Initialize an empty list if contents are null
        }

        return dto;
    }

    private String encodeToBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
    private ContentDto convertContentToDTO(Content content) {
        ContentDto contentDTO = new ContentDto();
        contentDTO.setContentId(content.getContentId());
        contentDTO.setContentType(content.getContentType().name()); // Assuming contentType is an Enum
        contentDTO.setUrlOrPath(encodeToBase64(content.getUrlOrPath())); // Convert byte[] to Base64
        return contentDTO;
    }


    /*
    Dashboard
     */


}
