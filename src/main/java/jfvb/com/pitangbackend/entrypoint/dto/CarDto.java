package jfvb.com.pitangbackend.entrypoint.dto;

import jfvb.com.pitangbackend.dataprovider.database.entity.Car;

import javax.validation.constraints.NotBlank;

public record CarDto(
        Long id,
        @NotBlank
        Integer year,
        @NotBlank
        String licensePlate,
        @NotBlank
        String model,
        @NotBlank
        String color) {

    public CarDto(Car car) {
        this(
                car.getId(),
                car.getYear(),
                car.getLicensePlate(),
                car.getModel(),
                car.getColor()
        );
    }
}
