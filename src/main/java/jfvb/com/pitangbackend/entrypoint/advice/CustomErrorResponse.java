package jfvb.com.pitangbackend.entrypoint.advice;

public record CustomErrorResponse(
        String message,
        Integer errorCode) {
}
