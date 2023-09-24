package jfvb.com.pitangbackend.core.usecase.car;

import jfvb.com.pitangbackend.entrypoint.dto.CarDto;

import java.util.List;

public interface UseCaseCar {
    CarDto getByIdAndIncreaseUsage(Long id);

    CarDto create(CarDto car);

    CarDto update(Long id, CarDto car);

    CarDto patch(Long id, CarDto car);

    void delete(Long id);

    List<CarDto> findAllByLoggedInUser();
}
