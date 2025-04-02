package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
        // No content, just a 204 No Content response
    }

    @GetMapping("/movies")
    public List<Movie> getMovies(){
        return movieService.getMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable long id){
        return movieService.getMovie(id);
    }

    @PostMapping("/movie")
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.addMovie(movie);
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
