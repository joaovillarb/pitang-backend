package jfvb.com.pitangbackend.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jfvb.com.pitangbackend.infrastructure.security.handler.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityFilter securityFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    @ConditionalOnMissingBean(SecurityFilterChain.class)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()


                .antMatchers(
                        "/signin",
                        "/users",
                        "/users/**",
                        "/h2-console",
                        "/h2-console/**",
                        "/actuator/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                )
                .permitAll()


                .antMatchers(
                        "/me",
                        "/cars",
                        "/cars/**"
                ).hasAnyRole("USER")

                .anyRequest().authenticated()

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())

                .and().cors().configurationSource(corsConfigurationSource)

                .and()
                .headers().frameOptions().disable()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAccessDeniedHandler(new ObjectMapper());
    }

}
