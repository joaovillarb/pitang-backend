package jfvb.com.pitangbackend.dataprovider.database.entity;

import jfvb.com.pitangbackend.entrypoint.dto.AccountUserDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_email_account_user", columnNames = {"email"}),
        @UniqueConstraint(name = "uk_login_account_user", columnNames = {"login"})})
public class AccountUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthday;
    private String login;
    private String password;
    private String phone;

    public AccountUser(AccountUserDto accountUser) {
        this(
                accountUser.id(),
                accountUser.firstName(),
                accountUser.lastName(),
                accountUser.email(),
                accountUser.birthday(),
                accountUser.login(),
                accountUser.password(),
                accountUser.phone()
        );
    }

    public AccountUser update(AccountUserDto accountUser) {
        setFirstName(accountUser.firstName());
        setLastName(accountUser.lastName());
        setEmail(accountUser.email());
        setBirthday(accountUser.birthday());
        setLogin(accountUser.login());
        setPassword(accountUser.password());
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
        if (Objects.nonNull(accountUser.birthday())) {
            setBirthday(accountUser.birthday());
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
