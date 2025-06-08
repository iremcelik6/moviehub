package com.moviehub.service;

import com.moviehub.entity.ContentType;
import com.moviehub.entity.Rating;
import com.moviehub.entity.User;
import com.moviehub.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class RatingService {
    
    private static final Logger logger = LoggerFactory.getLogger(RatingService.class);
    
    @Autowired
    private RatingRepository ratingRepository;
    
    public Rating createOrUpdateRating(Long contentId, ContentType contentType, User user, Integer score) {
        logger.info("Creating/updating rating for contentId: {}, contentType: {}, userId: {}, score: {}", 
            contentId, contentType, user.getId(), score);
            
        Optional<Rating> existingRating = ratingRepository.findByContentIdAndContentTypeAndUser(
            contentId, contentType, user
        );
        
        Rating rating;
        if (existingRating.isPresent()) {
            logger.info("Found existing rating, updating score from {} to {}", 
                existingRating.get().getScore(), score);
            rating = existingRating.get();
            rating.setScore(score);
        } else {
            logger.info("No existing rating found, creating new rating");
            rating = new Rating(contentId, contentType, user, score);
        }
        
        try {
            Rating savedRating = ratingRepository.save(rating);
            logger.info("Successfully saved rating with id: {}", savedRating.getId());
            return savedRating;
        } catch (Exception e) {
            logger.error("Error saving rating: {}", e.getMessage(), e);
            throw e;
        }
    }
    
    public Optional<Rating> getUserRating(Long contentId, ContentType contentType, User user) {
        return ratingRepository.findByContentIdAndContentTypeAndUser(contentId, contentType, user);
    }
    
    public Double getAverageRating(Long contentId, ContentType contentType) {
        return ratingRepository.calculateAverageRating(contentId, contentType);
    }
    
    public Long getRatingCount(Long contentId, ContentType contentType) {
        return ratingRepository.countByContentIdAndContentType(contentId, contentType);
    }
}