package com.react.chat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@Slf4j
public class CustomSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    log.info("***** security config!");

    // cors
    http.cors(corsConfigurer -> {
      corsConfigurer.configurationSource(corsConfigurationSource());
    });
    // session stateless
    http.sessionManagement(sessionConfig ->
        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    // csrf disable
    http.csrf(csrf -> csrf.disable());

    http.formLogin(login -> {
      login.loginPage("/api/member/login");
    });

    return http.build();
  }

  // CORS 설정 정보
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(Arrays.asList("*")); // 리스트형태로 경로 패턴 추가 : * 전체
    configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
    configuration.setAllowCredentials(true);

    // 위 설정정보를 토대로 Url 전체 경로에 적용하는 CORS 설정 소스 생성해 리턴
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
