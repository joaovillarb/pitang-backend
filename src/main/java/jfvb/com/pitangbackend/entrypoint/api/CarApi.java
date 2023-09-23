package jfvb.com.pitangbackend.entrypoint.api;

import jfvb.com.pitangbackend.core.usecase.car.UseCaseCar;
import jfvb.com.pitangbackend.entrypoint.dto.CarDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/cars")
public class CarApi {

    private final UseCaseCar useCaseCar;

    CarApi(UseCaseCar useCaseCar) {
        this.useCaseCar = useCaseCar;
    }

    @GetMapping
    public ResponseEntity<List<CarDto>> findAllByLoggedInUser(@RequestHeader("Authorization") String token) {
        final var response = useCaseCar.findAllByLoggedInUser(Long.parseLong(token));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getByIdAndLoggedInUser(@RequestHeader("Authorization") String token,
                                                         @PathVariable Long id) {
        final var response = useCaseCar.getByIdAndIncreaseUsage(id, Long.parseLong(token));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CarDto> create(@RequestHeader("Authorization") String token,
                                         @RequestBody CarDto car) {
        final var response = useCaseCar.create(car, Long.parseLong(token));

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
    public ResponseEntity<CarDto> update(@RequestHeader("Authorization") String token,
                                         @PathVariable Long id, @RequestBody CarDto car) {
        final var response = useCaseCar.update(id, car, Long.parseLong(token));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CarDto> patch(@RequestHeader("Authorization") String token,
                                        @PathVariable Long id, @RequestBody CarDto car) {
        final var response = useCaseCar.patch(id, car, Long.parseLong(token));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestHeader("Authorization") String token,
                                       @PathVariable Long id) {
        useCaseCar.delete(id, Long.parseLong(token));
        return ResponseEntity.noContent().build();
    }

}
