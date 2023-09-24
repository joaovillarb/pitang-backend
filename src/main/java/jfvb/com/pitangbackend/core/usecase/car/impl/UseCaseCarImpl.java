package jfvb.com.pitangbackend.core.usecase.car.impl;

import jfvb.com.pitangbackend.core.exception.AlreadyExistsException;
import jfvb.com.pitangbackend.core.exception.NotFoundException;
import jfvb.com.pitangbackend.core.gateway.CarGateway;
import jfvb.com.pitangbackend.core.usecase.car.UseCaseCar;
import jfvb.com.pitangbackend.core.validator.FieldsValidator;
import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import jfvb.com.pitangbackend.dataprovider.database.entity.Car;
import jfvb.com.pitangbackend.entrypoint.dto.CarDto;
import jfvb.com.pitangbackend.infrastructure.utils.SecurityUtils;

import java.util.List;

public class UseCaseCarImpl implements UseCaseCar {

    private final CarGateway carGateway;

    public UseCaseCarImpl(CarGateway carGateway) {
        this.carGateway = carGateway;
    }

    public List<CarDto> findAllByLoggedInUser() {
        AccountUser accountUser = SecurityUtils.getLoggedInUserId();
        return accountUser.getCars()
                .stream()
                .map(CarDto::new)
                .toList();
    }

    public CarDto getByIdAndIncreaseUsage(Long id) {
        var carFounded = recoverById(id)
                .increaseUsage();
        return persistAndCreateCarDto(carFounded);
    }

    public CarDto create(CarDto carDto) {
        FieldsValidator.validate(carDto);

        if (this.carGateway.existsByLicensePlate(carDto.licensePlate())) {
            throw new AlreadyExistsException("License Plate already exists", 3);
        }

        final var user = SecurityUtils.getLoggedInUserId();
        final var car = new Car(carDto, user);

        return persistAndCreateCarDto(car);
    }

    public CarDto update(Long id, CarDto car) {
        FieldsValidator.validate(car);
        var carFounded = recoverById(id);
        return persistAndCreateCarDto(carFounded.update(car));
    }

    public CarDto patch(Long id, CarDto car) {
        var carFounded = recoverById(id);
        return persistAndCreateCarDto(carFounded.patch(car));
    }

    public void delete(Long id) {
        var carFounded = recoverById(id);
        this.carGateway.delete(carFounded.getId());
    }

    private CarDto persistAndCreateCarDto(Car carFounded) {
        final var saved = this.carGateway.save(carFounded);
        return new CarDto(saved);
    }

    private Car recoverById(Long id) {
        AccountUser accountUser = SecurityUtils.getLoggedInUserId();
        return accountUser.getCars()
                .stream()
                .filter(car -> car.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Car not found with id=%s", id), 404));
    }
}
