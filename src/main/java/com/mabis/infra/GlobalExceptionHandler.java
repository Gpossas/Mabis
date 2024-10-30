package com.mabis.infra;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.mabis.exceptions.*;
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

    @ExceptionHandler(MultipartAttachmentException.class)
    private ResponseEntity<ErrorResponse> get_bytes_exception_handler(MultipartAttachmentException exception)
    {
        ErrorResponse response = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage());
        return new ResponseEntity<>(response, response.getHttp_status());
    }

    @ExceptionHandler(QRCodeEncodeException.class)
    private ResponseEntity<ErrorResponse> qrcode_encode_exception_handler(QRCodeEncodeException exception)
    {
        ErrorResponse response = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage());
        return new ResponseEntity<>(response, response.getHttp_status());
    }

    @ExceptionHandler(QRCodeToBytesException.class)
    private ResponseEntity<ErrorResponse> qrcode_to_bytes_exception_handler(QRCodeToBytesException exception)
    {
        ErrorResponse response = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage());
        return new ResponseEntity<>(response, response.getHttp_status());
    }

    @ExceptionHandler(NotActiveTableException.class)
    private ResponseEntity<ErrorResponse> table_not_active_exception_handler(NotActiveTableException exception)
    {
        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT, exception.getMessage());
        return new ResponseEntity<>(response, response.getHttp_status());
    }

    @ExceptionHandler(JWTCreationException.class)
    private ResponseEntity<ErrorResponse> jwt_creation_exception_handler(JWTCreationException exception)
    {
        ErrorResponse response = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage());
        return new ResponseEntity<>(response, response.getHttp_status());
    }

    @ExceptionHandler(UserEmailAlreadyInUse.class)
    public ResponseEntity<ErrorResponse> email_in_use_exception_handler(UserEmailAlreadyInUse exception)
    {
        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT, exception.getMessage());
        return new ResponseEntity<>(response, response.getHttp_status());
    }

    @ExceptionHandler(UnmatchPassword.class)
    public ResponseEntity<ErrorResponse> unmatch_password_exception_handler(UnmatchPassword exception)
    {
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
        return new ResponseEntity<>(response, response.getHttp_status());
    }
}
