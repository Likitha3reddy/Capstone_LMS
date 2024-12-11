package com.example.Practice_SpringBoot.service;

import com.example.Practice_SpringBoot.entity.Review;
import com.example.Practice_SpringBoot.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    // Add a review
    public Review addReview(Review review) {
        // Ensure the date is automatically set when a review is added
        review.setDateSubmitted(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    // Get all reviews for a specific course
    public List<Review> getReviewsByCourseId(Long courseId) {
        return reviewRepository.findByCourseId(courseId);
    }

    // Get all reviews submitted by a specific user
    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    // Delete a review by ID
    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new IllegalArgumentException("Review with ID " + reviewId + " does not exist.");
        }
        reviewRepository.deleteById(reviewId);
    }
}
