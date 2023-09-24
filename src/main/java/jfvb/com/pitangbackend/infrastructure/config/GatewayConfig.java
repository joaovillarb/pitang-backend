package jfvb.com.pitangbackend.infrastructure.config;

import jfvb.com.pitangbackend.core.gateway.AccountUserGateway;
import jfvb.com.pitangbackend.core.gateway.CarGateway;
import jfvb.com.pitangbackend.dataprovider.database.gateway.AccountUserGatewayImpl;
import jfvb.com.pitangbackend.dataprovider.database.gateway.CarGatewayImpl;
import jfvb.com.pitangbackend.dataprovider.database.repository.AccountUserRepository;
import jfvb.com.pitangbackend.dataprovider.database.repository.CarRepository;
import jfvb.com.pitangbackend.dataprovider.security.SecurityGateway;
import jfvb.com.pitangbackend.dataprovider.security.impl.SecurityGatewayImpl;
import jfvb.com.pitangbackend.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AccountUserRepository accountUserRepository;
    private final CarRepository carRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Bean
    @ConditionalOnMissingBean(AccountUserGateway.class)
    public AccountUserGateway accountUserGateway() {
        return new AccountUserGatewayImpl(
                this.accountUserRepository
        );
    }

    @Bean
    @ConditionalOnMissingBean(CarGateway.class)
    public CarGateway carGateway() {
        return new CarGatewayImpl(
                this.carRepository
        );
    }

    @Bean
    @ConditionalOnMissingBean(SecurityGateway.class)
    public SecurityGateway securityGateway() {
        return new SecurityGatewayImpl(
                accountUserGateway(), this.authenticationManager, this.jwtService
        );
    }
}
