package com.react.chat.security.filter;

import com.google.gson.Gson;
import com.react.chat.domain.enumFiles.Gender;
import com.react.chat.domain.enumFiles.Role;
import com.react.chat.dto.MemberDTO;
import com.react.chat.dto.ProfileImageDTO;
import com.react.chat.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
public class JWTCheckFilter extends OncePerRequestFilter {
  // 생략 필터 메서드 추가
  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

    // Preflight 필터 체크 X (Ajax CORS 요청 전에 날리는것)
    if(request.getMethod().equals("OPTIONS")) {
      return true;
    }

    String requestURI = request.getRequestURI();
    log.info("***** JWTCheckFilter - shouldNotFilter : requestURI : {}", requestURI);
    // 필터 체크 안 하는 경로
      //return requestURI.startsWith("/api/member/login") || requestURI.startsWith("/chat") || requestURI.startsWith("/match");
    if(requestURI.startsWith("/signup")) {
      return true;
    }
    // 추후 이미지 경로로 수정
/*    if(requestURI.startsWith("")) {
      return true;
    }*/
    return false;
  }

  // 필터링 로직 작성 (추상메서드)
  @Override
  protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    log.info("***** JWTCheckFilter - doFilterInternal!");

    String authValue = request.getHeader("Authorization");
    log.info("***** doFilterInternal - authValue : {}", authValue);

      if (authValue != null && authValue.startsWith("Bearer ")) {
          String token = authValue.substring(7);
          log.info("JWTCheckFilter - token: {}", token);
          //try {
          //        if (JWTUtil.isValidToken(token)) {
          //          String username = JWTUtil.getUsernameFromToken(token);
          //          log.info("***** doFilterInternal - accessToken : {}", token);
    try {
      String accessToken = authValue.substring(7);
      Map<String, Object> claims = JWTUtil.validateToken(accessToken);
      log.info("********* doFilterInternal - claims : {}", claims);

      // 인증 정보 claims로 MemberDTO 구성 -> 시큐리티에 반영 추가 (시큐리티용 권한)
      Long id = (Long) claims.get("id");
      String email = (String) claims.get("email");
      String password = (String) claims.get("password");
      Role role = (Role) claims.get("role");

      MemberDTO memberDTO = new MemberDTO(id, email, password, role);
        log.info("******** doFilterInternal - memberDTO : {}", memberDTO);

      // 시큐리티 인증 추가 JWT <-> SpringSecurity 로그인 상태 호환
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberDTO, password, memberDTO.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      //} else {
        //log.error("Invalid JWT token");
      filterChain.doFilter(request, response);
      //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //          return;
        //        }

    }catch (Exception e) {
      log.error("***** JWTCheckFilter error!!!");
      log.error(e.getMessage());
      //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

      Gson gson = new Gson();
      String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));
      response.setContentType("application/json");
      PrintWriter writer = response.getWriter();
      writer.println(msg);
      writer.close();
      //return; // 오류가 발생한 경우 요청 처리를 중단합니다.
    }

//    filterChain.doFilter(request, response); // 필터 체인을 통해 요청을 계속 진행합니다.
  }

  // 리스트를 LocalDateTime으로 변환하는 메서드 추가
  /*private LocalDateTime convertToLocalDateTime(List<?> dateList) {
    return LocalDateTime.of(
            ((Number) dateList.get(0)).intValue(),
            ((Number) dateList.get(1)).intValue(),
            ((Number) dateList.get(2)).intValue(),
            ((Number) dateList.get(3)).intValue(),
            ((Number) dateList.get(4)).intValue(),
            ((Number) dateList.get(5)).intValue(),
            ((Number) dateList.get(6)).intValue()
    );
  }*/
  }
}