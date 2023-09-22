package jfvb.com.pitangbackend.core.usecase.user;

import jfvb.com.pitangbackend.entrypoint.dto.AccountUserDto;

import java.util.List;

public interface UseCaseAccountUser {
    AccountUserDto getById(Long id);

    AccountUserDto create(AccountUserDto accountUser);

    AccountUserDto update(Long id, AccountUserDto accountUser);

    AccountUserDto patch(Long id, AccountUserDto accountUser);

    void delete(Long id);

    List<AccountUserDto> findAll();
}
