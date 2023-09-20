package jfvb.com.pitangbackend.core.usecase.user.impl;

import jfvb.com.pitangbackend.BaseUnitTest;
import jfvb.com.pitangbackend.core.domain.AccountUserDto;
import jfvb.com.pitangbackend.core.gateway.AccountUserGateway;
import jfvb.com.pitangbackend.core.usecase.user.UseCaseAccountUser;
import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UseCaseAccountUserImplTest extends BaseUnitTest {

    private final AccountUserGateway accountUserGateway = mock(AccountUserGateway.class);
    private final UseCaseAccountUser useCase = new UseCaseAccountUserImpl(accountUserGateway);

    @Test
    void getById() {
        // GIVEN
        final AccountUserDto accountUser = toAccountUserDto(1L);
        final AccountUser accountUserEntity = new AccountUser(accountUser);
        given(this.accountUserGateway.getById(1L))
                .willReturn(accountUserEntity);

        // WHEN
        final AccountUserDto response = this.useCase.getById(1L);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(accountUser);
    }

    @Test
    void shouldCreateAccountUser() {
        // GIVEN
        final AccountUserDto accountUser = toAccountUserDto(null);
        final AccountUser accountUserEntity = new AccountUser(accountUser);

        given(this.accountUserGateway.save(any(AccountUser.class)))
                .willReturn(accountUserEntity);

        // WHEN
        final AccountUserDto response = this.useCase.create(accountUser);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(accountUser);
    }

    @Test
    void shouldUpdateAccountUser() {
        // GIVEN
        final long accountId = 1L;
        final AccountUserDto updatedAccountUserDto = new AccountUserDto(
                accountId,
                "new firstName",
                "lastName",
                "email",
                LocalDateTime.now(),
                "login",
                "password",
                "phone"
        );

        final AccountUser originalAccountUserEntity = new AccountUser(toAccountUserDto(accountId));
        final AccountUser updatedAccountUserEntity = new AccountUser(updatedAccountUserDto);

        given(accountUserGateway.getById(accountId))
                .willReturn(originalAccountUserEntity);

        given(accountUserGateway.save(any(AccountUser.class)))
                .willReturn(updatedAccountUserEntity);

        // WHEN
        final AccountUserDto response = useCase.update(accountId, updatedAccountUserDto);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(updatedAccountUserDto);
    }

    @Test
    void shouldPatchAccountUser() {
        // GIVEN
        final Long accountId = 1L;
        final AccountUserDto updatedAccountUserDto = new AccountUserDto(
                accountId,
                "new firstName",
                "lastName",
                "email",
                LocalDateTime.now(),
                "login",
                "password",
                "phone"
        );

        final AccountUser originalAccountUserEntity = new AccountUser(toAccountUserDto(accountId));
        final AccountUser updatedAccountUserEntity = new AccountUser(updatedAccountUserDto);

        given(accountUserGateway.getById(accountId))
                .willReturn(originalAccountUserEntity);

        given(accountUserGateway.save(any(AccountUser.class)))
                .willReturn(updatedAccountUserEntity);

        // WHEN
        final AccountUserDto response = useCase.patch(accountId, updatedAccountUserDto);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(updatedAccountUserDto);
    }

    @Test
    void shouldDeleteAccountUser() {
        // GIVEN
        final Long accountId = 1L;

        given(accountUserGateway.getById(accountId))
                .willReturn(null);

        // WHEN
        useCase.delete(accountId);

        // THEN
        verify(accountUserGateway).getById(any(Long.class));
        verify(accountUserGateway).delete(any(Long.class));
    }


}