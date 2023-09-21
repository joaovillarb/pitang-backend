package jfvb.com.pitangbackend.core.usecase.car;

import jfvb.com.pitangbackend.entrypoint.dto.CarDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UseCaseCar {
    CarDto getById(Long id, Long userId);

    CarDto create(CarDto car, Long userId);

    CarDto update(Long id, CarDto car, Long userId);

    CarDto patch(Long id, CarDto car, Long userId);

    void delete(Long id, Long userId);

    Page<CarDto> pageBy(Pageable pageable);
}
