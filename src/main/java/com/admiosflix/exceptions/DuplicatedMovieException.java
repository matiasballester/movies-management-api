package com.admiosflix.exceptions;

public class DuplicatedMovieException extends RuntimeException {
    public DuplicatedMovieException(String title) {
        super("Duplicated movie with title " + title);
    }
}
