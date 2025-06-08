package com.moviehub.controller;

import com.moviehub.dto.RatingRequest;
import com.moviehub.entity.ContentType;
import com.moviehub.entity.Rating;
import com.moviehub.entity.User;
import com.moviehub.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ratings")
@CrossOrigin(origins = "http://localhost:3000")
public class RatingController {
    
    @Autowired
    private RatingService ratingService;
    
    @GetMapping("/content/{contentId}/{contentType}")
    public Map<String, Object> getContentRating(@PathVariable Long contentId, @PathVariable ContentType contentType) {
        Double averageRating = ratingService.getAverageRating(contentId, contentType);
        Long ratingCount = ratingService.getRatingCount(contentId, contentType);
        
        Map<String, Object> response = new HashMap<>();
        response.put("averageRating", averageRating != null ? averageRating : 0.0);
        response.put("ratingCount", ratingCount);
        
        return response;
    }
    
    @GetMapping("/user/{contentId}/{contentType}")
    public ResponseEntity<?> getUserRating(@PathVariable Long contentId, @PathVariable ContentType contentType, 
                                              Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ratingService.getUserRating(contentId, contentType, user)
                .map(rating -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("id", rating.getId());
                    response.put("score", rating.getScore());
                    response.put("contentId", rating.getContentId());
                    response.put("contentType", rating.getContentType());
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> createOrUpdateRating(@RequestBody RatingRequest request, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            Rating rating = ratingService.createOrUpdateRating(
                request.getContentId(), 
                request.getContentType(), 
                user, 
                request.getScore()
            );
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", rating.getId());
            response.put("score", rating.getScore());
            response.put("contentId", rating.getContentId());
            response.put("contentType", rating.getContentType());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating/updating rating: " + e.getMessage());
        }
    }
}