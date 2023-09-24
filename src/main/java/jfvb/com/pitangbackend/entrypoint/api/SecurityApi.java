package jfvb.com.pitangbackend.entrypoint.api;

import jfvb.com.pitangbackend.dataprovider.security.SecurityGateway;
import jfvb.com.pitangbackend.entrypoint.dto.AccountUserDto;
import jfvb.com.pitangbackend.entrypoint.dto.AuthenticationRequest;
import jfvb.com.pitangbackend.entrypoint.dto.AuthenticationResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class SecurityApi {

    private final SecurityGateway useCase;

    @PostMapping("signin")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(useCase.authenticate(authenticationRequest));
    }

    @GetMapping("me")
    public ResponseEntity<AccountUserDto> me() {
        return ResponseEntity.ok(useCase.me());
    }

}
