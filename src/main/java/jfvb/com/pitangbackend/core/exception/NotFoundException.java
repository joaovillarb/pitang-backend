package jfvb.com.pitangbackend.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private final Integer errorCode;

    public NotFoundException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
