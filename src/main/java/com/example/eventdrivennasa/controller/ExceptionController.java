package com.example.eventdrivennasa.controller;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    public ResponseEntity<?> handleNoPicException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(ex.getMessage());
    }

}
