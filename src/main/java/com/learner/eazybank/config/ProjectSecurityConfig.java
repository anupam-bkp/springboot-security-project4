package com.learner.eazybank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import javax.sql.DataSource;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

//        httpSecurity.authorizeHttpRequests(requests -> requests.anyRequest().permitAll());
//        httpSecurity.authorizeHttpRequests(requests -> requests.anyRequest().denyAll());

        httpSecurity.authorizeHttpRequests(requests -> requests
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
                .requestMatchers("/notices", "/contact", "/error").permitAll());

        httpSecurity.formLogin(Customizer.withDefaults()); //disabled default Form login
        httpSecurity.httpBasic(Customizer.withDefaults());  //http basic with password encoded in Base 64
        return httpSecurity.build();
    }

    /*@Bean
    public UserDetailsService userDetailsService(){
        UserDetails userDetails1 =
                User.withUsername("user")
//                        .password("{noop}12345") //used to use noop PasswordEncoder
                           .authorities("read").build();
        UserDetails userDetails2 =
                User.withUsername("admin")
//                        .password("{noop}54321")
                        .password("$2a$12$2oV.eOTshycPHLUXQ1JZOeXXZkEKFwJg5D8Z4m/reoCRn2GSYHKYu")
//                       .password("{bcrypt}$2a$12$2oV.eOTshycPHLUXQ1JZOeXXZkEKFwJg5D8Z4m/reoCRn2GSYHKYu")
                        .authorities("admin").build();

        return new InMemoryUserDetailsManager(userDetails1, userDetails2);
    }*/

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        //To work with factory use {bcrypt}password-in-bcrypt-format, {noop}password-in-plain-text
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); //if want to use distinct encoders.
//        return new BCryptPasswordEncoder(12); //if only want to use only bcrypt password encoder
    }

    /*@Bean
    public CompromisedPasswordChecker compromisedPasswordChecker(){
        //Makes to call to api from https://haveibeenpwned.com/API/v3#PwnedPasswords
//        "https://api.pwnedpasswords.com/range/";

        return new HaveIBeenPwnedRestApiPasswordChecker();
    }*/

}
