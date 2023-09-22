package jfvb.com.pitangbackend.entrypoint.dto;

import jfvb.com.pitangbackend.dataprovider.database.entity.Car;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public record CarDto(
        Long id,
        @NotBlank
        Integer year,
        @NotBlank
        String licensePlate,
        @NotBlank
        String model,
        @NotBlank
        String color,
        Integer usageCount) {

    public CarDto(Car car) {
        this(
                car.getId(),
                car.getProductionYear(),
                car.getLicensePlate(),
                car.getModel(),
                car.getColor(),
                Objects.isNull(car.getUsageCount()) ? 0 : car.getUsageCount()
        );
    }
}
