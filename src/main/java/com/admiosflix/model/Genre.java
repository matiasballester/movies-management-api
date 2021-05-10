package com.admiosflix.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity(name="Genre")
@Table(name="genre")
public class Genre {
    @Id
    @GeneratedValue(generator = "genre-sequence-generator")
    @GenericGenerator(
            name = "genre-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "genre_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "15"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private long id;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

}
