package jfvb.com.pitangbackend.core.gateway;

import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;

public interface AccountUserGateway extends AbstractGateway<AccountUser, Long> {
    boolean existsByEmail(String email);

    boolean existsByLogin(String login);
}
