package com.example.Practice_SpringBoot.controller;

import com.example.Practice_SpringBoot.entity.Review;
import com.example.Practice_SpringBoot.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Add a review
    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Review review) {
        Review savedReview = reviewService.addReview(review);
        return ResponseEntity.ok(savedReview);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Review>> getReviewsByCourseId(@PathVariable Long courseId) {
        List<Review> reviews = reviewService.getReviewsByCourseId(courseId);
        if (reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reviews);
    }

    // Get reviews by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUserId(@PathVariable Long userId) {
        List<Review> reviews = reviewService.getReviewsByUserId(userId);
        return ResponseEntity.ok(reviews);
    }

    // Delete a review by ID
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("Review deleted successfully.");
    }
}
