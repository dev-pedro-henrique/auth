package br.com.pedro.auth.user.exception.handler;

import br.com.pedro.auth.user.exception.ExistsByUsernameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExistsByUsernameException.class)
    public ResponseEntity<Map<String, String>> handleExistsByUsernameException(ExistsByUsernameException ex) {
        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException ex) {
        return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}