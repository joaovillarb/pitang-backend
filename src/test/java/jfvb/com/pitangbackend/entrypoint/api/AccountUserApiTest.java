package jfvb.com.pitangbackend.entrypoint.api;

import jfvb.com.pitangbackend.BaseControllerUnitTest;
import jfvb.com.pitangbackend.core.usecase.user.UseCaseAccountUser;
import jfvb.com.pitangbackend.entrypoint.dto.AccountUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

class AccountUserApiTest extends BaseControllerUnitTest {

    private final UseCaseAccountUser useCase = mock(UseCaseAccountUser.class);
    private final AccountUserApi api = new AccountUserApi(this.useCase);

    @Test
    void getById() {
        // GIVEN
        final AccountUserDto accountUser = toAccountUserDto(1L);

        given(this.useCase.getById(1L))
                .willReturn(accountUser);

        // WHEN
        final ResponseEntity<AccountUserDto> response = this.api.getById(1L);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(accountUser);
    }

    @Test
    void shouldFindAllAccountUser() {
        // GIVEN
        final AccountUserDto accountUser = toAccountUserDto(1L);
        final List<AccountUserDto> accountUserList = List.of(accountUser);

        given(this.useCase.findAll())
                .willReturn(accountUserList);

        // WHEN
        final var response = this.api.findAll();

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(accountUserList);
    }

    @Test
    void shouldCreateAccountUser() {
        // GIVEN
        final AccountUserDto accountUser = toAccountUserDto(null);

        given(this.useCase.create(accountUser))
                .willReturn(accountUser);

        // WHEN
        final ResponseEntity<AccountUserDto> response = this.api.create(accountUser);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(accountUser);
    }

    @Test
    void shouldUpdateAccountUser() {
        // GIVEN
        final AccountUserDto accountUser = toAccountUserDto(1L);

        given(this.useCase.update(1L, accountUser))
                .willReturn(accountUser);

        // WHEN
        final ResponseEntity<AccountUserDto> response = this.api.update(1L, accountUser);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(accountUser);
    }

    @Test
    void shouldPatchAccountUser() {
        // GIVEN
        final AccountUserDto accountUser = toAccountUserDto(1L);

        given(this.useCase.patch(1L, accountUser))
                .willReturn(accountUser);

        // WHEN
        final ResponseEntity<AccountUserDto> response = this.api.patch(1L, accountUser);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(accountUser);
    }

    @Test
    void shouldDeleteAccountUser() {
        // GIVEN
        doNothing().when(this.useCase)
                .delete(1L);

        // WHEN
        final ResponseEntity<Void> response = this.api.delete(1L);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}