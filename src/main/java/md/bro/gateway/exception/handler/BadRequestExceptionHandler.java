package md.bro.gateway.exception.handler;

import md.bro.gateway.exception.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class BadRequestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex, WebRequest request) {
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(ex.getMessage(), httpHeaders, HttpStatus.BAD_REQUEST);
    }
}