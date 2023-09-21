package jfvb.com.pitangbackend.infrastructure.config;

import jfvb.com.pitangbackend.core.gateway.AccountUserGateway;
import jfvb.com.pitangbackend.core.usecase.user.UseCaseAccountUser;
import jfvb.com.pitangbackend.core.usecase.user.impl.UseCaseAccountUserImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    private final AccountUserGateway accountUserGateway;

    public UseCaseConfig(AccountUserGateway accountUserGateway) {
        this.accountUserGateway = accountUserGateway;
    }

    @Bean
    @ConditionalOnMissingBean(UseCaseAccountUser.class)
    public UseCaseAccountUser useCaseUser() {
        return new UseCaseAccountUserImpl(this.accountUserGateway);
    }

}
