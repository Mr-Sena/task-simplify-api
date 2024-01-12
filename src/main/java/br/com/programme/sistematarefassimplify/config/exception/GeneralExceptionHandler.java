package br.com.programme.sistematarefassimplify.config.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity handlerBadRequest(BadRequestException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity handlerNotFound(NotFoundException exception) {
        return ResponseEntity.status(404).body(exception.getMessage());
    }
}
