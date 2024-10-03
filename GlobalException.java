package blogapp_api.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import blogapp_api.payload.Apiresponse;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Apiresponse> responseNotFoundExceptionHandler(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        Apiresponse api = new Apiresponse(message, false);
        return new ResponseEntity<>(api, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> rsp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String defaultMessage = error.getDefaultMessage();
            rsp.put(field, defaultMessage);
        });
        return new ResponseEntity<>(rsp, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Apiresponse> DisabledExceptionHandler(DisabledException ex) {
        String message = ex.getMessage();
        Apiresponse api = new Apiresponse(message, false);
        return new ResponseEntity<>(api, HttpStatus.BAD_REQUEST);
    }
}
