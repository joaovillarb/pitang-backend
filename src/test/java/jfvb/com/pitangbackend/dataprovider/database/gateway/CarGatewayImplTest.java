package jfvb.com.pitangbackend.dataprovider.database.gateway;

import jfvb.com.pitangbackend.BaseUnitTest;
import jfvb.com.pitangbackend.core.exception.NotFoundException;
import jfvb.com.pitangbackend.core.gateway.CarGateway;
import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import jfvb.com.pitangbackend.dataprovider.database.entity.Car;
import jfvb.com.pitangbackend.dataprovider.database.repository.CarRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class CarGatewayImplTest extends BaseUnitTest {

    private final CarRepository carRepository = mock(CarRepository.class);
    private final CarGateway carGateway = new CarGatewayImpl(carRepository);

    @Test
    void shouldGetById() {
        // GIVEN
        final var userId = 1L;
        final var accountId = 1L;
        final var accountUser = new AccountUser(toAccountUserDto(accountId));
        final var car = new Car(toCarDto(userId), accountUser);

        given(carRepository.findById(userId))
                .willReturn(Optional.of(car));

        // WHEN
        final var response = carGateway.getById(userId);

        // THEN
        assertThat(response).isNotNull();
        assertThat(userId).isEqualTo(response.getId());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGetById() {
        // GIVEN
        final var userId = 1L;

        given(carRepository.findById(userId))
                .willReturn(Optional.empty());

        // WHEN
        final var exception = assertThrows(NotFoundException.class,
                () -> carGateway.getById(userId));

        // THEN
        assertThat(exception).isNotNull();
        verify(carRepository).findById(userId);
    }

    @Test
    void shouldSave() {
        // GIVEN
        final Long userId = 1L;
        final var accountId = 1L;
        final var accountUser = new AccountUser(toAccountUserDto(accountId));
        final var car = new Car(toCarDto(userId), accountUser);

        given(carRepository.saveAndFlush(car))
                .willReturn(car);

        // WHEN
        final Car response = carGateway.save(car);

        // THEN
        assertThat(response).isNotNull();
        assertThat(car).isEqualTo(response);
    }

    @Test
    void shouldDelete() {
        // GIVEN
        final Long carId = 1L;

        doNothing().when(carRepository).deleteById(carId);
        doNothing().when(carRepository).flush();

        // WHEN
        carGateway.delete(carId);

        // THEN
        verify(carRepository).deleteById(carId);
        verify(carRepository).flush();
    }

    @Test
    void shouldVerifyIfExistsByLicensePlate() {
        // GIVEN
        final String licensePlate = "ABC-1234";

        given(carRepository.existsByLicensePlate(licensePlate))
                .willReturn(true);

        // WHEN
        final boolean response = carGateway.existsByLicensePlate(licensePlate);

        // THEN
        assertThat(response).isTrue();
        verify(carRepository).existsByLicensePlate(licensePlate);
    }

    @Test
    void shouldFindAllByAccountUserId() {
        // GIVEN
        final var userId = 1L;
        final var carDtoList = toCarDtoList(1L);
        final var accountUser = new AccountUser(toAccountUserDto(1L));
        final var carList = carDtoList.stream().map(car -> new Car(car, accountUser)).toList();

        given(carRepository.findAllByAccountUserIdOrderByUsageCountDesc(userId))
                .willReturn(carList);

        // WHEN
        final var response = carGateway.findAllByAccountUserId(userId);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(carList);
    }

    @Test
    void shouldGetByIdAndAccountUserId() {
        // GIVEN
        long carId = 1L;
        long userId = 1L;
        final var accountUser = new AccountUser(toAccountUserDto(carId));
        final var car = new Car(toCarDto(carId), accountUser);

        given(carRepository.findByIdAndAccountUserId(carId, userId))
                .willReturn(Optional.of(car));

        // WHEN
        final var response = carGateway.getByIdAndAccountUserId(carId, userId);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(car);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenGetByIdAndAccountUserId() {
        // GIVEN
        given(carRepository.findByIdAndAccountUserId(anyLong(), anyLong()))
                .willReturn(Optional.empty());

        // WHEN
        final var exception = assertThrows(NotFoundException.class,
                () -> carGateway.getByIdAndAccountUserId(1L, 1L));

        // THEN
        assertThat(exception).isNotNull();
        verify(carRepository).findByIdAndAccountUserId(1L, 1L);
    }
}