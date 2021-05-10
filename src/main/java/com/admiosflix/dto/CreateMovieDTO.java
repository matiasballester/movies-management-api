package com.admiosflix.dto;

import com.admiosflix.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class CreateMovieDTO {
    @NotNull(message = "Title is required")
    @Size(message = "Title max length should be 150 characters", max = 150)
    protected String title;
    protected String crew;
    protected String cast;
    @Min(value = 0, message = "Rating could not be less than 0")
    @Max(value = 10, message = "Rating could not be greater than 10")
    protected Double rating;
    @NotNull(message = "Release Date is required")
    protected Date releaseDate;
    protected Genre genre;
}
