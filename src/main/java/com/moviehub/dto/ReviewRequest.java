package com.moviehub.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.moviehub.entity.ContentType;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewRequest {
    private Long contentId;
    private ContentType contentType;
    private String content;
    
    // Constructors
    public ReviewRequest() {}
    
    public ReviewRequest(Long contentId, ContentType contentType, String content) {
        this.contentId = contentId;
        this.contentType = contentType;
        this.content = content;
    }
    
    // Getters and Setters
    public Long getContentId() { return contentId; }
    public void setContentId(Long contentId) { this.contentId = contentId; }
    
    public ContentType getContentType() { return contentType; }
    public void setContentType(ContentType contentType) { this.contentType = contentType; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}