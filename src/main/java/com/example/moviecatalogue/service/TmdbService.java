package com.example.moviecatalogue.service;

import com.example.moviecatalogue.dto.MovieDTOs.MovieDto;
import com.example.moviecatalogue.dto.MovieDTOs.PopularMoviesResponse;
import com.example.moviecatalogue.dto.MovieDTOs.SearchMoviesResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
public class TmdbService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // Get popular movies
    public List<MovieDto> getPopularMovies() {
        String uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/movie/popular")
                .queryParam("api_key", apiKey)
                .toUriString();

        PopularMoviesResponse response = restTemplate.getForObject(uri, PopularMoviesResponse.class);
        return response != null ? response.getResults() : Collections.emptyList();
    }

    // Search movies by title
    public List<MovieDto> searchMovies(String query) {
        String uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/search/movie")
                .queryParam("api_key", apiKey)
                .queryParam("query", query)
                .toUriString();

        SearchMoviesResponse response = restTemplate.getForObject(uri, SearchMoviesResponse.class);
        return response != null ? response.getResults() : Collections.emptyList();
    }

    // Get movie details by ID
    public MovieDto getMovieDetails(Long movieId) {
        String uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/movie/" + movieId)
                .queryParam("api_key", apiKey)
                .toUriString();

        return restTemplate.getForObject(uri, MovieDto.class);
    }
}