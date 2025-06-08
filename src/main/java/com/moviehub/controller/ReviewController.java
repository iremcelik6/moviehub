package com.moviehub.controller;

import com.moviehub.dto.ReviewRequest;
import com.moviehub.entity.ContentType;
import com.moviehub.entity.Review;
import com.moviehub.entity.User;
import com.moviehub.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;
    
    @GetMapping("/content/{contentId}/{contentType}")
    public List<Map<String, Object>> getReviewsByContent(@PathVariable Long contentId, @PathVariable ContentType contentType) {
        return reviewService.getReviewsByContent(contentId, contentType).stream()
            .map(review -> {
                Map<String, Object> reviewMap = new HashMap<>();
                reviewMap.put("id", review.getId());
                reviewMap.put("content", review.getContent());
                reviewMap.put("contentId", review.getContentId());
                reviewMap.put("contentType", review.getContentType());
                reviewMap.put("createdAt", review.getCreatedAt());
                reviewMap.put("username", review.getUser().getUsername());
                return reviewMap;
            })
            .collect(Collectors.toList());
    }
    
    @GetMapping("/user/{userId}")
    public List<Map<String, Object>> getReviewsByUser(@PathVariable Long userId) {
        return reviewService.getReviewsByUser(userId).stream()
            .map(review -> {
                Map<String, Object> reviewMap = new HashMap<>();
                reviewMap.put("id", review.getId());
                reviewMap.put("content", review.getContent());
                reviewMap.put("contentId", review.getContentId());
                reviewMap.put("contentType", review.getContentType());
                reviewMap.put("createdAt", review.getCreatedAt());
                return reviewMap;
            })
            .collect(Collectors.toList());
    }
    
    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewRequest request, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            Review review = reviewService.createReview(request.getContentId(), request.getContentType(), user, request.getContent());
            Map<String, Object> response = new HashMap<>();
            response.put("id", review.getId());
            response.put("content", review.getContent());
            response.put("contentId", review.getContentId());
            response.put("contentType", review.getContentType());
            response.put("createdAt", review.getCreatedAt());
            response.put("username", user.getUsername());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating review: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id, Authentication authentication) {
        // Check if user is authenticated
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401)
                .body("Authentication required. Please log in.");
        }

        // Validate ID
        if (id <= 0 || id > 1000000) {
            return ResponseEntity.badRequest()
                .body("Invalid review ID. Please provide a valid review ID.");
        }

        try {
            User user = (User) authentication.getPrincipal();
            return reviewService.getReviewById(id)
                    .map(review -> {
                        // Check if user is the owner or an admin
                        if (review.getUser().getId().equals(user.getId())) {
                            reviewService.deleteReview(id);
                            return ResponseEntity.ok().build();
                        } else if (user.getRole().name().equals("ADMIN")) {
                            reviewService.deleteReview(id);
                            return ResponseEntity.ok().build();
                        } else {
                            return ResponseEntity.status(403)
                                .body("You don't have permission to delete this review. Only the review owner or an admin can delete it.");
                        }
                    })
                    .orElse(ResponseEntity.status(404)
                        .body("Review not found with ID: " + id));
        } catch (ClassCastException e) {
            return ResponseEntity.status(401)
                .body("Invalid authentication. Please log in again.");
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body("An error occurred while deleting the review. Please try again later.");
        }
    }

    @GetMapping("/{reviewId}/user")
    public ResponseEntity<?> getReviewUser(@PathVariable Long reviewId) {
        return reviewService.getReviewById(reviewId)
                .map(review -> {
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("id", review.getUser().getId());
                    userMap.put("username", review.getUser().getUsername());
                    return ResponseEntity.ok(userMap);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}