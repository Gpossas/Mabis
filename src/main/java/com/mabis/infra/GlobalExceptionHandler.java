package com.mabis.infra;

import com.mabis.exceptions.ActiveTableException;
import com.mabis.exceptions.DishTypeNotFoundException;
import com.mabis.exceptions.InvalidStorageServiceException;
import com.mabis.exceptions.TableNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(InvalidStorageServiceException.class)
    private ResponseEntity<ErrorResponse> invalid_storage_service_exception_handler(InvalidStorageServiceException exception)
    {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(response, response.getHttp_status());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<MultipleErrorsResponse> invalid_method_argument_exception(MethodArgumentNotValidException exception)
    {
        MultipleErrorsResponse response = new MultipleErrorsResponse(HttpStatus.BAD_REQUEST, exception.getFieldErrors());
        return new ResponseEntity<>(response, response.getHttp_status());
    }

    @ExceptionHandler(TableNotFoundException.class)
    private ResponseEntity<ErrorResponse> table_not_found_exception_handler(TableNotFoundException exception)
    {
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(response, response.getHttp_status());
    }

    @ExceptionHandler(ActiveTableException.class)
    private ResponseEntity<ErrorResponse> modify_active_table_exception_handler(ActiveTableException exception)
    {
        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT, exception.getMessage());
        return new ResponseEntity<>(response, response.getHttp_status());
    }
}
