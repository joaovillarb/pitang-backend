package jfvb.com.pitangbackend;

import jfvb.com.pitangbackend.core.domain.AccountUserDto;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@ActiveProfiles("test")
public abstract class BaseTest {

    protected AccountUserDto toAccountUserDto(Long id) {
        return new AccountUserDto(
                id,
                "firstName",
                "lastName",
                "email",
                LocalDateTime.now(),
                "login",
                "password",
                "phone"
        );
    }
}
