package jfvb.com.pitangbackend.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidCredentialsException extends PitangBackendException {

    public InvalidCredentialsException() {
        super("Invalid login or password", 1);
    }
}
