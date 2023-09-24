package jfvb.com.pitangbackend.dataprovider.security;

import jfvb.com.pitangbackend.entrypoint.dto.AccountUserDto;
import jfvb.com.pitangbackend.entrypoint.dto.AuthenticationRequest;
import jfvb.com.pitangbackend.entrypoint.dto.AuthenticationResponse;

public interface SecurityGateway {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    AccountUserDto me();
}
