package com.example.demo.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "movies")
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @NotNull(message =  "movie must have a director")
    private String director;

    private String genre;

    @Min(value = 1000, message = "release Year must be 4 digits")
    private Integer releaseYear;

    private Double rating;


}

