package com.example.demo.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieDTO {
    private Long id;
    private String title;
    private String director;
    private String genre;
    private Integer releaseYear;
    private Double rating;
}


