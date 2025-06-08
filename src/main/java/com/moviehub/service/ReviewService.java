package com.moviehub.service;

import com.moviehub.entity.ContentType;
import com.moviehub.entity.Review;
import com.moviehub.entity.User;
import com.moviehub.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    public List<Review> getReviewsByContent(Long contentId, ContentType contentType) {
        return reviewRepository.findByContentIdAndContentTypeOrderByCreatedAtDesc(contentId, contentType);
    }
    
    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    
    public Review createReview(Long contentId, ContentType contentType, User user, String content) {
        Review review = new Review(contentId, contentType, user, content);
        return reviewRepository.save(review);
    }
    
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }
    
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}