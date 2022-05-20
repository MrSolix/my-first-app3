package eu.senla.dutov.handler;

import eu.senla.dutov.exception.IncorrectValueException;
import eu.senla.dutov.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IncorrectValueException.class)
    public ResponseEntity<?> handleIncorrectValueException(IncorrectValueException incorrectValueException) {
        return ResponseEntity.badRequest().body(incorrectValueException.getMessage());
    }
}
