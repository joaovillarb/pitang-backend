package jfvb.com.pitangbackend.core.usecase.car.impl;

import jfvb.com.pitangbackend.BaseUnitTest;
import jfvb.com.pitangbackend.core.exception.AlreadyExistsException;
import jfvb.com.pitangbackend.core.gateway.CarGateway;
import jfvb.com.pitangbackend.core.usecase.car.UseCaseCar;
import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import jfvb.com.pitangbackend.dataprovider.database.entity.Car;
import jfvb.com.pitangbackend.entrypoint.dto.CarDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UseCaseCarImplTest extends BaseUnitTest {

    private final CarGateway carGateway = mock(CarGateway.class);
    private final UseCaseCar useCase = new UseCaseCarImpl(carGateway);
    private final Authentication authentication = Mockito.mock(Authentication.class);
    private final SecurityContext securityContext = Mockito.mock(SecurityContext.class);

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    void getById() {
        // GIVEN
        final var carId = 1L;
        final var accountId = 1L;
        final var car = toCarDto(carId);
        final var accountUser = new AccountUser(toAccountUserDto(accountId));
        final var carEntity = new Car(car, accountUser);

        when(authentication.getPrincipal())
                .thenReturn(accountUser);

        given(carGateway.save(any(Car.class)))
                .willReturn(carEntity);

        // WHEN
        final CarDto response = this.useCase.getByIdAndIncreaseUsage(car.id());

        // THEN
        assertThat(response).isNotNull();
        assertThat(response)
                .usingRecursiveComparison()
                .ignoringFields("usageCount")
                .isEqualTo(car);
    }

    @Test
    void shouldCreateCar() {
        // GIVEN
        final CarDto car = toCarDto(null);
        final var accountUser = new AccountUser(toAccountUserDto(1L));
        final var carEntity = new Car(car, accountUser);

        when(authentication.getPrincipal())
                .thenReturn(accountUser);

        given(this.carGateway.save(any(Car.class)))
                .willReturn(carEntity);

        // WHEN
        final CarDto response = this.useCase.create(car);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(car);
    }

    @Test
    void shouldUpdateCar() {
        // GIVEN
        final var accountId = 1L;
        final var accountUser = new AccountUser(toAccountUserDto(accountId));
        final CarDto updatedCarDto = new CarDto(
                1L,
                2023,
                "ANO-2023",
                "Ford",
                "Grey",
                0
        );

        final Car updatedCarEntity = new Car(updatedCarDto, accountUser);

        when(authentication.getPrincipal())
                .thenReturn(accountUser);

        given(carGateway.save(any(Car.class)))
                .willReturn(updatedCarEntity);

        // WHEN
        final CarDto response = useCase.update(updatedCarDto.id(), updatedCarDto);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(updatedCarDto);
    }

    @Test
    void shouldPatchCar() {
        // GIVEN
        final Long accountId = 1L;
        final var accountUser = new AccountUser(toAccountUserDto(accountId));
        final CarDto updatedCarDto = new CarDto(
                1L,
                2023,
                "ANO-2024",
                "Ford Ka",
                "Grey",
                0
        );

        final Car updatedCarEntity = new Car(updatedCarDto, accountUser);

        when(authentication.getPrincipal())
                .thenReturn(accountUser);

        given(carGateway.save(any(Car.class)))
                .willReturn(updatedCarEntity);

        // WHEN
        final CarDto response = useCase.patch(updatedCarDto.id(), updatedCarDto);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(updatedCarDto);
    }

    @Test
    void shouldDeleteCar() {
        // GIVEN
        final var carId = 1L;

        when(authentication.getPrincipal())
                .thenReturn(new AccountUser(toAccountUserDto(1L)));

        // WHEN
        useCase.delete(carId);

        // THEN
        verify(carGateway).delete(anyLong());
    }

    @Test
    void shouldThrownAlreadyExistsExceptionWhenTryTwoCreateAccountsWithSameEmail() {
        // GIVEN
        final var car = toCarDto(null);

        given(this.carGateway.existsByLicensePlate(car.licensePlate()))
                .willReturn(true);

        // WHEN
        final var exception = assertThrows(AlreadyExistsException.class,
                () -> this.useCase.create(car));

        // THEN
        assertThat(exception).isNotNull();
        verify(carGateway).existsByLicensePlate(car.licensePlate());
    }

    @Test
    void shouldFindAll() {
        // GIVEN
        final var carId = 1L;
        final var accountUser = new AccountUser(toAccountUserDto(carId));
        final var carDtoList = toCarDtoList(carId);
        final var carList = carDtoList.stream().map(car -> new Car(car, accountUser)).toList();
        accountUser.setCars(carList);

        when(authentication.getPrincipal())
                .thenReturn(accountUser);

        // WHEN
        final var response = this.useCase.findAllByLoggedInUser();

        // THEN
        assertThat(response).isNotNull();
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(carDtoList);
    }


}