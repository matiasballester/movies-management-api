package com.admiosflix.service.impl;

import com.admiosflix.exceptions.InvalidMovieGenreException;
import com.admiosflix.model.Genre;
import com.admiosflix.repository.GenreRepository;
import com.admiosflix.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired private GenreRepository genreRepository;

    @Override
    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getGenreById(Long id) {
        return genreRepository.findById(id).orElseThrow(()-> new InvalidMovieGenreException("Genre not found"));
    }
}
