package br.inf.ids.rh.core.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionFilter extends ResponseEntityExceptionHandler {
 
    @ExceptionHandler({ RestException.class })
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        return 
    		new ResponseEntity<Object>(
				ex.getMessage(),
				new HttpHeaders(),
				HttpStatus.NOT_FOUND);
        
    }
     
}