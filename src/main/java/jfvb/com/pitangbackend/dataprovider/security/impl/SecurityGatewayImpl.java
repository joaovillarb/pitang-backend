package jfvb.com.pitangbackend.dataprovider.security.impl;

import jfvb.com.pitangbackend.core.exception.InvalidCredentialsException;
import jfvb.com.pitangbackend.core.gateway.AccountUserGateway;
import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import jfvb.com.pitangbackend.dataprovider.security.SecurityGateway;
import jfvb.com.pitangbackend.entrypoint.dto.AccountUserDto;
import jfvb.com.pitangbackend.entrypoint.dto.AuthenticationRequest;
import jfvb.com.pitangbackend.entrypoint.dto.AuthenticationResponse;
import jfvb.com.pitangbackend.entrypoint.dto.CarDto;
import jfvb.com.pitangbackend.infrastructure.security.JwtService;
import jfvb.com.pitangbackend.infrastructure.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class SecurityGatewayImpl implements SecurityGateway {

    private final AccountUserGateway accountUserGateway;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            final var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.login(),
                            request.password()
                    )
            );

            var user = (AccountUser) authentication.getPrincipal();
            final var token = jwtService.generateToken(user);

            user.setLastLogin(LocalDateTime.now());
            accountUserGateway.save(user);

            return new AuthenticationResponse(token);
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException();
        }
    }

    public AccountUserDto me() {
        AccountUser accountUser = SecurityUtils.getLoggedInUserId();
        return new AccountUserDto(
                accountUser.getId(),
                accountUser.getFirstName(),
                accountUser.getLastName(),
                accountUser.getEmail(),
                accountUser.getBirthday(),
                accountUser.getLogin(),
                null,
                accountUser.getPhone(),
                accountUser.getCars().stream()
                        .map(CarDto::new)
                        .toList(),
                accountUser.getCreatedAt(),
                accountUser.getLastLogin(),
                accountUser.getActive()
        );
    }
}
