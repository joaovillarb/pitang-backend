package jfvb.com.pitangbackend.dataprovider.database.entity;

import jfvb.com.pitangbackend.entrypoint.dto.CarDto;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Car extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer year;
    private String licensePlate;
    private String model;
    private String color;
    @ManyToOne
    private AccountUser accountUser;
    private Integer usageCount;

    public Car(CarDto car, AccountUser user) {
        this(
                car.id(),
                car.year(),
                car.licensePlate(),
                car.model(),
                car.color(),
                user,
                Objects.isNull(car.usageCount()) ? 0 : car.usageCount()
        );
    }

    public Car update(CarDto car) {
        setYear(car.year());
        setLicensePlate(car.licensePlate());
        setModel(car.model());
        setColor(car.color());
        return this;
    }

    public Car patch(CarDto car) {
        if (Objects.nonNull(car.year())) {
            setYear(car.year());
        }
        if (Objects.nonNull(car.licensePlate())) {
            setLicensePlate(car.licensePlate());
        }
        if (Objects.nonNull(car.model())) {
            setModel(car.model());
        }
        if (Objects.nonNull(car.color())) {
            setColor(car.color());
        }
        return this;
    }

    public Car increaseUsage() {
        this.usageCount += 1;
        return this;
    }
}
