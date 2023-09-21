package jfvb.com.pitangbackend.entrypoint.advices;

import jfvb.com.pitangbackend.core.exception.PitangBackendException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(final Exception ex) {
        final Integer errorCode = extractErrorCode(ex);
        log.error(ex.getMessage(), ex);

        if (ex.getClass().isAnnotationPresent(ResponseStatus.class)) {
            ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
            final CustomErrorResponse response = new CustomErrorResponse(ex.getMessage(), errorCode);
            return ResponseEntity
                    .status(responseStatus.value())
                    .body(response);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CustomErrorResponse(ex.getMessage(), errorCode));
    }

    private Integer extractErrorCode(Exception ex) {
        if (ex instanceof PitangBackendException exception) {
            return exception.getErrorCode();
        }

        return null;
    }
}
