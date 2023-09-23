package jfvb.com.pitangbackend.infrastructure.exception;

import jfvb.com.pitangbackend.core.exception.PitangBackendException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtTokenException extends PitangBackendException {

    public JwtTokenException(String errorToGenerateTokenJwt, Integer errorCode) {
        super(errorToGenerateTokenJwt, errorCode);
    }

    public JwtTokenException(String errorToGenerateTokenJwt) {
        super(errorToGenerateTokenJwt, 0);
    }
}
