package jfvb.com.pitangbackend.entrypoint.api;

import jfvb.com.pitangbackend.BaseIntegrationTests;
import jfvb.com.pitangbackend.entrypoint.advices.CustomErrorResponse;
import jfvb.com.pitangbackend.entrypoint.dto.AccountUserDto;
import jfvb.com.pitangbackend.provider.WrongUserProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountUserApiIntegrationTest extends BaseIntegrationTests {

    @Test
    void shouldCreateUser() throws Exception {
        // GIVEN
        AccountUserDto accountUserDto = new AccountUserDto(
                null,
                "firstName",
                "lastName",
                "email@emailrandom.com",
                LocalDate.now(),
                randomUuid,
                "password",
                "phone",
                List.of(toCarDto(null))
        );
        // WHEN
        final var resultActions = requestPost(accountUserDto, URI.create("/users"));

        // THEN
        resultActions.andExpect(status().isCreated());
        final var response = this.gson.fromJson(
                resultActions.andReturn().getResponse().getContentAsString(),
                AccountUserDto.class);

        assertThat(response).isNotNull();
        assertThat(response)
                .usingRecursiveComparison()
                .ignoringFields("id", "cars.id")
                .isEqualTo(accountUserDto);
    }

    @Test
    void shouldThrowExceptionWhenCreateTwoEqualsUsers() throws Exception {
        AccountUserDto accountUserDto = toAccountUserDto(null);
        // WHEN
        var resultActions = requestPost(accountUserDto, URI.create("/users"));
        resultActions = requestPost(accountUserDto, URI.create("/users"));

        // THEN
        resultActions.andExpect(status().isConflict());
        final var response = this.gson.fromJson(
                resultActions.andReturn().getResponse().getContentAsString(),
                CustomErrorResponse.class);

        assertThat(response).isNotNull();
    }

    @ParameterizedTest
    @ArgumentsSource(WrongUserProvider.class)
    void shouldThrowBadRequestWhenCreateUser(
            final AccountUserDto accountUserDto,
            final CustomErrorResponse customErrorResponseExpected) throws Exception {
        // WHEN
        final var resultActions = requestPost(accountUserDto, URI.create("/users"));

        // THEN
        resultActions.andExpect(status().isBadRequest());
        final var response = this.gson.fromJson(
                resultActions.andReturn().getResponse().getContentAsString(),
                CustomErrorResponse.class);

        assertThat(response).isNotNull();
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(customErrorResponseExpected);
    }
}
