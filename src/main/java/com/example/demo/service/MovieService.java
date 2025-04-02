package com.example.demo.service;

import com.example.demo.exception.InvalidRequestException;
import com.example.demo.exception.MovieNotFoundException;
import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieRepository movieRepository;


    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }


    public  Movie getMovie(long id){
        if (id <= 0) {
            throw new InvalidRequestException("Invalid movie ID provided");
        }

        Movie movie = movieRepository.findById(id).orElseThrow(() ->  new MovieNotFoundException(String.format("No movie found with ID %d.", id)));
        return movie;
    }

    public  Movie addMovie(Movie movie){
        logger.info("Creating movie: {}", movie.getTitle());

        Movie savedMovie = movieRepository.save(movie);
        logger.info("Movie created with ID: {}", savedMovie.getId());
        return savedMovie;
    }

    public Movie updateMovie(long id , Movie moviedetails){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        movie.setDirector(moviedetails.getDirector());
        movie.setReleaseYear(moviedetails.getReleaseYear());
        return movieRepository.save(movie);
    }

    public void deleteMovie(long id){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));

         movieRepository.delete(movie);
    }



}
