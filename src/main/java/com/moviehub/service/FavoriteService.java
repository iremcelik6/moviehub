package com.moviehub.service;

import com.moviehub.entity.ContentType;
import com.moviehub.entity.Favorite;
import com.moviehub.entity.User;
import com.moviehub.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {
    
    @Autowired
    private FavoriteRepository favoriteRepository;
    
    public List<Favorite> getUserFavorites(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }
    
    public Favorite addToFavorites(User user, Long contentId, ContentType contentType) {
        if (!favoriteRepository.existsByUserIdAndContentIdAndContentType(user.getId(), contentId, contentType)) {
            Favorite favorite = new Favorite(user, contentId, contentType);
            return favoriteRepository.save(favorite);
        }
        return null; // Already exists
    }
    
    public boolean removeFromFavorites(Long userId, Long contentId, ContentType contentType) {
        Optional<Favorite> favorite = favoriteRepository.findByUserIdAndContentIdAndContentType(
            userId, contentId, contentType
        );
        if (favorite.isPresent()) {
            favoriteRepository.delete(favorite.get());
            return true;
        }
        return false; // Favorite doesn't exist
    }
    
    public boolean isFavorite(Long userId, Long contentId, ContentType contentType) {
        return favoriteRepository.existsByUserIdAndContentIdAndContentType(userId, contentId, contentType);
    }
}