package jfvb.com.pitangbackend.core.usecase.user.impl;

import jfvb.com.pitangbackend.core.exception.AlreadyExistsException;
import jfvb.com.pitangbackend.core.gateway.AccountUserGateway;
import jfvb.com.pitangbackend.core.usecase.user.UseCaseAccountUser;
import jfvb.com.pitangbackend.core.validator.FieldsValidator;
import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import jfvb.com.pitangbackend.dataprovider.database.entity.Car;
import jfvb.com.pitangbackend.entrypoint.dto.AccountUserDto;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class UseCaseAccountUserImpl implements UseCaseAccountUser {

    private final AccountUserGateway accountUserGateway;

    public UseCaseAccountUserImpl(AccountUserGateway accountUserGateway) {
        this.accountUserGateway = accountUserGateway;
    }

    public AccountUserDto getById(Long id) {
        var accountUser = recoverById(id);
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

        var accountUser = new AccountUser(accountUserDto);
        accountUser.setActive(true);
        accountUser.getCars().forEach(car -> car.setActive(true));

        var saved = persist(accountUser);
        return new AccountUserDto(saved);
    }

    public AccountUserDto update(Long id, AccountUserDto accountUser) {
        FieldsValidator.validate(accountUser);
        var updated = this.accountUserGateway.getById(id)
                .update(accountUser);
        var saved = persist(updated);
        return new AccountUserDto(saved);
    }

    public AccountUserDto patch(Long id, AccountUserDto accountUser) {
        var updated = recoverById(id)
                .patch(accountUser);
        var saved = persist(updated);
        return new AccountUserDto(saved);
    }

    public void delete(Long id) {
        var accountUser = recoverById(id);
        this.accountUserGateway.logicalDelete(accountUser);
    }

    public List<AccountUserDto> findAll() {
        return this.accountUserGateway.findAll()
                .stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(user -> {
                                    IntStream intStream = ((AccountUser) user).getCars()
                                            .stream()
                                            .mapToInt(Car::getUsageCount);
                                    return intStream.sum();
                                })
                                .thenComparing(user -> ((AccountUser) user).getLogin())
                )
                .map(user -> {
                    List<Car> list = user.getCars()
                            .stream()
                            .sorted(Comparator
                                    .comparingInt(Car::getUsageCount)
                                    .reversed()
                                    .thenComparing(Car::getModel)
                            )
                            .toList();
                    return new AccountUserDto(user, list);
                })
                .toList();
    }

    private AccountUser recoverById(Long id) {
        return this.accountUserGateway.getById(id);
    }

    private AccountUser persist(AccountUser updated) {
        return this.accountUserGateway.save(updated);
    }
}
