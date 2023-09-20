package jfvb.com.pitangbackend.entrypoint.advices;

public record CustomErrorResponse(
        String message,
        Integer errorCode) {
}
