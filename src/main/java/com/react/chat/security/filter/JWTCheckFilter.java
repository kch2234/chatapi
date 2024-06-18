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
        if (requestURI.startsWith("/signup")) {
            return true;
        }
        if (requestURI.startsWith("/login")) {
            return true;
        }
        if (requestURI.startsWith("/api/member")) {
            return true;
        }
        if (requestURI.startsWith("/chat")) {
            return true;
        }
        if (requestURI.startsWith("/api/chat")) {
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
        try {
            if (JWTUtil.isValidToken(token)) {
                String username = JWTUtil.getUsernameFromToken(token);
                log.info("***** doFilterInternal - accessToken : {}", token);
                String accessToken = authValue.substring(7);
                Map<String, Object> claims = JWTUtil.validateToken(accessToken);
                log.info("********* doFilterInternal - claims : {}", claims);

                Long id = (Long) claims.get("id");
                String email = (String) claims.get("email");
                String password = (String) claims.get("password");
                Role role = Role.valueOf((String) claims.get("role"));
                MemberDTO memberDTO = new MemberDTO(id, email, password, role);
                log.info("******** doFilterInternal - memberDTO : {}", memberDTO);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberDTO, password, memberDTO.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                log.error("Invalid JWT token");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } catch (Exception e) {
            log.error("***** JWTCheckFilter error!!!");
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.println(msg);
            writer.close();
            return;
        }
        filterChain.doFilter(request, response);
    } else {
        filterChain.doFilter(request, response);
    }
}
}