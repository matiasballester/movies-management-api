package com.admiosflix.repository;

import com.admiosflix.model.Genre;
import com.admiosflix.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title);
    Page<Movie> findByGenre(Genre genre, Pageable pageable);
    Page<Movie> findByGenreDescriptionContainingIgnoreCase(String description, Pageable pageable);
}