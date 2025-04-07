package com.example.moviecatalogue.repository;

import com.example.moviecatalogue.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    // Spring Data JPA will automatically implement basic CRUD operations, so we don't need to define them here.
}