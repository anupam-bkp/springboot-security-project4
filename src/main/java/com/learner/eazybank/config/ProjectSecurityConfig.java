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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

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

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails userDetails1 =
                User.withUsername("user")
//                        .password("{noop}12345") //used to use noop passwordEncoder
                        .password("$2a$12$ChpNJ.g8D4DpuZhznsSnfOeD.9WxdYpOC64iaG2KE3DQkLDFQ1GgC")
//                        .password("password-in-brypt-format")  //used to use Bcrypt passwordEncoder
                        .authorities("read").build();
        UserDetails userDetails2 =
                User.withUsername("admin")
//                        .password("{noop}54321")
                        .password("$2a$12$2oV.eOTshycPHLUXQ1JZOeXXZkEKFwJg5D8Z4m/reoCRn2GSYHKYu")
//                        .password("{bcrypt}$2a$12$2oV.eOTshycPHLUXQ1JZOeXXZkEKFwJg5D8Z4m/reoCRn2GSYHKYu")
                        .authorities("admin").build();

        return new InMemoryUserDetailsManager(userDetails1, userDetails2);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        //Default PasswordEncoder - BCrypt
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); //Not Working.... Check
        return new BCryptPasswordEncoder(12);
    }

    /*@Bean
    public CompromisedPasswordChecker compromisedPasswordChecker(){
        //Makes to call to api from https://haveibeenpwned.com/API/v3#PwnedPasswords
//        "https://api.pwnedpasswords.com/range/";

        return new HaveIBeenPwnedRestApiPasswordChecker();
    }*/

}
