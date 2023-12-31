package red.bus.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import red.bus.user.payload.ErrorDetails;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFound e, WebRequest wq) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),e.getMessage(),wq.getDescription(true));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

   @ExceptionHandler(Exception.class)
   public ResponseEntity<ErrorDetails> handleAllException(Exception e, WebRequest wq) {
      ErrorDetails errorDetails = new ErrorDetails(new Date(),e.getMessage(),wq.getDescription(true));
     return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
   }
}
