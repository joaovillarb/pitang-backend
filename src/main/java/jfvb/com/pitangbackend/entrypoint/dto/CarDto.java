package jfvb.com.pitangbackend.entrypoint.dto;

import jfvb.com.pitangbackend.core.annotations.DtoNotBlank;
import jfvb.com.pitangbackend.core.annotations.DtoNotNull;
import jfvb.com.pitangbackend.core.annotations.DtoPattern;
import jfvb.com.pitangbackend.dataprovider.database.entity.Car;

import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * - {@code id}: O identificador único do carro.
 * - {@code year}: O ano de fabricação do carro.
 * - {@code licensePlate}: A placa de licença do carro, com até 9 caracteres.
 * - {@code model}: O modelo do carro, que pode conter letras e espaços.
 * - {@code color}: A cor do carro, que pode conter letras e espaços.
 * - {@code usageCount}: O número de vezes que o carro foi usado.
 * - {@code active}: Um booleano que indica se o carro está ativo ou não.
 */
public record CarDto(
        Long id,
        @DtoNotNull
        @DtoNotBlank
        Integer year,
        @DtoNotNull
        @DtoNotBlank
        @DtoPattern("^[\\s\\S]{1,9}$")
        String licensePlate,
        @DtoNotNull
        @DtoNotBlank
        @DtoPattern("^[\\p{L}\\s]+$")
        String model,
        @DtoNotNull
        @DtoNotBlank
        @DtoPattern("^[\\p{L}\\s]+$")
        String color,
        Integer usageCount,
        Boolean active) {

    public CarDto(Car car) {
        this(
                car.getId(),
                car.getProductionYear(),
                car.getLicensePlate(),
                car.getModel(),
                car.getColor(),
                Objects.isNull(car.getUsageCount()) ? 0 : car.getUsageCount(),
                isNull(car.getActive()) || car.getActive()
        );
    }
}
