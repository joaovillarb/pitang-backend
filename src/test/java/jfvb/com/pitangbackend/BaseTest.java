package jfvb.com.pitangbackend;

import jfvb.com.pitangbackend.entrypoint.dto.AccountUserDto;
import jfvb.com.pitangbackend.entrypoint.dto.CarDto;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@ActiveProfiles("test")
public abstract class BaseTest {

    protected static final String randomUuid = UUID.randomUUID().toString();

    protected AccountUserDto toAccountUserDto(Long id) {
        return new AccountUserDto(
                id,
                "firstName",
                "lastName",
                "email@email.com",
                LocalDate.now(),
                "login",
                "password",
                "phone",
                List.of(toCarDto(id))
        );
    }

    protected CarDto toCarDto(Long id) {
        return new CarDto(
                id,
                2018,
                "PDV-0622",
                "Audi",
                "White",
                0
        );
    }
}
