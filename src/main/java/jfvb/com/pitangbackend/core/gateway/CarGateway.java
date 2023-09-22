package jfvb.com.pitangbackend.core.gateway;

import jfvb.com.pitangbackend.dataprovider.database.entity.Car;

import java.util.List;

public interface CarGateway {

    Car getById(Long id);

    Car save(Car car);

    void delete(Long id);

    boolean existsByLicensePlate(String licensePlate);

    List<Car> findAllByAccountUserId(Long userId);

    Car getByIdAndAccountUserId(Long id, Long userId);
}
