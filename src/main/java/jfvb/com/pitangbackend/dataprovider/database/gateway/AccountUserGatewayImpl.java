package jfvb.com.pitangbackend.dataprovider.database.gateway;

import jfvb.com.pitangbackend.core.gateway.AccountUserGateway;
import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import jfvb.com.pitangbackend.dataprovider.database.repository.AccountUserRepository;

public class AccountUserGatewayImpl
        extends AbstractGatewayImpl<AccountUser, Long, AccountUserRepository>
        implements AccountUserGateway {

    public AccountUserGatewayImpl(AccountUserRepository accountUserRepository) {
        super(accountUserRepository, AccountUser.class);
    }

    public boolean existsByEmail(String email) {
        return this.repository.existsByEmail(email);
    }

    public boolean existsByLogin(String login) {
        return this.repository.existsByLogin(login);
    }

}
