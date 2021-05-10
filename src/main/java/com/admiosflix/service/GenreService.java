package com.admiosflix.service;

import com.admiosflix.model.Genre;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface GenreService {
    List<Genre> getGenres();
    Genre getGenreById(Long id);
}
