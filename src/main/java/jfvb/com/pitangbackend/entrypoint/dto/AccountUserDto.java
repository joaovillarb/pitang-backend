package jfvb.com.pitangbackend.entrypoint.dto;

import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

public record AccountUserDto(
        Long id,
        @NotNull
        @NotBlank
        String firstName,
        @NotNull
        @NotBlank
        String lastName,
        @NotNull
        @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
        String email,
        @NotNull
        LocalDate birthday,
        @NotNull
        @NotBlank
        String login,
        @NotNull
        @NotBlank
        String password,
        @NotNull
        @NotBlank
        String phone,
        List<CarDto> cars) {

    public AccountUserDto(AccountUser accountUser) {
        this(
                accountUser.getId(),
                accountUser.getFirstName(),
                accountUser.getLastName(),
                accountUser.getEmail(),
                accountUser.getBirthday(),
                accountUser.getLogin(),
                accountUser.getPassword(),
                accountUser.getPhone(),
                accountUser.getCars().stream()
                        .map(CarDto::new)
                        .toList()
        );
    }
}
