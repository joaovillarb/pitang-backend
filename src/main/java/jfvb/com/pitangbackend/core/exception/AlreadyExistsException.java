package jfvb.com.pitangbackend.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends RuntimeException {

    private final Integer errorCode;

    public AlreadyExistsException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
