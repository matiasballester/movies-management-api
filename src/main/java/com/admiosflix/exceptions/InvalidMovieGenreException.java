package com.admiosflix.exceptions;

public class InvalidMovieGenreException extends RuntimeException {
    public InvalidMovieGenreException(String genreDescription) {
        super("Invalid movie genre id " + genreDescription);
    }
}
