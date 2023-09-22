package jfvb.com.pitangbackend.core.gateway;

import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;

import java.util.List;

public interface AccountUserGateway {

    AccountUser getById(Long id);

    AccountUser save(AccountUser accountUser);

    void delete(Long id);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

    List<AccountUser> findAll();
}
