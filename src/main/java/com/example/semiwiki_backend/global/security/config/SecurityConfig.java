package com.example.semiwiki_backend.global.security.config;

import com.example.semiwiki_backend.global.security.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {


  private final JwtTokenFilter jwtTokenFilter;

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

            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
        .cors(cors ->{})

        //경로별 권한설정
        .authorizeHttpRequests(authorize->authorize
            .requestMatchers("/notice-board/post", "/notice-board/delete", "/notice-board/put").authenticated()
            .requestMatchers("/like/{board_id}/count").permitAll()
            .requestMatchers("/like/").authenticated()
            .requestMatchers("/admin/").hasRole("ADMIN")
            .requestMatchers("/user/").hasAnyRole("USER", "ADMIN")
            .anyRequest().permitAll())
        .build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOrigins(List.of(
            "http://localhost:5173"
    ));

    configuration.setAllowedHeaders(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }
}


