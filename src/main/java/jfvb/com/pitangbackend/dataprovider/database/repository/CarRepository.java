package jfvb.com.pitangbackend.dataprovider.database.repository;

import jfvb.com.pitangbackend.dataprovider.database.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

    boolean existsByLicensePlate(String licensePlate);
}
