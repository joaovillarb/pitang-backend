package jfvb.com.pitangbackend.dataprovider.database.gateway;

import jfvb.com.pitangbackend.core.exception.NotFoundException;
import jfvb.com.pitangbackend.core.gateway.AccountUserGateway;
import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import jfvb.com.pitangbackend.dataprovider.database.repository.AccountUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AccountUserGatewayImpl implements AccountUserGateway {

    private final AccountUserRepository accountUserRepository;

    public AccountUserGatewayImpl(AccountUserRepository accountUserRepository) {
        this.accountUserRepository = accountUserRepository;
    }

    public AccountUser getById(Long id) {
        return this.accountUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User not found with id=%s", id), 404));
    }

    public AccountUser save(AccountUser accountUser) {
        return this.accountUserRepository.saveAndFlush(accountUser);
    }

    public Page<AccountUser> pageBy(Pageable pageable) {
        return this.accountUserRepository.findAll(pageable);
    }

    public void delete(Long id) {
        this.accountUserRepository.deleteById(id);
        this.accountUserRepository.flush();
    }

    public boolean existsByEmail(String email) {
        return this.accountUserRepository.existsByEmail(email);
    }

    public boolean existsByLogin(String login) {
        return this.accountUserRepository.existsByLogin(login);
    }

}
