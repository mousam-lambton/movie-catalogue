package com.example.moviecatalogue.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "favorites")
public class Movie {
    @Id
    private Long id;
    private String title;

    // Movie overview is longer than 255 characters, so we set length to 2000
    @Column(length = 2000)
    private String overview;
    private String posterPath;
    private String backdropPath;
    private LocalDate releaseDate;
    private Double voteAverage;
    private Integer voteCount;
    private Integer runtime;
    private String tagline;

    @Column(length = 500)
    private String genres;

    // Default constructor required by JPA
    public Movie() {
    }

    public Movie(Long id, String title, String overview, String posterPath,
                 String backdropPath, LocalDate releaseDate, Double voteAverage,
                 Integer voteCount, Integer runtime, String tagline, String genres) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.runtime = runtime;
        this.tagline = tagline;
        this.genres = genres;
    }

    // Existing getters and setters
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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    // New getters and setters for the added properties
    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    // Helper methods
    public String getFullPosterPath() {
        return posterPath != null ? "https://image.tmdb.org/t/p/w500" + posterPath : null;
    }

    public String getFullBackdropPath() {
        return backdropPath != null ? "https://image.tmdb.org/t/p/original" + backdropPath : null;
    }

    public String getFormattedRuntime() {
        if (runtime == null) return null;
        int hours = runtime / 60;
        int minutes = runtime % 60;
        return hours > 0 ? hours + "h " + minutes + "m" : minutes + "m";
    }

    public String getReleaseDateStr() {
        return releaseDate != null ? releaseDate.toString() : null;
    }
}