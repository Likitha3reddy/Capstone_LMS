package com.example.Practice_SpringBoot.controller;

import com.example.Practice_SpringBoot.entity.Image;
import com.example.Practice_SpringBoot.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/imageOrFile")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/save")
    public String saveImageOrFile(@RequestParam("file")MultipartFile file,
                                  @RequestParam("name") String name,
                                  @RequestParam("description") String description) throws IOException {
       return  imageService.saveImage(file,name,description);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Image>> gettingAllImages(){
        List<Image> images=imageService.getAllImages();
        return ResponseEntity.ok().body(images);
    }

//
//    @GetMapping("/image/{id}")
//    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
//        Image image = imageService.getImageById(id);
//        if (image != null) {
//            // Checking file extension from database or metadata can be added
//            return ResponseEntity.ok()
//                    .contentType(MediaType.IMAGE_JPEG)  // Change to IMAGE_PNG if image is PNG
//                    .body(image.getImage());  // Return the byte array of the image
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        Optional<Image> image = imageService.getImageById(id);  // Retrieve file by ID

        return image.map(value -> {
            byte[] fileContent = value.getImage();
            String fileName = value.getName().toLowerCase();  // Get file name to infer extension

            // Dynamically set content type based on file extension
            String contentType = getContentType(fileName);

            // Return file with the appropriate content type
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))  // Set content type
                    .body(fileContent);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Helper method to determine content type
    private String getContentType(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".gif")) {
            return "image/gif";
        } else if (fileName.endsWith(".txt")) {
            return "text/plain";
        } else if (fileName.endsWith(".mp4")) {
            return "video/mp4";
        } else if (fileName.endsWith(".mp3")) {
            return "audio/mp3";
        } else if (fileName.endsWith(".pdf")) {
            return "application/pdf";
        } else {
            return "application/octet-stream";  // Default for unknown types
        }
    }
}
