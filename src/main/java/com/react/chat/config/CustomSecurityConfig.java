package com.react.chat.config;

import com.react.chat.security.filter.JWTCheckFilter;
import com.react.chat.security.handler.CustomAccessDeniedHandler;
import com.react.chat.security.handler.CustomLoginFailureHandler;
import com.react.chat.security.handler.CustomLoginSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@Slf4j
@EnableMethodSecurity
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
      login.loginPage("/login"); // 로그인 경로
      // 로그인 성공시 실행될 로직 클래스
      login.successHandler(new CustomLoginSuccessHandler());
      // 로그인 실패시 실행될 로직 클래스
      login.failureHandler(new CustomLoginFailureHandler());
    });

    http.addFilterBefore(new JWTCheckFilter(), UsernamePasswordAuthenticationFilter.class);

    // 접근 제한(허용X) 되었을 경우 예외 처리
    http.exceptionHandling(exception -> {
      exception.accessDeniedHandler(new CustomAccessDeniedHandler());
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
