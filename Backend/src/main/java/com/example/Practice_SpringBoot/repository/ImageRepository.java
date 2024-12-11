package com.example.Practice_SpringBoot.repository;

import com.example.Practice_SpringBoot.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
}
