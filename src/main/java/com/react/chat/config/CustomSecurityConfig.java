package com.react.chat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.react.chat.security.filter.JWTCheckFilter;
import com.react.chat.security.handler.CustomAccessDeniedHandler;
import com.react.chat.security.handler.CustomLoginFailureHandler;
import com.react.chat.security.handler.CustomLoginSuccessHandler;
import com.react.chat.security.handler.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@Slf4j
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class CustomSecurityConfig {

  private final Gson gson;

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
      login.successHandler(new CustomLoginSuccessHandler(gson));
      // 로그인 실패시 실행될 로직 클래스
      login.failureHandler(new CustomLoginFailureHandler());
    });

    // 권한 설정
    http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/login").permitAll() // 로그인 경로 허용
            .requestMatchers("/chat/**").permitAll() // 웹소켓 엔드포인트 허용
            .requestMatchers("/api/chat/list").permitAll()
            .requestMatchers("/api/chat/**").permitAll()
            .requestMatchers("/api/member/**").permitAll()
            .requestMatchers("/sockjs/**").permitAll()
            .anyRequest().authenticated()
    );

    http.addFilterBefore(new JWTCheckFilter(), UsernamePasswordAuthenticationFilter.class);

    // 접근 제한(허용X) 되었을 경우 예외 처리
    http.exceptionHandling(exception -> {
      exception.accessDeniedHandler(new CustomAccessDeniedHandler());
      exception.authenticationEntryPoint(new JwtAuthenticationEntryPoint());
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
    configuration.setMaxAge(3600L);
    configuration.setExposedHeaders(Collections.singletonList("Authorization"));

    // 위 설정정보를 토대로 Url 전체 경로에 적용하는 CORS 설정 소스 생성해 리턴
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);

    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }
}
