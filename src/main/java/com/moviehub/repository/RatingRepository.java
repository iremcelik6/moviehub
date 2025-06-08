package com.moviehub.repository;

import com.moviehub.entity.ContentType;
import com.moviehub.entity.Rating;
import com.moviehub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByContentIdAndContentTypeAndUser(Long contentId, ContentType contentType, User user);
    
    List<Rating> findByContentIdAndContentType(Long contentId, ContentType contentType);
    
    List<Rating> findByUser(User user);
    
    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.contentId = :contentId AND r.contentType = :contentType")
    Double calculateAverageRating(@Param("contentId") Long contentId, @Param("contentType") ContentType contentType);
    
    Long countByContentIdAndContentType(Long contentId, ContentType contentType);
}