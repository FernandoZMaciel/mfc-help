package br.com.mfc_help.handler;

import br.com.mfc_help.infra.exception.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException bre) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception. Check the Documentation")
                        .details(bre.getMessage())
                        .developerMessage(bre.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<EntityNotFoundExceptionDetails> handleEntityNotFoundException(EntityNotFoundException enfe) {
        return new ResponseEntity<>(
                EntityNotFoundExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .title("Entity Not Found. Check the Documentation")
                        .details(enfe.getMessage())
                        .developerMessage(enfe.getClass().getName())
                        .build(), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    public ResponseEntity<ValidationExceptionDetails> handleValidationException(MethodArgumentNotValidException manve) {
        List <FieldError> fieldErrors = manve.getBindingResult().getFieldErrors();

        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Validation Exception. Check the Documentation")
                        .details("Check the Fields Error")
                        .developerMessage(manve.getClass().getName())
                        .fields(fields)
                        .fieldsMessage(fieldsMessage)
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<UnauthorizedAccessExceptionDetails> handleUnauthorizedAccessException(UnauthorizedAccessException uae) {

        return new ResponseEntity<>(
                UnauthorizedAccessExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.FORBIDDEN.value())
                        .title("Unauthorized Exception. Check the Documentation")
                        .details("You violated Authorization")
                        .developerMessage(uae.getClass().getName())
                        .build(), HttpStatus.FORBIDDEN
        );
    }
}
