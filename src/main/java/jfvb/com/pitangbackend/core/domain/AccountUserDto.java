package jfvb.com.pitangbackend.core.domain;

import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;

import java.time.LocalDateTime;

public record AccountUserDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        LocalDateTime birthDay,
        String login,
        String password,
        String phone) {

    public AccountUserDto(AccountUser accountUser) {
        this(
                accountUser.getId(),
                accountUser.getFirstName(),
                accountUser.getLastName(),
                accountUser.getEmail(),
                accountUser.getBirthDay(),
                accountUser.getLogin(),
                accountUser.getPassword(),
                accountUser.getPhone()
        );
    }
}
