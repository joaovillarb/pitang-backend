package jfvb.com.pitangbackend.dataprovider.database.entity;

import jfvb.com.pitangbackend.core.domain.AccountUserDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime birthDay;
    private String login;
    private String password;
    private String phone;

    public AccountUser(AccountUserDto accountUser) {
        this(
                accountUser.id(),
                accountUser.firstName(),
                accountUser.lastName(),
                accountUser.email(),
                accountUser.birthDay(),
                accountUser.login(), // todo: make sense?
                accountUser.password(), // todo: make sense?
                accountUser.phone()
        );
    }

    public AccountUser update(AccountUserDto accountUser) {
        setFirstName(accountUser.firstName());
        setLastName(accountUser.lastName());
        setEmail(accountUser.email());
        setBirthDay(accountUser.birthDay());
        setLogin(accountUser.login()); // todo: make sense?
        setPassword(accountUser.password()); // todo: make sense?
        setPhone(accountUser.phone());
        return this;
    }

    public AccountUser patch(AccountUserDto accountUser) {
        if (Objects.nonNull(accountUser.firstName())) {
            setFirstName(accountUser.firstName());
        }
        if (Objects.nonNull(accountUser.lastName())) {
            setLastName(accountUser.lastName());
        }
        if (Objects.nonNull(accountUser.email())) {
            setEmail(accountUser.email());
        }
        if (Objects.nonNull(accountUser.birthDay())) {
            setBirthDay(accountUser.birthDay());
        }
        if (Objects.nonNull(accountUser.login())) {
            setLogin(accountUser.login());
        }
        if (Objects.nonNull(accountUser.password())) {
            setPassword(accountUser.password());
        }
        if (Objects.nonNull(accountUser.phone())) {
            setPhone(accountUser.phone());
        }
        return this;
    }

}
