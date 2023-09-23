package jfvb.com.pitangbackend.provider;

import jfvb.com.pitangbackend.BaseTest;
import jfvb.com.pitangbackend.core.exception.InvalidFieldsException;
import jfvb.com.pitangbackend.core.exception.MissingFieldsException;
import jfvb.com.pitangbackend.entrypoint.dto.AccountUserDto;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.time.LocalDate;
import java.util.List;
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
                                "phone",
                                List.of(toCarDto(null))
                        ),
                        new MissingFieldsException("Missing Field(s): [firstName]")
                ),
                Arguments.of(
                        new AccountUserDto(
                                null,
                                "",
                                "lastName",
                                "email@email.com",
                                LocalDate.now(),
                                "login",
                                "password",
                                "phone",
                                List.of(toCarDto(null))
                        ),
                        new InvalidFieldsException("Invalid Field(s): [firstName]")
                )
        );
    }
}
