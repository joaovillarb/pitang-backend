package jfvb.com.pitangbackend.dataprovider.database.gateway;

import jfvb.com.pitangbackend.core.exception.NotFoundException;
import jfvb.com.pitangbackend.core.gateway.CarGateway;
import jfvb.com.pitangbackend.dataprovider.database.entity.Car;
import jfvb.com.pitangbackend.dataprovider.database.repository.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CarGatewayImpl implements CarGateway {

    private final CarRepository carRepository;

    public CarGatewayImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car getById(Long id) {
        return this.carRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Car not found with id=%s", id), 404));
    }

    public Car save(Car car) {
        return this.carRepository.saveAndFlush(car);
    }

    public Page<Car> pageBy(Pageable pageable) {
        return this.carRepository.findAll(pageable);
    }

    public void delete(Long id) {
        this.carRepository.deleteById(id);
        this.carRepository.flush();
    }

    public boolean existsByLicensePlate(String licensePlate) {
        return this.carRepository.existsByLicensePlate(licensePlate);
    }

}
