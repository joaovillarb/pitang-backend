package jfvb.com.pitangbackend;

import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import jfvb.com.pitangbackend.dataprovider.database.entity.Car;
import jfvb.com.pitangbackend.entrypoint.dto.AccountUserDto;
import jfvb.com.pitangbackend.entrypoint.dto.CarDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration
public abstract class BaseTest {

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
                List.of(toCarDto(id)),
                null,
                null,
                true
        );
    }

    protected AccountUserDto toAccountUserDtoLoggedIn(Long id) {
        return new AccountUserDto(
                id,
                "firstName",
                "lastName",
                "email@email.com",
                LocalDate.now(),
                "login",
                null,
                "phone",
                List.of(toCarDto(id)),
                LocalDateTime.now(),
                LocalDateTime.now(),
                true
        );
    }

    protected AccountUser toAccountUserLoggedIn(Long id) {
        AccountUser accountUser = new AccountUser(
                id,
                "firstName",
                "lastName",
                "email@email.com",
                LocalDate.now(),
                "login",
                null,
                "phone",
                null,
                LocalDateTime.now()
        );

        accountUser.setCreatedAt(LocalDateTime.now());
        accountUser.setCars(List.of(toCar(id, accountUser)));

        return accountUser;
    }

    protected List<AccountUserDto> createListAccountUserDto() {
        List<AccountUserDto> usersDto = new ArrayList<>();
        usersDto.add(getAccountUserDto2());
        usersDto.add(getAccountUserDto1());
        return usersDto;
    }

    protected AccountUserDto getAccountUserDto1() {
        return new AccountUserDto(
                2L,
                "Hello",
                "World",
                "hello@world.com",
                LocalDate.parse("1990-05-01"),
                "hello.world",
                "h3ll0",
                "988888888",
                List.of(
                        new CarDto(3L, 2018, "PDV-0625", "Audi", "White", 0, true)
                ),
                null,
                null,
                true
        );
    }

    protected AccountUserDto getAccountUserDto2() {
        return new AccountUserDto(
                1L,
                "Hello",
                "World",
                "hello@world.comb",
                LocalDate.parse("1990-05-01"),
                "bello.world",
                "h3ll0",
                "988888888",
                List.of(
                        new CarDto(2L, 2018, "PDV-0622", "Ford", "White", 0, true),
                        new CarDto(1L, 2018, "PDV-0625", "Audi", "White", 1, true)
                ),
                null,
                null,
                true
        );
    }

    protected CarDto toCarDto(Long id) {
        return new CarDto(
                id,
                2018,
                "PDV-0622",
                "Audi",
                "White",
                0,
                true
        );
    }

    protected Car toCar(Long id, AccountUser user) {
        return new Car(
                id,
                2018,
                "PDV-0622",
                "Audi",
                "White",
                user,
                0
        );
    }

    protected List<CarDto> toCarDtoList(Long id) {
        return List.of(toCarDto(id));
    }
}
