package com.moviehub.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "ratings", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"content_id", "content_type", "user_id"})
})
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "content_id", nullable = false)
    private Long contentId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
    
    @Column(nullable = false)
    private Integer score; // 1-10
    
    // Virtual relationships
    @ManyToOne
    @JoinColumn(name = "content_id", insertable = false, updatable = false)
    @JsonBackReference
    private Movie movie;
    
    @ManyToOne
    @JoinColumn(name = "content_id", insertable = false, updatable = false)
    @JsonBackReference
    private Series series;
    
    // Constructors
    public Rating() {}
    
    public Rating(Long contentId, ContentType contentType, User user, Integer score) {
        this.contentId = contentId;
        this.contentType = contentType;
        this.user = user;
        this.score = score;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getContentId() { return contentId; }
    public void setContentId(Long contentId) { this.contentId = contentId; }
    
    public ContentType getContentType() { return contentType; }
    public void setContentType(ContentType contentType) { this.contentType = contentType; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    
    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }
    
    public Series getSeries() { return series; }
    public void setSeries(Series series) { this.series = series; }
}