package jfvb.com.pitangbackend.provider;

import jfvb.com.pitangbackend.BaseTest;
import jfvb.com.pitangbackend.entrypoint.advices.CustomErrorResponse;
import jfvb.com.pitangbackend.entrypoint.dto.AccountUserDto;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.time.LocalDate;
import java.util.stream.Stream;

public class WrongUserProvider extends BaseTest implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                Arguments.of(
                        new AccountUserDto(
                                null,
                                null,
                                "lastName",
                                "email@email.com",
                                LocalDate.now(),
                                "login",
                                "password",
                                "phone"
                        ),
                        new CustomErrorResponse(
                                "Missing Field(s): [firstName]",
                                5
                        )
                ),
                Arguments.of(
                        new AccountUserDto(
                                null,
                                "firstName",
                                "lastName",
                                "email",
                                LocalDate.now(),
                                "login",
                                "password",
                                "phone"
                        ),
                        new CustomErrorResponse(
                                "Invalid Field(s): [email]",
                                4
                        )
                )
        );
    }
}
