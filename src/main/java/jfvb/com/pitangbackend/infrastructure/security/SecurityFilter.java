package jfvb.com.pitangbackend.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jfvb.com.pitangbackend.entrypoint.advice.CustomErrorResponse;
import jfvb.com.pitangbackend.infrastructure.exception.JwtTokenException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public void doFilterInternal(@NonNull HttpServletRequest request,
                                 @NonNull HttpServletResponse response,
                                 @NonNull FilterChain filterChain) throws ServletException, IOException {
        final var authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final var token = authHeader.substring(7);

        try {
            var subject = jwtService.extractUsername(token);

            UserDetails userDetails = userDetailsService.loadUserByUsername(subject);

            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (JwtTokenException e) {
            handleJwtTokenException(response, e);
        }

    }

    private void handleJwtTokenException(HttpServletResponse response, JwtTokenException e) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        CustomErrorResponse errorResponse = new CustomErrorResponse(e.getMessage(), e.getErrorCode());
        new ObjectMapper().writeValue(response.getWriter(), errorResponse);
    }

}
