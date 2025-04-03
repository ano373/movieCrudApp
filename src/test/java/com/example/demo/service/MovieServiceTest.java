package com.example.demo.service;

import com.example.demo.DTO.MovieDTO;
import com.example.demo.exception.MovieNotFoundException;
import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    void getMovieById_ShouldReturnMovieDTO_WhenMovieExists() {
        // Arrange
        Long movieId = 1L;
        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setTitle("Inception");
        movie.setDirector("andrew");
        movie.setGenre("Sc-Fi");
        movie.setReleaseYear(2010);
        movie.setRating(8.4);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        // Act
        Movie result = movieService.getMovie(movieId);

        // Assert
        assertEquals("Inception", result.getTitle());
        assertEquals("andrew", result.getDirector());
        assertEquals("Sc-Fi", result.getGenre());
        assertEquals(2010, result.getReleaseYear());
        assertEquals(8.4, result.getRating(), 0.01); // Allow small margin for floating-point comparison

    }

    @Test
    void getMovieById_ShouldThrowException_WhenMovieNotFound() {
        Long movieId = 99L;
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> movieService.getMovie(movieId));
    }
}

