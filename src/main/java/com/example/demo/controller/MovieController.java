package com.example.demo.controller;

import com.example.demo.DTO.MovieDTO;
import com.example.demo.model.Movie;
import com.example.demo.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {}

    @GetMapping("/movies")
    public List<MovieDTO> getMovies(){
        return movieService.getMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable long id){
        return movieService.getMovie(id);
    }


    @PostMapping("/movie")
    public ResponseEntity<?> addMovie(@RequestBody @Valid Movie movie) {
        Movie createdMovie = movieService.addMovie(movie);
        return ResponseEntity.ok(createdMovie);
    }



    @PutMapping("/edit/{id}")
    public ResponseEntity<Movie> editMovie(@PathVariable Long id , @RequestBody Movie movieDetails){
        Movie movie = movieService.updateMovie(id, movieDetails);
        return ResponseEntity.ok(movie);

    }
    @DeleteMapping("/{id}")
    public String removeMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
        return "movie has been deleted";
    }




}
