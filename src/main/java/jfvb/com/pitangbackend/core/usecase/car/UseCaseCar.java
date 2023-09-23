package jfvb.com.pitangbackend.core.usecase.car;

import jfvb.com.pitangbackend.entrypoint.dto.CarDto;

import java.util.List;

public interface UseCaseCar {
    CarDto getByIdAndIncreaseUsage(Long id, Long userId);

    CarDto create(CarDto car, Long userId);

    CarDto update(Long id, CarDto car, Long userId);

    CarDto patch(Long id, CarDto car, Long userId);

    void delete(Long id, Long userId);

    List<CarDto> findAllByLoggedInUser(Long userId);
}
