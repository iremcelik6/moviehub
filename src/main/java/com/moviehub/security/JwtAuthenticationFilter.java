package com.moviehub.security;

import com.moviehub.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT Authentication Filter
 * Her HTTP request'inde JWT token'ı kontrol eder
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserService userService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        // Authorization header'ından token'ı al
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        
        // Bearer token kontrolü
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtUtil.getUsernameFromToken(token);
            } catch (Exception e) {
                logger.error("JWT Token parsing error: " + e.getMessage());
            }
        }
        
        // Token geçerliyse ve henüz authentication yapılmamışsa
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // Token'ı validate et
            if (jwtUtil.validateToken(token) && !jwtUtil.isTokenExpired(token)) {
                
                // Kullanıcıyı veritabanından al
                userService.getUserByUsername(username).ifPresent(user -> {
                    
                    // Authentication token oluştur
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(
                            user, 
                            null, 
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                        );
                    
                    // Request detaylarını ekle
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // Security context'e ekle
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                });
            }
        }
        
        // Filter chain'i devam ettir
        filterChain.doFilter(request, response);
    }
    
    /**
     * Bu filter'ın uygulanmaması gereken endpoint'ler
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        
        // Authentication endpoint'leri için filter'ı atla
        return path.startsWith("/api/auth/") || 
               path.startsWith("/api/movies") && request.getMethod().equals("GET") ||
               path.startsWith("/api/series") && request.getMethod().equals("GET") ||
               path.startsWith("/h2-console") || 
               path.startsWith("/actuator");
    }
}