package org.example.yy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccessExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResult> handleException(Exception exception) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResult("100000", "internal server error!"));
    }


    @ExceptionHandler(AccessException.class)
    public ResponseEntity<ExceptionResult> handleAccessException(AccessException accessException) {
        ExceptionResult result = new ExceptionResult(accessException.getErrorCode(), accessException.getMessage());
        return switch (accessException.getErrorCode()) {
            case "100006" -> ResponseEntity.status(401).body(result);
            case "100001", "100002" -> ResponseEntity.status(404).body(result);
            case "100003", "100005" -> ResponseEntity.status(400).body(result);
            default -> ResponseEntity.status(500).body(result);
        };
    }

}
