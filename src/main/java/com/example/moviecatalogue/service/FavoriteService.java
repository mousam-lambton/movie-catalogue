package com.example.moviecatalogue.service;

import com.example.moviecatalogue.dto.MovieDTOs.MovieDto;
import com.example.moviecatalogue.model.Movie;
import com.example.moviecatalogue.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    private final MovieRepository movieRepository;
    private final TmdbService tmdbService;

    @Autowired
    public FavoriteService(MovieRepository movieRepository, TmdbService tmdbService) {
        this.movieRepository = movieRepository;
        this.tmdbService = tmdbService;
    }

    // Get all favorite movies
    public List<Movie> getAllFavorites() {
        return movieRepository.findAll();
    }

    // Check if a movie is a favorite
    public boolean isFavorite(Long movieId) {
        return movieRepository.existsById(movieId);
    }

    // Add a movie to favorites
    public Movie addFavorite(Long movieId) {
        // Check if already a favorite
        Optional<Movie> existingMovie = movieRepository.findById(movieId);
        if (existingMovie.isPresent()) {
            return existingMovie.get();
        }

        // Fetch movie details from TMDb
        MovieDto movieDto = tmdbService.getMovieDetails(movieId);
        if (movieDto != null) {
            Movie movie = movieDto.toEntity();
            return movieRepository.save(movie);
        }

        return null;
    }

    // Remove a movie from favorites
    public void removeFavorite(Long movieId) {
        movieRepository.deleteById(movieId);
    }
}