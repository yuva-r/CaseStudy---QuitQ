
package com.hexaware.QuitQ.configuration;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hexaware.QuitQ.security.JwtAuthenticationEntryPoint;
import com.hexaware.QuitQ.security.JwtAuthenticationFilter;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationEntryPoint authenticationEntryPoint,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
    	logger.info("Securitycongig cons triggered.......");
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        logger.info("SecurityConfig constructor initialized.");
    }

    @Bean
    //creates instance managed by string
    public ModelMapper modelMapperBean() {
        logger.info("ModelMapper bean created...........");
        return new ModelMapper();
    }
    // creating bean for converting entity to dto
    @Bean
    //for authenticating credentials to authenticate user login
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        logger.info("AuthenticationManager bean created.....");
        return configuration.getAuthenticationManager();
    }

    @Bean
    //needed by spring security for encoding passwords
    public static PasswordEncoder passwordEncoder() {
        logger.info("PasswordEncoder bean (BCrypt) created......");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        logger.info("SecurityFilterChain configuration started.......");

        httpSecurity.csrf().disable()

            .authorizeHttpRequests((authorize) -> 
            authorize
            .requestMatchers("/api/**").permitAll()
                .requestMatchers("/api/authenticate/**").permitAll()
                .requestMatchers("/api/v1/employees/delete/**").permitAll()
                .requestMatchers("/api/v1/products/**").permitAll()
                .anyRequest().authenticated())
            .exceptionHandling(exception -> 
                exception.authenticationEntryPoint(authenticationEntryPoint))
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        logger.info("SecurityFilterChain configured successfully.......");
        return httpSecurity.build();
    }
}