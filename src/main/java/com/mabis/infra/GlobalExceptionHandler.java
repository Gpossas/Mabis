package com.mabis.infra;

import com.mabis.exceptions.DishTypeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(DishTypeNotFoundException.class)
    private ResponseEntity<ErrorResponse> dish_type_not_found_exception_handler(DishTypeNotFoundException exception)
    {
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(response, response.getHttp_status());
    }
}
