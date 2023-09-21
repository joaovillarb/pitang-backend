package jfvb.com.pitangbackend.infrastructure.config;

import jfvb.com.pitangbackend.core.gateway.AccountUserGateway;
import jfvb.com.pitangbackend.core.gateway.CarGateway;
import jfvb.com.pitangbackend.core.usecase.car.UseCaseCar;
import jfvb.com.pitangbackend.core.usecase.car.impl.UseCaseCarImpl;
import jfvb.com.pitangbackend.core.usecase.user.UseCaseAccountUser;
import jfvb.com.pitangbackend.core.usecase.user.impl.UseCaseAccountUserImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    private final AccountUserGateway accountUserGateway;
    private final CarGateway carGateway;

    public UseCaseConfig(AccountUserGateway accountUserGateway,
                         CarGateway carGateway) {
        this.accountUserGateway = accountUserGateway;
        this.carGateway = carGateway;
    }

    @Bean
    @ConditionalOnMissingBean(UseCaseAccountUser.class)
    public UseCaseAccountUser useCaseUser() {
        return new UseCaseAccountUserImpl(this.accountUserGateway);
    }

    @Bean
    @ConditionalOnMissingBean(UseCaseCar.class)
    public UseCaseCar useCaseCar() {
        return new UseCaseCarImpl(this.carGateway, this.accountUserGateway);
    }

}
