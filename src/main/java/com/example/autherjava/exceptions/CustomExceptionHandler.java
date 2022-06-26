package com.example.autherjava.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {
@ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ErrorResponse handlerNotFoundException(NotFoundException ex , WebRequest req) {
    return new ErrorResponse(HttpStatus.NOT_FOUND,ex.getMessage());
}
}
