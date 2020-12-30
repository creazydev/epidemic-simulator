package com.github.epidemicsimulator.adapter.in.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor
@Getter
public class ApiError {
    private final HttpStatus status;
    private final String message;
    private final List<String> errors;
}
