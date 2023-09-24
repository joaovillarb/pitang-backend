package jfvb.com.pitangbackend.entrypoint.api;

import jfvb.com.pitangbackend.BaseControllerUnitTest;
import jfvb.com.pitangbackend.dataprovider.security.SecurityGateway;
import jfvb.com.pitangbackend.entrypoint.dto.AuthenticationRequest;
import jfvb.com.pitangbackend.entrypoint.dto.AuthenticationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SecurityApiTest extends BaseControllerUnitTest {

    private final SecurityGateway useCase = mock(SecurityGateway.class);
    private final SecurityApi api = new SecurityApi(this.useCase);

    @Test
    void shouldAuthenticateUser() {
        // GIVEN
        var authenticationResponse = new AuthenticationResponse("XXXXXXXXXX");

        given(useCase.authenticate(any(AuthenticationRequest.class)))
                .willReturn(authenticationResponse);

        // WHEN
        final var response = api.authenticate(
                new AuthenticationRequest(
                        "login",
                        "password"
                )
        );

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(authenticationResponse);
    }

    @Test
    void shouldReturnUserDtoWhenHasAuthenticateUser() {
        // GIVEN
        var accountUser = toAccountUserDtoLoggedIn(2L);

        given(useCase.me())
                .willReturn(accountUser);

        //WHEN
        final var response = api.me();

        //THEN
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(accountUser);
    }
}