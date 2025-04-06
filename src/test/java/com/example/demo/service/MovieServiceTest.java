package com.example.demo.service;

import com.example.demo.DTO.MovieDTO;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.exception.MovieNotFoundException;
import com.example.demo.mapper.MovieMapper;
import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private MovieMapper movieMapper;


    @InjectMocks
    private MovieService movieService;


    private Movie  movie;
    private MovieDTO movieDTOExpected;


    @BeforeEach
    void SetupMovie(){
         movieDTOExpected = new MovieDTO(1L,"Inception", "Nolan", "Sc-Fi", 2010,8.8);
         movie =  new Movie(1L,"Inception","Nolan","Sc-Fi",2010,8.8);
    }

    @Test
    @DisplayName("getAllMovies")
    void getAllMovies_ShouldReturnMovieDToList(){
        List<Movie> movies = List.of(
                new Movie(1L, "Inception", "Nolan", "Sci-Fi", 2010, 8.8),
                new Movie(2L, "The Matrix", "Wachowskis", "Sci-Fi", 1999, 8.7)
        );

        List<MovieDTO> expectedDTOs = List.of(
                new MovieDTO(1L, "Inception", "Nolan", "Sci-Fi", 2010, 8.8),
                new MovieDTO(2L, "The Matrix", "Wachowskis", "Sci-Fi", 1999, 8.7)
        );
        when(movieRepository.findAll()).thenReturn(movies);
        when(movieMapper.toDTO(movies.get(0))).thenReturn(expectedDTOs.get(0));
        when(movieMapper.toDTO(movies.get(1))).thenReturn(expectedDTOs.get(1));

        // Act
        List<MovieDTO> result = movieService.getMovies();

        // Assert
        assertEquals(expectedDTOs, result); // Verify correct DTO list
        verify(movieRepository).findAll(); // Verify repository call
        verify(movieMapper, times(2)).toDTO(any(Movie.class)); // Verify mapper called twice
    }

    @Test
    void AddMovie_ShouldReturnTheNewMovie(){
        Movie inputMovie = new Movie(null, "Inception", "Nolan", "Sci-Fi", 2010, 8.8);
        when(movieRepository.save(inputMovie)).thenReturn(movie);
        when(movieMapper.toDTO(movie)).thenReturn(movieDTOExpected);

        // Act
        MovieDTO result = movieService.addMovie(inputMovie);

        // Assert
        assertEquals(movieDTOExpected, result);
        verify(movieRepository).save(inputMovie);
        verify(movieMapper).toDTO(movie);
    }

    @Test
    void UpdateMovie_ShouldReturnTheNewMovieDetails(){
        Movie updatedMovie = new Movie(1L, "Inception", "medo", "Sci-Fi", 2050, 8.8);
        long id = 1L;

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        when(movieRepository.save(movie)).thenReturn(updatedMovie);

       Movie result =  movieService.updateMovie(id,updatedMovie);

        assertEquals(updatedMovie.getDirector(), result.getDirector());
        assertEquals(updatedMovie.getReleaseYear(), result.getReleaseYear());

        verify(movieRepository).findById(id);
        verify(movieRepository).save(movie);
    }
    @Test
    @DisplayName("deleteMovie should delete movie when ID exists")
    void deleteMovie_ShouldDelete_WhenMovieExists() {
        // Arrange
        long id = 1L;
        Movie movie = new Movie(id, "Inception", "Nolan", "Sci-Fi", 2010, 8.8);

        when(movieRepository.findById(id)).thenReturn(Optional.of(movie));
        doNothing().when(movieRepository).delete(movie);

        // Act
        movieService.deleteMovie(id);

        // Assert
        verify(movieRepository).findById(id);
        verify(movieRepository).delete(movie);
    }
    @Test
    @DisplayName("deleteMovie should throw when movie doesn't exist")
    void deleteMovie_ShouldThrow_WhenMovieNotFound() {
        // Arrange
        long id = 999L;

        when(movieRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MovieNotFoundException.class,
                () -> movieService.deleteMovie(id));

        verify(movieRepository).findById(id);
        verifyNoMoreInteractions(movieRepository); // Ensure delete() isn't called
    }



    @Test
    @DisplayName("getMovie")
    void getMovieById_ShouldReturnMovieDTO_WhenMovieExists() {
        // Arrange

        long movieId = 1L;
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(movieMapper.toDTO(movie)).thenReturn(movieDTOExpected);

        // Act
        MovieDTO resultDTO = movieService.getMovie(movieId);

        verify(movieRepository).findById(movieId);
        verify(movieMapper).toDTO(movie);


        assertEquals(movieDTOExpected, resultDTO);

    }
    @Test
    @DisplayName("getmovie - InvalidRequestException")
    void getMovie_ThrowsNotFound_WhenMovieMissing() {
        long validId = 2L;
        when(movieRepository.findById(validId)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> movieService.getMovie(validId));
        verify(movieRepository).findById(validId); // Ensure repository is called
        verifyNoInteractions(movieMapper); // Ensure mapper is not invoked
    }
    @Test
    @DisplayName("getMovie - MovieNotFoundException")
    void getMovie_ThrowsInvalidRequest_WhenIdIsInvalid() {
        long invalidId = 0L;
        assertThrows(InvalidRequestException.class, () -> movieService.getMovie(invalidId));
        verifyNoInteractions(movieRepository); // Ensure no repository call is made
    }
}

