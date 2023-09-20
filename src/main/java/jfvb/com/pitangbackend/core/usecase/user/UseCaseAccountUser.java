package jfvb.com.pitangbackend.core.usecase.user;

import jfvb.com.pitangbackend.core.domain.AccountUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UseCaseAccountUser {
    AccountUserDto getById(Long id);

    AccountUserDto create(AccountUserDto accountUser);

    AccountUserDto update(Long id, AccountUserDto accountUser);

    AccountUserDto patch(Long id, AccountUserDto accountUser);

    void delete(Long id);

    Page<AccountUserDto> pageBy(Pageable pageable);
}
