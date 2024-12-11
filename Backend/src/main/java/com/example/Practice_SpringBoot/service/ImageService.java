package com.example.Practice_SpringBoot.service;

import com.example.Practice_SpringBoot.entity.Image;
import com.example.Practice_SpringBoot.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public String saveImage(MultipartFile file,String name,String description) throws IOException {
        Image image=new Image();
        image.setName(name);
        image.setDescription(description);
        image.setImage(file.getBytes());

        imageRepository.save(image);
       return "Image saved in database";
    }
    public List<Image> getAllImages(){
        return imageRepository.findAll();
    }
    public Optional<Image> getImageById(Long id){
        return imageRepository.findById(id);
    }

}
