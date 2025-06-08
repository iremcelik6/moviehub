package com.moviehub.controller;

import com.moviehub.entity.Movie;
import com.moviehub.entity.ContentType;
import com.moviehub.service.MovieService;
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
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {
    
    @Autowired
    private MovieService movieService;
    
    @Autowired
    private RatingService ratingService;
    
    @GetMapping
    public List<Map<String, Object>> getAllMovies() {
        return movieService.getAllMovies().stream()
            .map(movie -> {
                Map<String, Object> movieMap = new HashMap<>();
                movieMap.put("id", movie.getId());
                movieMap.put("title", movie.getTitle());
                movieMap.put("description", movie.getDescription());
                movieMap.put("releaseDate", movie.getReleaseDate());
                movieMap.put("genre", movie.getGenre());
                movieMap.put("director", movie.getDirector());
                movieMap.put("duration", movie.getDuration());
                movieMap.put("posterUrl", movie.getPosterUrl());
                movieMap.put("createdAt", movie.getCreatedAt());
                
                // Add rating information
                Double averageRating = ratingService.getAverageRating(movie.getId(), ContentType.MOVIE);
                Long ratingCount = ratingService.getRatingCount(movie.getId(), ContentType.MOVIE);
                movieMap.put("averageRating", averageRating != null ? averageRating : 0.0);
                movieMap.put("ratingCount", ratingCount);
                
                return movieMap;
            })
            .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id)
                .map(movie -> {
                    Map<String, Object> movieMap = new HashMap<>();
                    movieMap.put("id", movie.getId());
                    movieMap.put("title", movie.getTitle());
                    movieMap.put("description", movie.getDescription());
                    movieMap.put("releaseDate", movie.getReleaseDate());
                    movieMap.put("genre", movie.getGenre());
                    movieMap.put("director", movie.getDirector());
                    movieMap.put("duration", movie.getDuration());
                    movieMap.put("posterUrl", movie.getPosterUrl());
                    movieMap.put("createdAt", movie.getCreatedAt());
                    
                    // Add rating information
                    Double averageRating = ratingService.getAverageRating(movie.getId(), ContentType.MOVIE);
                    Long ratingCount = ratingService.getRatingCount(movie.getId(), ContentType.MOVIE);
                    movieMap.put("averageRating", averageRating != null ? averageRating : 0.0);
                    movieMap.put("ratingCount", ratingCount);
                    
                    return ResponseEntity.ok(movieMap);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    public List<Map<String, Object>> searchMovies(@RequestParam String title) {
        return movieService.searchMovies(title).stream()
            .map(movie -> {
                Map<String, Object> movieMap = new HashMap<>();
                movieMap.put("id", movie.getId());
                movieMap.put("title", movie.getTitle());
                movieMap.put("description", movie.getDescription());
                movieMap.put("releaseDate", movie.getReleaseDate());
                movieMap.put("genre", movie.getGenre());
                movieMap.put("director", movie.getDirector());
                movieMap.put("duration", movie.getDuration());
                movieMap.put("posterUrl", movie.getPosterUrl());
                movieMap.put("createdAt", movie.getCreatedAt());
                
                // Add rating information
                Double averageRating = ratingService.getAverageRating(movie.getId(), ContentType.MOVIE);
                Long ratingCount = ratingService.getRatingCount(movie.getId(), ContentType.MOVIE);
                movieMap.put("averageRating", averageRating != null ? averageRating : 0.0);
                movieMap.put("ratingCount", ratingCount);
                
                return movieMap;
            })
            .collect(Collectors.toList());
    }
    
    @GetMapping("/genre/{genre}")
    public List<Map<String, Object>> getMoviesByGenre(@PathVariable String genre) {
        return movieService.getMoviesByGenre(genre).stream()
            .map(movie -> {
                Map<String, Object> movieMap = new HashMap<>();
                movieMap.put("id", movie.getId());
                movieMap.put("title", movie.getTitle());
                movieMap.put("description", movie.getDescription());
                movieMap.put("releaseDate", movie.getReleaseDate());
                movieMap.put("genre", movie.getGenre());
                movieMap.put("director", movie.getDirector());
                movieMap.put("duration", movie.getDuration());
                movieMap.put("posterUrl", movie.getPosterUrl());
                movieMap.put("createdAt", movie.getCreatedAt());
                
                // Add rating information
                Double averageRating = ratingService.getAverageRating(movie.getId(), ContentType.MOVIE);
                Long ratingCount = ratingService.getRatingCount(movie.getId(), ContentType.MOVIE);
                movieMap.put("averageRating", averageRating != null ? averageRating : 0.0);
                movieMap.put("ratingCount", ratingCount);
                
                return movieMap;
            })
            .collect(Collectors.toList());
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Movie createMovie(@RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        return movieService.getMovieById(id)
                .map(existingMovie -> {
                    movie.setId(id);
                    return ResponseEntity.ok(movieService.saveMovie(movie));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        return movieService.getMovieById(id)
                .map(movie -> {
                    movieService.deleteMovie(id);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}