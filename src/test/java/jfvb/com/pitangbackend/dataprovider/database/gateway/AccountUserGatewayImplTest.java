package jfvb.com.pitangbackend.dataprovider.database.gateway;

import jfvb.com.pitangbackend.BaseUnitTest;
import jfvb.com.pitangbackend.core.exception.NotFoundException;
import jfvb.com.pitangbackend.core.gateway.AccountUserGateway;
import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import jfvb.com.pitangbackend.dataprovider.database.repository.AccountUserRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

class AccountUserGatewayImplTest extends BaseUnitTest {

    private final AccountUserRepository accountUserRepository = mock(AccountUserRepository.class);
    private final AccountUserGateway accountUserGateway = new AccountUserGatewayImpl(accountUserRepository);

    @Test
    void shouldGetById() {
        // GIVEN
        final Long userId = 1L;
        final AccountUser accountUser = new AccountUser(toAccountUserDto(userId));

        given(accountUserRepository.findById(userId))
                .willReturn(Optional.of(accountUser));

        // WHEN
        final AccountUser response = accountUserGateway.getById(userId);

        // THEN
        assertThat(response).isNotNull();
        assertThat(userId).isEqualTo(response.getId());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGetById() {
        // GIVEN
        final Long userId = 1L;

        given(accountUserRepository.findById(userId))
                .willReturn(Optional.empty());

        // WHEN
        final var exception = assertThrows(NotFoundException.class,
                () -> accountUserGateway.getById(userId));

        // THEN
        assertThat(exception).isNotNull();
        verify(accountUserRepository).findById(userId);
    }

    @Test
    void shouldSave() {
        // GIVEN
        final Long userId = 1L;
        final AccountUser accountUser = new AccountUser(toAccountUserDto(userId));

        given(accountUserRepository.saveAndFlush(accountUser))
                .willReturn(accountUser);

        // WHEN
        final AccountUser response = accountUserGateway.save(accountUser);

        // THEN
        assertThat(response).isNotNull();
        assertThat(accountUser).isEqualTo(response);
    }

    @Test
    void shouldDelete() {
        // GIVEN
        final Long carId = 1L;

        doNothing().when(accountUserRepository).deleteById(carId);
        doNothing().when(accountUserRepository).flush();

        // WHEN
        accountUserGateway.delete(carId);

        // THEN
        verify(accountUserRepository).deleteById(carId);
        verify(accountUserRepository).flush();
    }

    @Test
    void shouldVerifyIfExistsByEmail() {
        // GIVEN
        final String email = "email@email.com";

        given(accountUserRepository.existsByEmail(email))
                .willReturn(true);

        // WHEN
        final boolean response = accountUserGateway.existsByEmail(email);

        // THEN
        assertThat(response).isTrue();
        verify(accountUserRepository).existsByEmail(email);
    }

    @Test
    void shouldVerifyIfExistsByLogin() {
        // GIVEN
        final String login = "login";

        given(accountUserRepository.existsByLogin(login))
                .willReturn(true);

        // WHEN
        final boolean response = accountUserGateway.existsByLogin(login);

        // THEN
        assertThat(response).isTrue();
        verify(accountUserRepository).existsByLogin(login);
    }

    @Test
    void shouldFindAll() {
        //  GIVEN
        final var accountUserList = toAccountUserDto(1L);
        final var accountUserDtoList = List.of(accountUserList);
        final var accountUserListEntity = accountUserDtoList
                .stream()
                .map(AccountUser::new)
                .toList();

        given(accountUserRepository.findAll())
                .willReturn(accountUserListEntity);

        //  WHEN
        final var response = accountUserGateway.findAll();

        //  THEN
        assertThat(response).isNotNull();
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(accountUserListEntity);
    }

}