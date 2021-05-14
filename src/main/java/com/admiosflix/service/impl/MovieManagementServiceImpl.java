package com.admiosflix.service.impl;

import com.admiosflix.dto.CreateMovieDTO;
import com.admiosflix.dto.MovieDTO;
import com.admiosflix.exceptions.DuplicatedMovieException;
import com.admiosflix.exceptions.InvalidMovieGenreException;
import com.admiosflix.exceptions.MovieNotFoundException;
import com.admiosflix.model.Movie;
import com.admiosflix.repository.GenreRepository;
import com.admiosflix.repository.MovieRepository;
import com.admiosflix.service.MovieManagementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovieManagementServiceImpl implements MovieManagementService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<Movie> getMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    public Page<Movie> getMoviesByGenre(String description, Pageable pageable) {
        return movieRepository.findByGenreDescriptionContainingIgnoreCase(description, pageable);
    }

    @Override
    public Page<Movie> getMoviesByGenre(Integer genreId, Pageable pageable) {
        return movieRepository.findByGenreId(genreId, pageable);
    }

    @Override
    public Movie getMovie(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
        movieRepository.deleteById(id);
    }

    @Override
    public Movie createMovie(CreateMovieDTO movieDTO) throws DuplicatedMovieException, InvalidMovieGenreException {
        if(movieRepository.findByTitle(movieDTO.getTitle()) != null) // Just to validate something
            throw new DuplicatedMovieException(movieDTO.getTitle());

        Movie movie = modelMapper.map(movieDTO, Movie.class);
        genreRepository.findById(movie.getGenre().getId()).orElseThrow(() -> new InvalidMovieGenreException(movie.getGenre().getDescription()));

        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(Long id, MovieDTO movieDTO) throws MovieNotFoundException {
        Movie existingMovie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
        modelMapper.map(movieDTO, existingMovie);

        genreRepository.findById(existingMovie.getGenre().getId()).orElseThrow(() -> new InvalidMovieGenreException(existingMovie.getGenre().getDescription()));

        return movieRepository.save(existingMovie);
    }

}
