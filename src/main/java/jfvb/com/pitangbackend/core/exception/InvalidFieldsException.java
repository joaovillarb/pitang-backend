package jfvb.com.pitangbackend.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFieldsException extends RuntimeException {

    private final Integer errorCode;

    public InvalidFieldsException(String message) {
        super(message);
        this.errorCode = 4;
    }
}
