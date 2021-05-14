package com.admiosflix.controllers;

import com.admiosflix.dto.CreateMovieDTO;
import com.admiosflix.dto.ResponseDTO;
import com.admiosflix.dto.MovieDTO;
import com.admiosflix.exceptions.MovieNotFoundException;
import com.admiosflix.model.Movie;
import com.admiosflix.service.GenreService;
import com.admiosflix.service.MovieManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/movies-management-api/v1/")
@Validated
public class MoviesManagementController {
    private static Logger LOGGER = LoggerFactory.getLogger(MoviesManagementController.class);

    @Autowired
    private MovieManagementService movieManagementService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/movies")
    @Parameters({
            @Parameter(name = "genreDescription", description = "genre description",
                    in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "page", description = "page number",
                    in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "size", description = "page size",
                    in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "sort", description = "sort specification",
                    in = ParameterIn.QUERY, schema = @Schema(type = "string"))
    })
    public ResponseEntity getAllMovies(@RequestParam(required = false) String genreDescription, @Parameter(hidden = true) @PageableDefault(sort = "title", direction = Sort.Direction.ASC) Pageable pageable) {
        try {
            Page<Movie> pageMovie = (genreDescription == null) ?
                                        movieManagementService.getMovies(pageable) :
                                            movieManagementService.getMoviesByGenre(genreDescription, pageable);
            Page<MovieDTO> pageMovieDTO = pageMovie.map(movie -> modelMapper.map(movie, MovieDTO.class));
            return ResponseEntity.status(HttpStatus.OK).body(pageMovieDTO);
        } catch (Exception e) {
            LOGGER.error("Failed to load all movies");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.builder().message(e.getMessage()).build());
        }
    }

    @GetMapping(value = "/movies/genre/{genre-id}")
    @Parameters({
            @Parameter(name = "genre-id", description = "genre id",
                    in = ParameterIn.PATH, schema = @Schema(type = "string")),
            @Parameter(name = "page", description = "page number",
                    in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "size", description = "page size",
                    in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "sort", description = "sort specification",
                    in = ParameterIn.QUERY, schema = @Schema(type = "string"))
    })
    public ResponseEntity findMoviesByGenre(@PathVariable(name = "genre-id") Integer genreId, @Parameter(hidden = true) @PageableDefault(sort = "title", direction = Sort.Direction.ASC) Pageable pageable) {
        try {
            Page<Movie> pageMovie = movieManagementService.getMoviesByGenre(genreId, pageable);
            Page<MovieDTO> pageMovieDTO = pageMovie.map(movie -> modelMapper.map(movie, MovieDTO.class));
            return ResponseEntity.status(HttpStatus.OK).body(pageMovieDTO);
        } catch (Exception e) {
            LOGGER.error("Failed to load all movies");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/movies/{movie-id}")
    public ResponseEntity getMovie(@PathVariable(name = "movie-id") @NotNull Long id) {
        try {
            Movie movie = movieManagementService.getMovie(id);
            return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(movie, MovieDTO.class));
        } catch (MovieNotFoundException e) {
            LOGGER.error("Failed to update the movie", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.builder().objectId(id).message(e.getMessage()).build());
        } catch (Exception e) {
            LOGGER.error("Failed to get movie by id: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.builder().message(e.getMessage()).build());
        }
    }

    @Operation(summary = "Create movie", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/movies")
    public ResponseEntity createMovie(@Validated @RequestBody CreateMovieDTO movieDTO) {
        try {
            Movie movie = movieManagementService.createMovie(movieDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDTO.builder().objectId(movie.getMovieId()).message("Movie successfully created").build());
        } catch (Exception e) {
            LOGGER.error("Failed to create the movie");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.builder().message(e.getMessage()).build());
        }
    }

    @Operation(summary = "Update movie", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "/movies/{movie-id}")
    public ResponseEntity updateMovie(@PathVariable(required = false, name = "movie-id") Long id, @NotNull @RequestBody MovieDTO movieDTO) {
        try {
            Movie movie = movieManagementService.updateMovie(id, movieDTO);
            ResponseDTO responseDTO = ResponseDTO.builder().objectId(movie.getMovieId()).message("Movie successfully updated").build();
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (MovieNotFoundException e) {
            LOGGER.error("Failed to update the movie", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.builder().objectId(id).message(e.getMessage()).build());
        } catch (Exception e) {
            LOGGER.error("Failed to update the movie", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.builder().objectId(id).message("Failed to update the movie").build());
        }
    }

    @Operation(summary = "Delete movie", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping(value = "/movies/{movie-id}")
    public ResponseEntity deleteMovie(@NotNull @PathVariable(name = "movie-id") Long id) {
        try {
            movieManagementService.deleteMovie(id);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder().message("Movie successfully deleted").build());
        } catch (MovieNotFoundException e) {
            LOGGER.error("Failed to delete the movie", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.builder().objectId(id).message(e.getMessage()).build());
        } catch (Exception e) {
            LOGGER.error("Failed to delete the movie", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.builder().objectId(id).message("Failed to delete movie").build());
        }
    }

    @GetMapping(value = "/genres/")
    public ResponseEntity getGenres() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(genreService.getGenres());
        } catch (Exception e) {
            LOGGER.error("Failed to load all genres");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDTO.builder().message(e.getMessage()).build());
        }
    }
}
