package com.admiosflix.service;

import com.admiosflix.dto.CreateMovieDTO;
import com.admiosflix.dto.MovieDTO;
import com.admiosflix.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface MovieManagementService {
    Page<Movie> getMovies(Pageable pageable);
    Page<Movie> getMoviesByGenre(String description, Pageable pageable);
    Page<Movie> getMoviesByGenre(Integer genreId, Pageable pageable);
    Movie getMovie(Long id);
    void deleteMovie(Long id) throws Exception;
    Movie createMovie(CreateMovieDTO movie);
    Movie updateMovie(Long id, MovieDTO movie);
}
