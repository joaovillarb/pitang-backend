package jfvb.com.pitangbackend.infrastructure.config;

import jfvb.com.pitangbackend.core.gateway.AccountUserGateway;
import jfvb.com.pitangbackend.core.gateway.CarGateway;
import jfvb.com.pitangbackend.dataprovider.database.gateway.AccountUserGatewayImpl;
import jfvb.com.pitangbackend.dataprovider.database.gateway.CarGatewayImpl;
import jfvb.com.pitangbackend.dataprovider.database.repository.AccountUserRepository;
import jfvb.com.pitangbackend.dataprovider.database.repository.CarRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final AccountUserRepository accountUserRepository;
    private final CarRepository carRepository;

    public GatewayConfig(AccountUserRepository accountUserRepository,
                         CarRepository carRepository) {
        this.accountUserRepository = accountUserRepository;
        this.carRepository = carRepository;
    }

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
}
