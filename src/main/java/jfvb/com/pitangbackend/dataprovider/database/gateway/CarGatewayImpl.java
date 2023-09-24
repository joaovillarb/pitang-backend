package jfvb.com.pitangbackend.dataprovider.database.gateway;

import jfvb.com.pitangbackend.core.exception.NotFoundException;
import jfvb.com.pitangbackend.core.gateway.CarGateway;
import jfvb.com.pitangbackend.dataprovider.database.entity.Car;
import jfvb.com.pitangbackend.dataprovider.database.repository.CarRepository;

import java.util.List;

public final class CarGatewayImpl extends AbstractGatewayImpl<Car, Long, CarRepository> implements CarGateway {

    public CarGatewayImpl(CarRepository carRepository) {
        super(carRepository, Car.class);
    }

    public boolean existsByLicensePlate(String licensePlate) {
        return this.repository.existsByLicensePlate(licensePlate);
    }

    public List<Car> findAllByAccountUserId(Long userId) {
        return this.repository.findAllByAccountUserIdOrderByUsageCountDesc(userId);
    }

    public Car getByIdAndAccountUserId(Long id, Long userId) {
        return this.repository.findByIdAndAccountUserId(id, userId)
                .orElseThrow(() -> new NotFoundException(String.format("Car not found with id=%s", id), 404));
    }

}
