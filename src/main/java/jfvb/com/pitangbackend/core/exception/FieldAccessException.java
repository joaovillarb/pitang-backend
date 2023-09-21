package jfvb.com.pitangbackend.core.exception;

public class FieldAccessException extends RuntimeException {

    public FieldAccessException(String fieldName, Throwable cause) {
        super("Error accessing field: " + fieldName, cause);
    }

}

