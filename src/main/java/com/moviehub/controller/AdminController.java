package com.moviehub.controller;

import com.moviehub.entity.User;
import com.moviehub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/users")
    public List<Map<String, Object>> getAllUsers() {
        return userService.getAllUsers().stream()
            .map(user -> {
                Map<String, Object> userMap = Map.of(
                    "id", user.getId(),
                    "username", user.getUsername(),
                    "email", user.getEmail(),
                    "role", user.getRole().name(),
                    "createdAt", user.getCreatedAt()
                );
                return userMap;
            })
            .collect(Collectors.toList());
    }
    
    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardStats() {
        // Add dashboard statistics here
        return Map.of(
            "totalUsers", userService.getTotalUsers(),
            "totalMovies", userService.getTotalMovies(),
            "totalSeries", userService.getTotalSeries(),
            "totalReviews", userService.getTotalReviews()
        );
    }
} 