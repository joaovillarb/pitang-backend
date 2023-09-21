package jfvb.com.pitangbackend.core.usecase.user.impl;

import jfvb.com.pitangbackend.core.exception.AlreadyExistsException;
import jfvb.com.pitangbackend.core.gateway.AccountUserGateway;
import jfvb.com.pitangbackend.core.usecase.user.UseCaseAccountUser;
import jfvb.com.pitangbackend.core.validator.FieldsValidator;
import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import jfvb.com.pitangbackend.entrypoint.dto.AccountUserDto;
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
        FieldsValidator.validate(accountUserDto);

        if (this.accountUserGateway.existsByEmail(accountUserDto.email())) {
            throw new AlreadyExistsException("Email already exists", 2);
        }

        if (this.accountUserGateway.existsByLogin(accountUserDto.login())) {
            throw new AlreadyExistsException("Login already exists", 3);
        }

        AccountUser accountUser = new AccountUser(accountUserDto);
        AccountUser saved = this.accountUserGateway.save(accountUser);
        return new AccountUserDto(saved);
    }

    public AccountUserDto update(Long id, AccountUserDto accountUser) {
        FieldsValidator.validate(accountUser);
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
