package com.example.moviecatalogue.controller;

import com.example.moviecatalogue.dto.MovieDTOs.MovieDto;
import com.example.moviecatalogue.model.Movie;
import com.example.moviecatalogue.service.FavoriteService;
import com.example.moviecatalogue.service.TmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public String home(HttpServletRequest request, Model model) {
        List<MovieDto> movies = tmdbService.getPopularMovies();

        // Get all favorite movie IDs
        Set<Long> favoriteIds = favoriteService.getAllFavorites()
                .stream()
                .map(Movie::getId)
                .collect(Collectors.toSet());

        model.addAttribute("movies", movies);
        model.addAttribute("favoriteIds", favoriteIds);
        model.addAttribute("title", "Popular Movies");
        model.addAttribute("httpServletRequest", request);
        return "movie-list";
    }

    // topRated movies page
    @GetMapping("/topRated")
    public String topRated(HttpServletRequest request,Model model) {
        List<MovieDto> movies = tmdbService.getTopRatedMovies();

        // Get all favorite movie IDs
        Set<Long> favoriteIds = favoriteService.getAllFavorites()
                .stream()
                .map(Movie::getId)
                .collect(Collectors.toSet());

        model.addAttribute("movies", movies);
        model.addAttribute("favoriteIds", favoriteIds);
        model.addAttribute("title", "Top Rated Movies");
        model.addAttribute("httpServletRequest", request);
        return "movie-list";
    }

    // Search for movies
    @GetMapping("/search")
    public String search(@RequestParam String query,HttpServletRequest request, Model model) {
        List<MovieDto> movies = tmdbService.searchMovies(query);

        // Get all favorite movie IDs
        Set<Long> favoriteIds = favoriteService.getAllFavorites()
                .stream()
                .map(Movie::getId)
                .collect(Collectors.toSet());

        model.addAttribute("movies", movies);
        model.addAttribute("favoriteIds", favoriteIds);
        model.addAttribute("title", "Search Results for: " + query);
        model.addAttribute("searchQuery", query);
        model.addAttribute("httpServletRequest", request);
        return "movie-list";
    }

    // Movie details page
    @GetMapping("/movie/{id}")
    public String movieDetails(@PathVariable Long id,HttpServletRequest request, Model model) {
        MovieDto movie = tmdbService.getMovieDetails(id);
        boolean isFavorite = favoriteService.isFavorite(id);

        model.addAttribute("movie", movie);
        model.addAttribute("isFavorite", isFavorite);
        model.addAttribute("httpServletRequest", request);
        return "movie-details";
    }

    // Favorites page
    @GetMapping("/favorites")
    public String favorites(HttpServletRequest request,Model model) {
        List<Movie> favorites = favoriteService.getAllFavorites();
        model.addAttribute("favorites", favorites);
        model.addAttribute("httpServletRequest", request);
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
        } else if (redirect != null && redirect.equals("home")) {
            return "redirect:/";
        } else if (redirect != null && redirect.equals("topRated")) {
            return "redirect:/topRated";
        } else if (redirect != null && redirect.startsWith("search:")) {
            String query = redirect.substring(7);
            return "redirect:/search?query=" + query;
        }
        return "redirect:/favorites";
    }
}