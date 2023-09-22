package jfvb.com.pitangbackend.dataprovider.database.repository;

import jfvb.com.pitangbackend.dataprovider.database.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {

    boolean existsByLicensePlate(String licensePlate);

    List<Car> findAllByAccountUserIdOrderByUsageCountDesc(Long userId);

    Optional<Car> findByIdAndAccountUserId(Long id, Long userId);
}
