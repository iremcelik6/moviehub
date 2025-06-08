package com.moviehub.service;

import com.moviehub.entity.Series;
import com.moviehub.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeriesService {
    
    @Autowired
    private SeriesRepository seriesRepository;
    
    public List<Series> getAllSeries() {
        return seriesRepository.findAllOrderByCreatedAtDesc();
    }
    
    public Optional<Series> getSeriesById(Long id) {
        return seriesRepository.findById(id);
    }
    
    public List<Series> searchSeries(String title) {
        return seriesRepository.findByTitleContainingIgnoreCase(title);
    }
    
    public List<Series> getSeriesByGenre(String genre) {
        return seriesRepository.findByGenreContainingIgnoreCase(genre);
    }
    
    public Series saveSeries(Series series) {
        return seriesRepository.save(series);
    }
    
    public void deleteSeries(Long id) {
        seriesRepository.deleteById(id);
    }
}