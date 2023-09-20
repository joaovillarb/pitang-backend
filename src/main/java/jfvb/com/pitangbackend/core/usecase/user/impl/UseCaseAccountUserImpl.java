package jfvb.com.pitangbackend.core.usecase.user.impl;

import jfvb.com.pitangbackend.core.domain.AccountUserDto;
import jfvb.com.pitangbackend.core.gateway.AccountUserGateway;
import jfvb.com.pitangbackend.core.usecase.user.UseCaseAccountUser;
import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class UseCaseAccountUserImpl implements UseCaseAccountUser {

    private final AccountUserGateway accountUserGateway;

    public UseCaseAccountUserImpl(AccountUserGateway accountUserGateway) {
        this.accountUserGateway = accountUserGateway;
    }

    public AccountUserDto getById(Long id) {
        AccountUser accountUser = this.accountUserGateway.getById(id);
        return new AccountUserDto(accountUser);
    }

    public AccountUserDto create(AccountUserDto accountUserDto) {
        AccountUser accountUser = new AccountUser(accountUserDto);
        AccountUser saved = this.accountUserGateway.save(accountUser);
        return new AccountUserDto(saved);
    }

    public AccountUserDto update(Long id, AccountUserDto accountUser) {
        AccountUser updated = this.accountUserGateway.getById(id)
                .update(accountUser);
        AccountUser saved = this.accountUserGateway.save(updated);
        return new AccountUserDto(saved);
    }

    public AccountUserDto patch(Long id, AccountUserDto accountUser) {
        AccountUser updated = this.accountUserGateway.getById(id)
                .patch(accountUser);
        AccountUser saved = this.accountUserGateway.save(updated);
        return new AccountUserDto(saved);
    }

    public void delete(Long id) {
        this.accountUserGateway.getById(id);
        this.accountUserGateway.delete(id);
    }

    public Page<AccountUserDto> pageBy(Pageable pageable) {
        return this.accountUserGateway.pageBy(pageable)
                .map(AccountUserDto::new);
    }
}
