package jfvb.com.pitangbackend.entrypoint.api;

import jfvb.com.pitangbackend.core.usecase.user.UseCaseAccountUser;
import jfvb.com.pitangbackend.entrypoint.dto.AccountUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class AccountUserApi {

    private final UseCaseAccountUser useCaseAccountUser;

    AccountUserApi(UseCaseAccountUser useCaseAccountUser) {
        this.useCaseAccountUser = useCaseAccountUser;
    }

    @GetMapping
    public List<AccountUserDto> findAll() {
        return useCaseAccountUser.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountUserDto> getById(@PathVariable Long id) {
        final AccountUserDto response = useCaseAccountUser.getById(id);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<AccountUserDto> create(@RequestBody AccountUserDto accountUser) {
        final AccountUserDto response = useCaseAccountUser.create(accountUser);

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
    public ResponseEntity<AccountUserDto> update(@PathVariable Long id, @RequestBody AccountUserDto accountUser) {
        final AccountUserDto response = useCaseAccountUser.update(id, accountUser);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AccountUserDto> patch(@PathVariable Long id, @RequestBody AccountUserDto accountUser) {
        final AccountUserDto response = useCaseAccountUser.patch(id, accountUser);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        useCaseAccountUser.delete(id);
        return ResponseEntity.noContent().build();
    }

}
