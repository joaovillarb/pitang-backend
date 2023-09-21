package jfvb.com.pitangbackend;

import jfvb.com.pitangbackend.entrypoint.dto.AccountUserDto;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
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
                "phone"
        );
    }
}
