package com.moviehub.repository;

import com.moviehub.entity.ContentType;
import com.moviehub.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByContentIdAndContentTypeOrderByCreatedAtDesc(Long contentId, ContentType contentType);
    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);
}