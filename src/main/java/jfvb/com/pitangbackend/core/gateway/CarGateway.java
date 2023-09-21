package jfvb.com.pitangbackend.core.gateway;

import jfvb.com.pitangbackend.dataprovider.database.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarGateway {

    Car getById(Long id);

    Car save(Car car);

    Page<Car> pageBy(Pageable pageable);

    void delete(Long id);

    boolean existsByLicensePlate(String licensePlate);

}
