package jfvb.com.pitangbackend.infrastructure.exception;

import jfvb.com.pitangbackend.core.exception.PitangBackendException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotLoggedInException extends PitangBackendException {

    public UserNotLoggedInException() {
        super("User is not logged in", 0);
    }
}
