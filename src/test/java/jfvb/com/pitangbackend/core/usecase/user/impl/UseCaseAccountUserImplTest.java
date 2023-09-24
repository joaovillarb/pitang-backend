package jfvb.com.pitangbackend.core.usecase.user.impl;

import jfvb.com.pitangbackend.BaseUnitTest;
import jfvb.com.pitangbackend.core.exception.AlreadyExistsException;
import jfvb.com.pitangbackend.core.gateway.AccountUserGateway;
import jfvb.com.pitangbackend.core.usecase.user.UseCaseAccountUser;
import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import jfvb.com.pitangbackend.entrypoint.dto.AccountUserDto;
import jfvb.com.pitangbackend.provider.WrongUserProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
                .ignoringFields("password")
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
                .ignoringFields("password")
                .isEqualTo(accountUser);
    }

    @ParameterizedTest
    @ArgumentsSource(WrongUserProvider.class)
    void shouldThrowExceptionWhenCreateAccountUserWithBlankValues(final AccountUserDto accountUser, final RuntimeException expectedException) {
        // WHEN
        final var response = assertThrows(RuntimeException.class,
                () -> this.useCase.create(accountUser));

        // THEN
        assertThat(response).isNotNull();
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(expectedException);
        verify(accountUserGateway, times(0)).existsByEmail(anyString());
        verify(accountUserGateway, times(0)).existsByLogin(anyString());
        verify(accountUserGateway, times(0)).save(any(AccountUser.class));
    }

    @Test
    void shouldUpdateAccountUser() {
        // GIVEN
        final long accountId = 1L;
        final AccountUserDto updatedAccountUserDto = createAccountUserDto(accountId, "email@email.com");

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
                .ignoringFields("password")
                .isEqualTo(updatedAccountUserDto);
    }

    @Test
    void shouldPatchAccountUser() {
        // GIVEN
        final Long accountId = 1L;
        final AccountUserDto updatedAccountUserDto = createAccountUserDto(accountId, "email");

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
                .ignoringFields("password")
                .isEqualTo(updatedAccountUserDto);
    }

    private AccountUserDto createAccountUserDto(Long accountId, String email) {
        return new AccountUserDto(
                accountId,
                "new firstName",
                "lastName",
                email,
                LocalDate.now(),
                "login",
                "password",
                "phone",
                List.of(toCarDto(accountId)),
                null,
                null
        );
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

    @Test
    void shouldThrownAlreadyExistsExceptionWhenTryTwoCreateAccountsWithSameEmail() {
        // GIVEN
        final AccountUserDto accountUser = toAccountUserDto(null);

        given(this.accountUserGateway.existsByEmail(accountUser.email()))
                .willReturn(true);

        // WHEN
        final var exception = assertThrows(AlreadyExistsException.class,
                () -> this.useCase.create(accountUser));

        // THEN
        assertThat(exception).isNotNull();
        verify(accountUserGateway).existsByEmail(accountUser.email());
    }

    @Test
    void shouldThrownAlreadyExistsExceptionWhenTryTwoCreateAccountsWithSameLogin() {
        // GIVEN
        final AccountUserDto accountUser = toAccountUserDto(null);

        given(this.accountUserGateway.existsByEmail(accountUser.email()))
                .willReturn(false);

        given(this.accountUserGateway.existsByLogin(accountUser.login()))
                .willReturn(true);

        // WHEN
        final var exception = assertThrows(AlreadyExistsException.class,
                () -> this.useCase.create(accountUser));

        // THEN
        assertThat(exception).isNotNull();
        verify(accountUserGateway).existsByEmail(accountUser.email());
        verify(accountUserGateway).existsByLogin(accountUser.login());
    }

    @Test
    void testFindAllWithEmptyList() {
        // GIVEN
        given(accountUserGateway.findAll())
                .willReturn(new ArrayList<>());

        // WHEN
        final var response = useCase.findAll();

        // THEN
        assertThat(response).isEmpty();
    }

    @Test
    void testFindAllWithUsersAndCars() {
        // GIVEN
        var usersDto = createListAccountUserDto();
        var users = usersDto.stream().map(AccountUser::new).toList();

        given(accountUserGateway.findAll())
                .willReturn(users);

        // WHEN
        final var result = useCase.findAll();

        // THEN
        assertThat(result).hasSize(2);
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("password")
                .ignoringCollectionOrder()
                .isEqualTo(usersDto);
    }
}