package com.example.demo.mapper;

import com.example.demo.DTO.MovieDTO;
import com.example.demo.model.Movie;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieMapperTest {
    private final MovieMapper movieMapper = Mappers.getMapper(MovieMapper.class);

    @Test
    void toDTO() {
        Movie movie = new Movie(1L, "Inception", "Nolan", "Sci-Fi", 2010, 8.8);

        MovieDTO dto = movieMapper.toDTO(movie);

        assertEquals(movie.getId(), dto.getId());
        assertEquals(movie.getTitle(), dto.getTitle());
        assertEquals(movie.getDirector(), dto.getDirector());
        assertEquals(movie.getGenre(), dto.getGenre());
        assertEquals(movie.getReleaseYear(), dto.getReleaseYear());
        assertEquals(movie.getRating(), dto.getRating(), 0.01);
    }

    @Test
    void toEntity(){
        MovieDTO dto = new MovieDTO(1L, "Inception","Nolan","Sci-Fi", 2010, 8.8);

        Movie movie = movieMapper.toEntity(dto);
        assertEquals(dto.getId(),movie.getId());
        assertEquals(dto.getTitle(),movie.getTitle());
        assertEquals(dto.getDirector(),movie.getDirector());
        assertEquals(dto.getGenre(),movie.getGenre());
        assertEquals(dto.getReleaseYear(),movie.getReleaseYear());
        assertEquals(dto.getRating(),movie.getRating());


    }


}



