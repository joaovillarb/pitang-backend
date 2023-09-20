package jfvb.com.pitangbackend.entrypoint.advices;

import jfvb.com.pitangbackend.core.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleException(Exception ex) {
        final Integer errorCode = extractErrorCode(ex);

        if (ex.getClass().isAnnotationPresent(ResponseStatus.class)) {
            ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
            final CustomErrorResponse response = new CustomErrorResponse(ex.getMessage(), errorCode);
            return ResponseEntity
                    .status(responseStatus.value())
                    .body(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new CustomErrorResponse(ex.getMessage(), errorCode));
    }

    private Integer extractErrorCode(Exception ex) {
        if (ex instanceof NotFoundException exception) {
            return exception.getErrorCode();
        } else {
            return null;
        }
    }

}
