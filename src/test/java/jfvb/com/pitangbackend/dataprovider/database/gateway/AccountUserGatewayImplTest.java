package jfvb.com.pitangbackend.dataprovider.database.gateway;

import jfvb.com.pitangbackend.BaseUnitTest;
import jfvb.com.pitangbackend.core.exception.NotFoundException;
import jfvb.com.pitangbackend.core.gateway.AccountUserGateway;
import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import jfvb.com.pitangbackend.dataprovider.database.repository.AccountUserRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

    // todo: delete and pageby
}