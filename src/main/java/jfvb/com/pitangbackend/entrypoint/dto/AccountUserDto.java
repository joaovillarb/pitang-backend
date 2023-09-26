package jfvb.com.pitangbackend.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jfvb.com.pitangbackend.core.annotations.DtoNotBlank;
import jfvb.com.pitangbackend.core.annotations.DtoNotNull;
import jfvb.com.pitangbackend.core.annotations.DtoPattern;
import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import jfvb.com.pitangbackend.dataprovider.database.entity.Car;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * - {@code id}: O identificador único do usuário.
 * - {@code firstName}: O primeiro nome do usuário, com pelo menos 3 caracteres alfabéticos ou espaços.
 * - {@code lastName}: O sobrenome do usuário, com pelo menos 3 caracteres alfabéticos ou espaços.
 * - {@code email}: O endereço de e-mail do usuário, seguindo o formato de um endereço de e-mail válido.
 * - {@code birthday}: A data de nascimento do usuário.
 * - {@code login}: O nome de usuário do usuário.
 * - {@code password}: A senha do usuário.
 * - {@code phone}: O número de telefone do usuário, com apenas números, parênteses, espaços e hifens, com um mínimo de 7 e máximo de 12 caracteres.
 * - {@code cars}: Uma lista de objetos {@link CarDto} associados ao usuário.
 * - {@code createdAt}: A data e hora de criação do registro do usuário.
 * - {@code lastLogin}: A data e hora do último login do usuário.
 * - {@code active}: Um booleano que indica se a conta do usuário está ativa ou não.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AccountUserDto(
        Long id,
        @DtoNotNull
        @DtoNotBlank
        @DtoPattern("^[\\p{L}\\s]{3,}$")
        String firstName,
        @DtoNotNull
        @DtoNotBlank
        @DtoPattern("^[\\p{L}\\s]{3,}$")
        String lastName,
        @DtoNotNull
        @DtoNotBlank
        @DtoPattern("^[A-Za-z0-9+_.-]+@(.+)$")
        String email,
        @DtoNotNull
        LocalDate birthday,
        @DtoNotNull
        @DtoNotBlank
        String login,
        @DtoNotNull
        @DtoNotBlank
        String password,
        @DtoNotNull
        @DtoNotBlank
        @DtoPattern("^[0-9() -]{7,12}$")
        String phone,
        List<CarDto> cars,
        LocalDateTime createdAt,
        LocalDateTime lastLogin,
        Boolean active
) {

    public AccountUserDto(AccountUser accountUser) {
        this(
                accountUser.getId(),
                accountUser.getFirstName(),
                accountUser.getLastName(),
                accountUser.getEmail(),
                accountUser.getBirthday(),
                accountUser.getLogin(),
                null,
                accountUser.getPhone(),
                accountUser.getCars().stream()
                        .map(CarDto::new)
                        .toList(),
                null,
                null,
                isNull(accountUser.getActive()) || accountUser.getActive()
        );
    }

    public AccountUserDto(AccountUser accountUser, List<Car> sortedCars) {
        this(
                accountUser.getId(),
                accountUser.getFirstName(),
                accountUser.getLastName(),
                accountUser.getEmail(),
                accountUser.getBirthday(),
                accountUser.getLogin(),
                null,
                accountUser.getPhone(),
                sortedCars.stream()
                        .map(CarDto::new)
                        .toList(),
                null,
                null,
                isNull(accountUser.getActive()) || accountUser.getActive()
        );
    }
}
