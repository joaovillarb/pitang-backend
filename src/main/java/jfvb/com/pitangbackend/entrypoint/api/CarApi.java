package jfvb.com.pitangbackend.entrypoint.api;

import jfvb.com.pitangbackend.core.usecase.car.UseCaseCar;
import jfvb.com.pitangbackend.entrypoint.dto.CarDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/cars")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CarApi {

    private final UseCaseCar useCaseCar;

    @GetMapping
    public ResponseEntity<List<CarDto>> findAllByLoggedInUser() {
        final var response = useCaseCar.findAllByLoggedInUser();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getByIdAndLoggedInUser(@PathVariable Long id) {
        final var response = useCaseCar.getByIdAndIncreaseUsage(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CarDto> create(@RequestBody CarDto car) {
        final var response = useCaseCar.create(car);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> update(@PathVariable Long id, @RequestBody CarDto car) {
        final var response = useCaseCar.update(id, car);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CarDto> patch(@PathVariable Long id, @RequestBody CarDto car) {
        final var response = useCaseCar.patch(id, car);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        useCaseCar.delete(id);
        return ResponseEntity.noContent().build();
    }

}
