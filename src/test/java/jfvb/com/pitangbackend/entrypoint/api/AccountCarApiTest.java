package jfvb.com.pitangbackend.entrypoint.api;

import jfvb.com.pitangbackend.BaseControllerUnitTest;
import jfvb.com.pitangbackend.core.usecase.car.UseCaseCar;
import jfvb.com.pitangbackend.entrypoint.dto.CarDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

class AccountCarApiTest extends BaseControllerUnitTest {

    private final UseCaseCar useCase = mock(UseCaseCar.class);
    private final CarApi api = new CarApi(this.useCase);

    @Test
    void getById() {
        // GIVEN
        final CarDto car = toCarDto(1L);

        given(this.useCase.getByIdAndIncreaseUsage(1L))
                .willReturn(car);

        // WHEN
        final ResponseEntity<CarDto> response = this.api.getByIdAndLoggedInUser(1L);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(car);
    }

    @Test
    void shouldFindAllCars() {
        // GIVEN
        final List<CarDto> cars = toCarDtoList(1L);

        given(this.useCase.findAllByLoggedInUser())
                .willReturn(cars);

        // WHEN
        final ResponseEntity<List<CarDto>> response = this.api.findAllByLoggedInUser();

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(cars);
    }


    @Test
    void shouldCreateCar() {
        // GIVEN
        final CarDto carDto = toCarDto(null);

        given(this.useCase.create(carDto))
                .willReturn(carDto);

        // WHEN
        final ResponseEntity<CarDto> response = this.api.create(carDto);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(carDto);
    }

    @Test
    void shouldUpdateCar() {
        // GIVEN
        final CarDto carDto = toCarDto(1L);

        given(this.useCase.update(1L, carDto))
                .willReturn(carDto);

        // WHEN
        final ResponseEntity<CarDto> response = this.api.update(1L, carDto);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(carDto);
    }

    @Test
    void shouldPatchCar() {
        // GIVEN
        final CarDto car = toCarDto(1L);

        given(this.useCase.patch(1L, car))
                .willReturn(car);

        // WHEN
        final ResponseEntity<CarDto> response = this.api.patch(1L, car);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .usingRecursiveComparison()
                .isEqualTo(car);
    }

    @Test
    void shouldDeleteCar() {
        // GIVEN
        doNothing().when(this.useCase)
                .delete(1L);

        // WHEN
        final ResponseEntity<Void> response = this.api.delete(1L);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}