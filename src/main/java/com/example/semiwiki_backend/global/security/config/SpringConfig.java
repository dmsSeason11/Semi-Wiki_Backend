package com.example.semiwiki_backend.global.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
public class SpringConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(csrf->csrf.disable())
        .logout(logout->logout.disable())
        .formLogin(formLogin->formLogin.disable())
        .httpBasic(httpbasic->httpbasic.disable())

        .sessionManagement(sessionManagement ->sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(exception->exception.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))

        //addfilterBefore설정해야함
        .cors(cors ->{})

        //경로별 권한설정
        .authorizeHttpRequests(authorize->authorize
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/user.**").hasAnyRole("USER", "ADMIN")
            .anyRequest().permitAll())

        .build();
  }
}


