package jfvb.com.pitangbackend.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingFieldsException extends PitangBackendException {

    public MissingFieldsException(String message) {
        super(message, 5);
    }

}
