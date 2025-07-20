package com.springBoot.rural_reach.exceptionHandler;

import com.springBoot.rural_reach.dto.JwtResponseDto;
import com.springBoot.rural_reach.exceptions.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex, HttpServletRequest req) {
        ErrorResponse error = new ErrorResponse(
                Instant.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error",
                ex.getMessage(), req.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle validation failures
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fe -> fieldErrors.put(fe.getField(), fe.getDefaultMessage()));

        ErrorResponse error = new ErrorResponse(
                Instant.now(), HttpStatus.BAD_REQUEST.value(), "Validation Failed",
                fieldErrors.toString(), req.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Handle duplicate-user during register/login via JWT DTO
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<JwtResponseDto> handleUserExists(UserAlreadyExistsException ex) {
        JwtResponseDto resp = JwtResponseDto.builder()
                .accessToken(null)
                .refreshToken(null)
                .error(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(resp);
    }

    // Helper error response class
    @lombok.AllArgsConstructor
    @lombok.Getter
    @lombok.Setter
    public static class ErrorResponse {
        private Instant timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
    }
}

