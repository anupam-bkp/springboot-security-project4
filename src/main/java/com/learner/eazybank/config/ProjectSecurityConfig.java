package com.learner.eazybank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

//        httpSecurity.authorizeHttpRequests(requests -> requests.anyRequest().permitAll());
//        httpSecurity.authorizeHttpRequests(requests -> requests.anyRequest().denyAll());

        httpSecurity.authorizeHttpRequests(requests -> requests
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
                .requestMatchers("/notices", "/contact", "/error").permitAll());

//        httpSecurity.formLogin(Customizer.withDefaults()); //disabled default Form login
        httpSecurity.httpBasic(Customizer.withDefaults());  //http basic with password encoded in Base 64
        return httpSecurity.build();
    }

}
