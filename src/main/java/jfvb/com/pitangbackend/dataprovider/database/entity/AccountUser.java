package jfvb.com.pitangbackend.dataprovider.database.entity;

import jfvb.com.pitangbackend.entrypoint.dto.AccountUserDto;
import jfvb.com.pitangbackend.infrastructure.config.ApplicationConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_email_account_user", columnNames = {"email"}),
        @UniqueConstraint(name = "uk_login_account_user", columnNames = {"login"})})
public class AccountUser extends BaseEntity implements UserDetails {

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
    @OneToMany(mappedBy = "accountUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars;
    private LocalDateTime lastLogin;

    public AccountUser(AccountUserDto accountUser) {
        this.id = accountUser.id();
        this.firstName = accountUser.firstName();
        this.lastName = accountUser.lastName();
        this.email = accountUser.email();
        this.birthday = accountUser.birthday();
        this.login = accountUser.login();
        this.password = ApplicationConfig.passwordEncoder().encode(accountUser.password());
        this.phone = accountUser.phone();
        this.cars = accountUser.cars().stream()
                .map(carDto -> new Car(carDto, this))
                .toList();
    }


    public AccountUser update(AccountUserDto accountUser) {
        this.firstName = accountUser.firstName();
        this.lastName = accountUser.lastName();
        this.email = accountUser.email();
        this.birthday = accountUser.birthday();
        this.login = accountUser.login();
        this.password = ApplicationConfig.passwordEncoder().encode(accountUser.password());
        this.phone = accountUser.phone();
        this.active = accountUser.active();
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
            setPassword(ApplicationConfig.passwordEncoder().encode(accountUser.password()));
        }
        if (Objects.nonNull(accountUser.phone())) {
            setPhone(accountUser.phone());
        }
        if (Objects.nonNull(accountUser.active())) {
            setActive(accountUser.active());
        }
        return this;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public String getUsername() {
        return this.login;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return getActive();
    }

    public String toString() {
        return "AccountUser{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
