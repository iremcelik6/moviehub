package com.moviehub.repository;

import com.moviehub.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    List<Series> findByTitleContainingIgnoreCase(String title);
    List<Series> findByGenreContainingIgnoreCase(String genre);
    
    @Query("SELECT s FROM Series s ORDER BY s.createdAt DESC")
    List<Series> findAllOrderByCreatedAtDesc();
}