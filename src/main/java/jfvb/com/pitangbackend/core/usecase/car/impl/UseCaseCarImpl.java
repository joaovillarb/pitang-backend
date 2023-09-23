package jfvb.com.pitangbackend.core.usecase.car.impl;

import jfvb.com.pitangbackend.core.exception.AlreadyExistsException;
import jfvb.com.pitangbackend.core.gateway.AccountUserGateway;
import jfvb.com.pitangbackend.core.gateway.CarGateway;
import jfvb.com.pitangbackend.core.usecase.car.UseCaseCar;
import jfvb.com.pitangbackend.core.validator.FieldsValidator;
import jfvb.com.pitangbackend.dataprovider.database.entity.Car;
import jfvb.com.pitangbackend.entrypoint.dto.CarDto;

import java.util.List;

public class UseCaseCarImpl implements UseCaseCar {

    private final CarGateway carGateway;
    private final AccountUserGateway accountUserGateway;

    public UseCaseCarImpl(CarGateway carGateway,
                          AccountUserGateway accountUserGateway) {
        this.carGateway = carGateway;
        this.accountUserGateway = accountUserGateway;
    }

    public List<CarDto> findAllByLoggedInUser(Long userId) {
        return this.carGateway.findAllByAccountUserId(userId)
                .stream()
                .map(CarDto::new)
                .toList();
    }

    public CarDto getByIdAndIncreaseUsage(Long id, Long userId) {
        var carFounded = recoverById(id, userId)
                .increaseUsage();
        return persistAndCreateCarDto(carFounded);
    }

    public CarDto create(CarDto carDto, Long userId) {
        FieldsValidator.validate(carDto);

        if (this.carGateway.existsByLicensePlate(carDto.licensePlate())) {
            throw new AlreadyExistsException("License Plate already exists", 3);
        }

        final var user = accountUserGateway.getById(userId);
        final var car = new Car(carDto, user);

        return persistAndCreateCarDto(car);
    }

    public CarDto update(Long id, CarDto car, Long userId) {
        FieldsValidator.validate(car);
        var carFounded = recoverById(id, userId);
        return persistAndCreateCarDto(carFounded.update(car));
    }

    public CarDto patch(Long id, CarDto car, Long userId) {
        var carFounded = recoverById(id, userId);
        return persistAndCreateCarDto(carFounded.patch(car));
    }

    public void delete(Long id, Long userId) {
        var carFounded = recoverById(id, userId);
        this.carGateway.delete(carFounded.getId());
    }

    private CarDto persistAndCreateCarDto(Car carFounded) {
        final var saved = this.carGateway.save(carFounded);
        return new CarDto(saved);
    }

    private Car recoverById(Long id, Long userId) {
        return this.carGateway.getByIdAndAccountUserId(id, userId);
    }
}
