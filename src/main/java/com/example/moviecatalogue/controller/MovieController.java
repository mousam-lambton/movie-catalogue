package com.example.moviecatalogue.controller;

import com.example.moviecatalogue.dto.MovieDTOs.MovieDto;
import com.example.moviecatalogue.model.Movie;
import com.example.moviecatalogue.service.FavoriteService;
import com.example.moviecatalogue.service.TmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MovieController {

    private final TmdbService tmdbService;
    private final FavoriteService favoriteService;

    @Autowired
    public MovieController(TmdbService tmdbService, FavoriteService favoriteService) {
        this.tmdbService = tmdbService;
        this.favoriteService = favoriteService;
    }

    // Home page - shows popular movies
    @GetMapping("/")
    public String home(Model model) {
        List<MovieDto> movies = tmdbService.getPopularMovies();
        model.addAttribute("movies", movies);
        model.addAttribute("title", "Popular Movies");
        return "movie-list";
    }

    // Search for movies
    @GetMapping("/search")
    public String search(@RequestParam String query, Model model) {
        List<MovieDto> movies = tmdbService.searchMovies(query);
        model.addAttribute("movies", movies);
        model.addAttribute("title", "Search Results for: " + query);
        model.addAttribute("searchQuery", query);
        return "movie-list";
    }

    // Movie details page
    @GetMapping("/movie/{id}")
    public String movieDetails(@PathVariable Long id, Model model) {
        MovieDto movie = tmdbService.getMovieDetails(id);
        boolean isFavorite = favoriteService.isFavorite(id);

        model.addAttribute("movie", movie);
        model.addAttribute("isFavorite", isFavorite);
        return "movie-details";
    }

    // Favorites page
    @GetMapping("/favorites")
    public String favorites(Model model) {
        List<Movie> favorites = favoriteService.getAllFavorites();
        model.addAttribute("favorites", favorites);
        return "favorites";
    }

    // Add to favorites
    @PostMapping("/favorites/add/{id}")
    public String addToFavorites(@PathVariable Long id, @RequestParam(required = false) String redirect) {
        favoriteService.addFavorite(id);

        if (redirect != null && redirect.equals("details")) {
            return "redirect:/movie/" + id;
        }
        return "redirect:/favorites";
    }

    // Remove from favorites
    @PostMapping("/favorites/remove/{id}")
    public String removeFromFavorites(@PathVariable Long id, @RequestParam(required = false) String redirect) {
        favoriteService.removeFavorite(id);

        if (redirect != null && redirect.equals("details")) {
            return "redirect:/movie/" + id;
        }
        return "redirect:/favorites";
    }
}