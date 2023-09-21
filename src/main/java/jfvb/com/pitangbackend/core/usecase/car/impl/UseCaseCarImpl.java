package jfvb.com.pitangbackend.core.usecase.car.impl;

import jfvb.com.pitangbackend.core.exception.AlreadyExistsException;
import jfvb.com.pitangbackend.core.exception.UnauthorizedException;
import jfvb.com.pitangbackend.core.gateway.AccountUserGateway;
import jfvb.com.pitangbackend.core.gateway.CarGateway;
import jfvb.com.pitangbackend.core.usecase.car.UseCaseCar;
import jfvb.com.pitangbackend.core.validator.FieldsValidator;
import jfvb.com.pitangbackend.dataprovider.database.entity.AccountUser;
import jfvb.com.pitangbackend.dataprovider.database.entity.Car;
import jfvb.com.pitangbackend.entrypoint.dto.CarDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class UseCaseCarImpl implements UseCaseCar {

    private final CarGateway carGateway;
    private final AccountUserGateway accountUserGateway;

    public UseCaseCarImpl(CarGateway carGateway,
                          AccountUserGateway accountUserGateway) {
        this.carGateway = carGateway;
        this.accountUserGateway = accountUserGateway;
    }

    public CarDto getById(Long id, Long userId) {
        Car carFounded = getCarForLoggedInUser(id, userId);
        return new CarDto(carFounded);
    }

    public CarDto create(CarDto carDto, Long userId) {
        FieldsValidator.validate(carDto);

        if (this.carGateway.existsByLicensePlate(carDto.licensePlate())) {
            throw new AlreadyExistsException("License Plate already exists", 3);
        }

        final AccountUser user = accountUserGateway.getById(userId);
        final Car car = new Car(carDto, user);

        Car saved = this.carGateway.save(car);
        return new CarDto(saved);
    }

    public CarDto update(Long id, CarDto car, Long userId) {
        FieldsValidator.validate(car);
        Car carFounded = getCarForLoggedInUser(id, userId);
        Car saved = this.carGateway.save(carFounded.update(car));
        return new CarDto(saved);
    }

    public CarDto patch(Long id, CarDto car, Long userId) {
        Car carFounded = getCarForLoggedInUser(id, userId);
        Car saved = this.carGateway.save(carFounded.patch(car));
        return new CarDto(saved);
    }

    public void delete(Long id, Long userId) {
        Car carFounded = getCarForLoggedInUser(id, userId);
        this.carGateway.getById(carFounded.getId());
        this.carGateway.delete(carFounded.getId());
    }

    public Page<CarDto> pageBy(Pageable pageable) {
        return this.carGateway.pageBy(pageable)
                .map(CarDto::new);
    }

    private Car getCarForLoggedInUser(Long id, Long userId) {
        final AccountUser user = accountUserGateway.getById(userId);
        Car carFounded = this.carGateway.getById(id);

        if (!carFounded.getAccountUser().equals(user)) {
            throw new UnauthorizedException("User not authorized to make changes on this car", 10);
        }
        return carFounded;
    }
}
