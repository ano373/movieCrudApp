package com.example.demo.mapper;

import com.example.demo.DTO.MovieDTO;
import com.example.demo.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    public MovieDTO toDTO(Movie movie){
        return new MovieDTO(
                movie.getTitle(),
                (movie.getDirector() != null ? movie.getDirector() : "Unknown Director"),
                ( movie.getReleaseYear() != null && movie.getReleaseYear() >= 1000 ? movie.getReleaseYear() : 2000)
        );
    }
}
