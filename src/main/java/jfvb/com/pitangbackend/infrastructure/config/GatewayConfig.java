package jfvb.com.pitangbackend.infrastructure.config;

import jfvb.com.pitangbackend.core.gateway.AccountUserGateway;
import jfvb.com.pitangbackend.dataprovider.database.gateway.AccountUserGatewayImpl;
import jfvb.com.pitangbackend.dataprovider.database.repository.AccountUserRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final AccountUserRepository accountUserRepository;

    public GatewayConfig(AccountUserRepository accountUserRepository) {
        this.accountUserRepository = accountUserRepository;
    }

    @Bean
    @ConditionalOnMissingBean(AccountUserGateway.class)
    public AccountUserGateway accountUserGateway() {
        return new AccountUserGatewayImpl(
                this.accountUserRepository
        );
    }
}
