package com.moviehub.controller;

import com.moviehub.entity.Series;
import com.moviehub.entity.ContentType;
import com.moviehub.service.SeriesService;
import com.moviehub.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/series")
@CrossOrigin(origins = "http://localhost:3000")
public class SeriesController {
    
    @Autowired
    private SeriesService seriesService;
    
    @Autowired
    private RatingService ratingService;
    
    @GetMapping
    public List<Map<String, Object>> getAllSeries() {
        return seriesService.getAllSeries().stream()
            .map(series -> {
                Map<String, Object> seriesMap = new HashMap<>();
                seriesMap.put("id", series.getId());
                seriesMap.put("title", series.getTitle());
                seriesMap.put("description", series.getDescription());
                seriesMap.put("releaseDate", series.getReleaseDate());
                seriesMap.put("genre", series.getGenre());
                seriesMap.put("seasons", series.getSeasons());
                seriesMap.put("episodes", series.getEpisodes());
                seriesMap.put("posterUrl", series.getPosterUrl());
                seriesMap.put("status", series.getStatus());
                seriesMap.put("createdAt", series.getCreatedAt());
                
                // Add rating information
                Double averageRating = ratingService.getAverageRating(series.getId(), ContentType.SERIES);
                Long ratingCount = ratingService.getRatingCount(series.getId(), ContentType.SERIES);
                seriesMap.put("averageRating", averageRating != null ? averageRating : 0.0);
                seriesMap.put("ratingCount", ratingCount);
                
                return seriesMap;
            })
            .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getSeriesById(@PathVariable Long id) {
        return seriesService.getSeriesById(id)
                .map(series -> {
                    Map<String, Object> seriesMap = new HashMap<>();
                    seriesMap.put("id", series.getId());
                    seriesMap.put("title", series.getTitle());
                    seriesMap.put("description", series.getDescription());
                    seriesMap.put("releaseDate", series.getReleaseDate());
                    seriesMap.put("genre", series.getGenre());
                    seriesMap.put("seasons", series.getSeasons());
                    seriesMap.put("episodes", series.getEpisodes());
                    seriesMap.put("posterUrl", series.getPosterUrl());
                    seriesMap.put("status", series.getStatus());
                    seriesMap.put("createdAt", series.getCreatedAt());
                    
                    // Add rating information
                    Double averageRating = ratingService.getAverageRating(series.getId(), ContentType.SERIES);
                    Long ratingCount = ratingService.getRatingCount(series.getId(), ContentType.SERIES);
                    seriesMap.put("averageRating", averageRating != null ? averageRating : 0.0);
                    seriesMap.put("ratingCount", ratingCount);
                    
                    return ResponseEntity.ok(seriesMap);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    public List<Map<String, Object>> searchSeries(@RequestParam String title) {
        return seriesService.searchSeries(title).stream()
            .map(series -> {
                Map<String, Object> seriesMap = new HashMap<>();
                seriesMap.put("id", series.getId());
                seriesMap.put("title", series.getTitle());
                seriesMap.put("description", series.getDescription());
                seriesMap.put("releaseDate", series.getReleaseDate());
                seriesMap.put("genre", series.getGenre());
                seriesMap.put("seasons", series.getSeasons());
                seriesMap.put("episodes", series.getEpisodes());
                seriesMap.put("posterUrl", series.getPosterUrl());
                seriesMap.put("status", series.getStatus());
                seriesMap.put("createdAt", series.getCreatedAt());
                
                // Add rating information
                Double averageRating = ratingService.getAverageRating(series.getId(), ContentType.SERIES);
                Long ratingCount = ratingService.getRatingCount(series.getId(), ContentType.SERIES);
                seriesMap.put("averageRating", averageRating != null ? averageRating : 0.0);
                seriesMap.put("ratingCount", ratingCount);
                
                return seriesMap;
            })
            .collect(Collectors.toList());
    }
    
    @GetMapping("/genre/{genre}")
    public List<Map<String, Object>> getSeriesByGenre(@PathVariable String genre) {
        return seriesService.getSeriesByGenre(genre).stream()
            .map(series -> {
                Map<String, Object> seriesMap = new HashMap<>();
                seriesMap.put("id", series.getId());
                seriesMap.put("title", series.getTitle());
                seriesMap.put("description", series.getDescription());
                seriesMap.put("releaseDate", series.getReleaseDate());
                seriesMap.put("genre", series.getGenre());
                seriesMap.put("seasons", series.getSeasons());
                seriesMap.put("episodes", series.getEpisodes());
                seriesMap.put("posterUrl", series.getPosterUrl());
                seriesMap.put("status", series.getStatus());
                seriesMap.put("createdAt", series.getCreatedAt());
                
                // Add rating information
                Double averageRating = ratingService.getAverageRating(series.getId(), ContentType.SERIES);
                Long ratingCount = ratingService.getRatingCount(series.getId(), ContentType.SERIES);
                seriesMap.put("averageRating", averageRating != null ? averageRating : 0.0);
                seriesMap.put("ratingCount", ratingCount);
                
                return seriesMap;
            })
            .collect(Collectors.toList());
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Series createSeries(@RequestBody Series series) {
        return seriesService.saveSeries(series);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Series> updateSeries(@PathVariable Long id, @RequestBody Series series) {
        return seriesService.getSeriesById(id)
                .map(existingSeries -> {
                    series.setId(id);
                    return ResponseEntity.ok(seriesService.saveSeries(series));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSeries(@PathVariable Long id) {
        return seriesService.getSeriesById(id)
                .map(series -> {
                    seriesService.deleteSeries(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}