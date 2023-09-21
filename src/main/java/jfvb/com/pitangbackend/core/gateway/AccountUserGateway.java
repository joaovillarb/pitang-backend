package jfvb.com.pitangbackend.core.gateway;

import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountUserGateway {

    AccountUser getById(Long id);

    AccountUser save(AccountUser accountUser);

    Page<AccountUser> pageBy(Pageable pageable);

    void delete(Long id);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);
}
