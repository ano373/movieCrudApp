package com.example.demo.mapper;

import com.example.demo.DTO.MovieDTO;
import com.example.demo.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDTO toDTO(Movie movie);
    Movie toEntity(MovieDTO dto);
}
