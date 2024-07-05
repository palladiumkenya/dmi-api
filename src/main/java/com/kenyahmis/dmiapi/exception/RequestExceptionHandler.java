package com.kenyahmis.dmiapi.exception;

import com.kenyahmis.dmiapi.model.APIErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger LOG = LoggerFactory.getLogger(RequestExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        logErrorMessage(errors);
        return new ResponseEntity<>(new APIErrorResponse<>(errors, "Request validation error"),
                HttpStatus.BAD_REQUEST);
    }

    private void logErrorMessage(Map<String, String> errors) {
        LOG.error("==================START Validation errors==================");
        for (String field : errors.keySet()) {
            LOG.error("{}: {}", field, errors.get(field));
        }
        LOG.error("==================END Validation errors==================");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("unreadableField", ex.getMessage());
        return new ResponseEntity<>(new APIErrorResponse<>(error, "Invalid Request"), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected APIErrorResponse<?> handleException(Exception ex) {
        ex.printStackTrace();
        return new APIErrorResponse<>(null, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeParseException.class)
    protected APIErrorResponse<?> handleDateTimeParseException(DateTimeParseException ex,  WebRequest request) {
        return new APIErrorResponse<>(null, ex.getMessage());
    }
}
