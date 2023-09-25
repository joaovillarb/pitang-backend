package jfvb.com.pitangbackend.dataprovider.security.impl;

import jfvb.com.pitangbackend.BaseUnitTest;
import jfvb.com.pitangbackend.core.exception.InvalidCredentialsException;
import jfvb.com.pitangbackend.core.gateway.AccountUserGateway;
import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import jfvb.com.pitangbackend.dataprovider.security.SecurityGateway;
import jfvb.com.pitangbackend.entrypoint.dto.AccountUserDto;
import jfvb.com.pitangbackend.entrypoint.dto.AuthenticationRequest;
import jfvb.com.pitangbackend.entrypoint.dto.CarDto;
import jfvb.com.pitangbackend.infrastructure.security.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SecurityGatewayImplTest extends BaseUnitTest {

    private final AccountUserGateway accountUserGateway = mock(AccountUserGateway.class);
    private final AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
    private final JwtService jwtService = mock(JwtService.class);
    private final SecurityGateway securityGateway = new SecurityGatewayImpl(accountUserGateway, authenticationManager, jwtService);
    private final Authentication authentication = Mockito.mock(Authentication.class);
    private final SecurityContext securityContext = Mockito.mock(SecurityContext.class);

    @Test
    void shouldAuthenticate() {
        // GIVEN
        var authenticationRequest = new AuthenticationRequest("login", "password");

        AccountUser accountUser = new AccountUser(toAccountUserDto(1L));
        var authenticationResponse = new UsernamePasswordAuthenticationToken(accountUser, null);

        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willReturn(authenticationResponse);

        String fakeToken = "Fake token";
        given(jwtService.generateToken(any()))
                .willReturn(fakeToken);

        // WHEN
        final var response = securityGateway.authenticate(
                authenticationRequest
        );

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.token()).isEqualTo(fakeToken);
    }

    @Test
    void shouldThrowInvalidCredentialsExceptionWhenAuthenticationFails() {
        // GIVEN
        var authenticationRequest = new AuthenticationRequest("username", "wrong_password");
        given(authenticationManager.authenticate(any()))
                .willThrow(BadCredentialsException.class);

        // WHEN
        final var exception = assertThrows(InvalidCredentialsException.class,
                () -> securityGateway.authenticate(authenticationRequest));

        // THEN
        assertThat(exception).isNotNull();
        assertThat(exception.getErrorCode()).isOne();
        assertThat(exception.getMessage()).isEqualTo("Invalid login or password");
    }

    @Test
    void shouldReturnUserDtoWhenHasAuthenticateUser() {
        // GIVEN
        var accountUser = toAccountUserLoggedIn(2L);

        var accountUserDto = createAccountUserDto(accountUser);

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);


        when(authentication.getPrincipal())
                .thenReturn(accountUser);

        //WHEN
        final var response = securityGateway.me();

        //THEN
        assertThat(response).isNotNull();
        assertThat(response)
                .usingRecursiveComparison()
                .ignoringFields("active")
                .isEqualTo(accountUserDto);
    }

    private AccountUserDto createAccountUserDto(AccountUser accountUser) {
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
                true
        );
    }

}