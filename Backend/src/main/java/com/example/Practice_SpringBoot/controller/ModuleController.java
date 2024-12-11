package com.example.Practice_SpringBoot.controller;

import com.example.Practice_SpringBoot.entity.*;
import com.example.Practice_SpringBoot.entity.Module;
import com.example.Practice_SpringBoot.payload.ModuleDto;
import com.example.Practice_SpringBoot.service.ContentService;
import com.example.Practice_SpringBoot.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaTypeFactory.getMediaType;

@RestController
@RequestMapping("modules")

public class ModuleController {
    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ContentService contentService;

    @PostMapping("/{courseId}")
    public Module addModule(@PathVariable Long courseId, @RequestBody Module module) {
        return moduleService.addModuleToCourse(courseId, module);
    }



    @PostMapping("/{moduleId}/contents")
    public ResponseEntity<Content> addContentToModule(
            @PathVariable Long moduleId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("contentType") String contentType) {
        Content savedContent = moduleService.addContentToModule(moduleId, file, contentType);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContent);
    }


    @GetMapping("/contents/{contentId}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long contentId) {
        Optional<Content> content = moduleService.getContentById(contentId);  // Retrieve content by ID

        return content.map(value -> {
            byte[] fileContent = value.getUrlOrPath();

            // Dynamically determine content type based on the ContentType enum
            String contentType = getContentType(value.getContentType());

            // Build response with file content and appropriate headers
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(fileContent);
        }).orElseGet(() -> ResponseEntity.notFound().build());  // Return 404 if not found
    }

    private String getContentType(ContentType contentType) {
        switch (contentType) {
            case VIDEO:
                return "video/mp4";  // For video files
            case PDF:
                return "application/pdf";  // For PDF files
            default:
                return "application/octet-stream";  // Fallback for unknown content types
        }
    }



    /*
    dashboard
     */

//
//
//    @GetMapping("/{userId}")
//    public ResponseEntity<Dashboard> getDashboard(@PathVariable Long userId) {
//        Dashboard dashboard = dashboardService.getDashboardByUserId(userId);
//        return ResponseEntity.ok(dashboard);
//    }
//
//    @PutMapping("/{userId}")
//    public ResponseEntity<Dashboard> updateDashboard(@PathVariable Long userId, @RequestBody Map<String, Object> updatedData) throws JsonProcessingException {
//        String updatedDataJson = new ObjectMapper().writeValueAsString(updatedData); // Convert Map to JSON string
//        Dashboard updatedDashboard = dashboardService.updateDashboard(userId, updatedDataJson);
//        return ResponseEntity.ok(updatedDashboard);
//    }



    //geting all the modules related to course
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ModuleDto>> getModulesByCourseId(@PathVariable Long courseId) {
        List<ModuleDto> modules = moduleService.getModulesByCourseId(courseId);
        return ResponseEntity.ok(modules);
    }

    // getting the contents by module id
    @GetMapping("/contents/module/{moduleId}")
    public ResponseEntity<List<Content>> getContentsByModuleId(@PathVariable Long moduleId) {
        List<Content> contents = contentService.getContentsByModuleId(moduleId);
        return ResponseEntity.ok(contents);
    }






}
