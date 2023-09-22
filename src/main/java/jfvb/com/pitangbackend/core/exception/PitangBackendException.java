package jfvb.com.pitangbackend.core.exception;

import lombok.Getter;

@Getter
public abstract class PitangBackendException extends RuntimeException {

    protected final Integer errorCode;

    protected PitangBackendException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
