package com.example.Practice_SpringBoot.repository;

import com.example.Practice_SpringBoot.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content,Long> {
    List<Content> findByModuleModuleId(Long moduleId);
}
