package com.moviehub.controller;

import com.moviehub.entity.ContentType;
import com.moviehub.entity.Favorite;
import com.moviehub.entity.User;
import com.moviehub.service.FavoriteService;
import com.moviehub.service.MovieService;
import com.moviehub.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "http://localhost:3000")
public class FavoriteController {
    
    @Autowired
    private FavoriteService favoriteService;
    
    @Autowired
    private MovieService movieService;
    
    @Autowired
    private SeriesService seriesService;
    
    @GetMapping
    public List<Map<String, Object>> getUserFavorites(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Favorite> favorites = favoriteService.getUserFavorites(user.getId());
        
        return favorites.stream().map(favorite -> {
            Map<String, Object> favoriteMap = new HashMap<>();
            favoriteMap.put("id", favorite.getId());
            favoriteMap.put("contentId", favorite.getContentId());
            favoriteMap.put("contentType", favorite.getContentType());
            
            // İçerik detaylarını ekle
            if (favorite.getContentType() == ContentType.MOVIE) {
                movieService.getMovieById(favorite.getContentId()).ifPresent(movie -> {
                    favoriteMap.put("title", movie.getTitle());
                    favoriteMap.put("posterUrl", movie.getPosterUrl());
                    favoriteMap.put("genre", movie.getGenre());
                });
            } else if (favorite.getContentType() == ContentType.SERIES) {
                seriesService.getSeriesById(favorite.getContentId()).ifPresent(series -> {
                    favoriteMap.put("title", series.getTitle());
                    favoriteMap.put("posterUrl", series.getPosterUrl());
                    favoriteMap.put("genre", series.getGenre());
                });
            }
            
            return favoriteMap;
        }).collect(Collectors.toList());
    }
    
    @PostMapping
    public ResponseEntity<Favorite> addToFavorites(@RequestBody Map<String, Object> request, 
                                                  Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Long contentId = Long.valueOf(request.get("contentId").toString());
        ContentType contentType = ContentType.valueOf(request.get("contentType").toString());
        
        Favorite favorite = favoriteService.addToFavorites(user, contentId, contentType);
        
        if (favorite != null) {
            return ResponseEntity.ok(favorite);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{contentId}/{contentType}")
    public ResponseEntity<?> removeFromFavorites(@PathVariable Long contentId, @PathVariable ContentType contentType,
                                               Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        boolean removed = favoriteService.removeFromFavorites(user.getId(), contentId, contentType);
        if (removed) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/check/{contentId}/{contentType}")
    public Map<String, Boolean> checkFavorite(@PathVariable Long contentId, @PathVariable ContentType contentType,
                                             Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        boolean isFavorite = favoriteService.isFavorite(user.getId(), contentId, contentType);
        return Map.of("isFavorite", isFavorite);
    }
}