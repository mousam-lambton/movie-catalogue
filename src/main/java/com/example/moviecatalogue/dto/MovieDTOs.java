package com.example.moviecatalogue.dto;

import com.example.moviecatalogue.model.Movie;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class MovieDTOs {

    // DTO for popular movies response
    public static class PopularMoviesResponse {
        private int page;
        private List<MovieDto> results;

        @JsonProperty("total_pages")
        private int totalPages;

        @JsonProperty("total_results")
        private int totalResults;

        // Getters and Setters
        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public List<MovieDto> getResults() {
            return results;
        }

        public void setResults(List<MovieDto> results) {
            this.results = results;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(int totalResults) {
            this.totalResults = totalResults;
        }
    }

    // DTO for search movies response, same as identical structure to PopularMoviesResponse,
    public static class SearchMoviesResponse extends PopularMoviesResponse {
    }

    // DTO for individual movie data
    public static class MovieDto {
        private Long id;
        private String title;
        private String overview;

        @JsonProperty("poster_path")
        private String posterPath;

        @JsonProperty("release_date")
        private String releaseDateStr;

        @JsonProperty("vote_average")
        private Double voteAverage;

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getPosterPath() {
            return posterPath;
        }

        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }

        public String getReleaseDateStr() {
            return releaseDateStr;
        }

        public void setReleaseDateStr(String releaseDateStr) {
            this.releaseDateStr = releaseDateStr;
        }

        public Double getVoteAverage() {
            return voteAverage;
        }

        public void setVoteAverage(Double voteAverage) {
            this.voteAverage = voteAverage;
        }

        // Helper method to get the full poster URL
        public String getFullPosterPath() {
            return posterPath != null ? "https://image.tmdb.org/t/p/w500" + posterPath : null;
        }

        // Convert DTO to entity
        public Movie toEntity() {
            LocalDate releaseDate = null;
            if (releaseDateStr != null && !releaseDateStr.isEmpty()) {
                try {
                    releaseDate = LocalDate.parse(releaseDateStr, DateTimeFormatter.ISO_DATE);
                } catch (DateTimeParseException e) {
                    // Handle parse exception
                }
            }

            return new Movie(
                    id,
                    title,
                    overview,
                    posterPath,
                    releaseDate,
                    voteAverage
            );
        }
    }
}