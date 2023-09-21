package jfvb.com.pitangbackend.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnauthorizedException extends PitangBackendException {

    public UnauthorizedException(String message, Integer errorCode) {
        super(message, errorCode);
    }
}
