package com.moviehub.dto;

import com.moviehub.entity.ContentType;

public class RatingRequest {
    private Long contentId;
    private ContentType contentType;
    private Integer score;
    
    // Constructors
    public RatingRequest() {}
    
    public RatingRequest(Long contentId, ContentType contentType, Integer score) {
        this.contentId = contentId;
        this.contentType = contentType;
        this.score = score;
    }
    
    // Getters and Setters
    public Long getContentId() { return contentId; }
    public void setContentId(Long contentId) { this.contentId = contentId; }
    
    public ContentType getContentType() { return contentType; }
    public void setContentType(ContentType contentType) { this.contentType = contentType; }
    
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
}