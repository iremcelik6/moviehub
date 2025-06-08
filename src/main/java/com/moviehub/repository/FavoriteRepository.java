package com.moviehub.repository;

import com.moviehub.entity.ContentType;
import com.moviehub.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Long userId);
    Optional<Favorite> findByUserIdAndContentIdAndContentType(Long userId, Long contentId, ContentType contentType);
    boolean existsByUserIdAndContentIdAndContentType(Long userId, Long contentId, ContentType contentType);
}