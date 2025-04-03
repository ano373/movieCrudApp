package com.example.demo.exception;

import java.util.List;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ErrorDetails {
    private String error ;
    private List<String> messages = new ArrayList<>();
}
