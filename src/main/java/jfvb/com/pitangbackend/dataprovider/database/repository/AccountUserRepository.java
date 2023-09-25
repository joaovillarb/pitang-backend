package jfvb.com.pitangbackend.dataprovider.database.repository;

import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountUserRepository extends JpaRepository<AccountUser, Long> {

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

    Optional<AccountUser> findByLogin(String login);
}
