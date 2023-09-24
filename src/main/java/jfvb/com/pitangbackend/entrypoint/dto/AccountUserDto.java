package jfvb.com.pitangbackend.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jfvb.com.pitangbackend.core.annotations.DtoNotBlank;
import jfvb.com.pitangbackend.core.annotations.DtoNotNull;
import jfvb.com.pitangbackend.core.annotations.DtoPattern;
import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import jfvb.com.pitangbackend.dataprovider.database.entity.Car;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AccountUserDto(
        Long id,
        @DtoNotNull
        @DtoNotBlank
        String firstName,
        @DtoNotNull
        @DtoNotBlank
        String lastName,
        @DtoNotNull
        @DtoNotBlank
        @DtoPattern("^[A-Za-z0-9+_.-]+@(.+)$")
        String email,
        @DtoNotNull
        LocalDate birthday,
        @DtoNotNull
        @DtoNotBlank
        String login,
        @DtoNotNull
        @DtoNotBlank
        String password,
        @DtoNotNull
        @DtoNotBlank
        String phone,
        List<CarDto> cars,
        LocalDateTime createdAt,
        LocalDateTime lastLogin
) {

    public AccountUserDto(AccountUser accountUser) {
        this(
                accountUser.getId(),
                accountUser.getFirstName(),
                accountUser.getLastName(),
                accountUser.getEmail(),
                accountUser.getBirthday(),
                accountUser.getLogin(),
                null,
                accountUser.getPhone(),
                accountUser.getCars().stream()
                        .map(CarDto::new)
                        .toList(),
                null,
                null
        );
    }

    public AccountUserDto(AccountUser accountUser, List<Car> sortedCars) {
        this(
                accountUser.getId(),
                accountUser.getFirstName(),
                accountUser.getLastName(),
                accountUser.getEmail(),
                accountUser.getBirthday(),
                accountUser.getLogin(),
                null,
                accountUser.getPhone(),
                sortedCars.stream()
                        .map(CarDto::new)
                        .toList(),
                null,
                null
        );
    }
}
