package com.example.Practice_SpringBoot.service;

import com.example.Practice_SpringBoot.entity.Content;
import com.example.Practice_SpringBoot.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContentService {
    @Autowired
    private ContentRepository contentRepository;

    public List<Content> getContentsByModuleId(Long moduleId) {
        return contentRepository.findByModuleModuleId(moduleId);
    }
}
