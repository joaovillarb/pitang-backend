package jfvb.com.pitangbackend.core.gateway;

import jfvb.com.pitangbackend.dataprovider.database.entity.Car;

import java.util.List;

public interface CarGateway extends AbstractGateway<Car, Long> {

    boolean existsByLicensePlate(String licensePlate);

    List<Car> findAllByAccountUserId(Long userId);

    Car getByIdAndAccountUserId(Long id, Long userId);
}
