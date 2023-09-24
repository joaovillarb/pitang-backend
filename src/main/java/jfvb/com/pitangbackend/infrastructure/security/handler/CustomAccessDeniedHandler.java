package jfvb.com.pitangbackend.infrastructure.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jfvb.com.pitangbackend.entrypoint.advice.CustomErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        mapper.writeValue(response.getWriter(), new CustomErrorResponse("Unauthorized", 1));
    }
}
