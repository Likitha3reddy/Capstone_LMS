package com.example.Practice_SpringBoot.repository;

import com.example.Practice_SpringBoot.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Fetch reviews by courseId
    List<Review> findByCourseId(Long courseId);

    // Fetch reviews by userId
    List<Review> findByUserId(Long userId);


}
