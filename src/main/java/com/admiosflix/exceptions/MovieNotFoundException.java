package com.admiosflix.exceptions;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(Long id) {
        super("Could not found movie id " + id);
    }
}
