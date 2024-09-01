package com.davidnguyen.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CustomErrorResponse> handleResponseStatusException(ResponseStatusException ex,
                                                                             WebRequest request) {

        CustomErrorResponse errorResponse = new CustomErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(ex.getStatusCode().value());

        // Tách phần tên trạng thái từ chuỗi toString(), ví dụ: "409 CONFLICT" -> "CONFLICT"
        String error = ex.getStatusCode().toString();
        String reasonPhrase = error.substring(error.indexOf(" ") + 1);

        errorResponse.setError(reasonPhrase);
        errorResponse.setMessage(ex.getReason());
        errorResponse.setPath(request.getDescription(false).substring(4)); // Remove "uri=" prefix

        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    // You can add more handlers for other exceptions
}
