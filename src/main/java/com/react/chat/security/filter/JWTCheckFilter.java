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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
public class JWTCheckFilter extends OncePerRequestFilter {

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    // Preflight 필터 체크 X (Ajax CORS 요청 전에 날리는것)
    if (request.getMethod().equals("OPTIONS")) {
      return true;
    }

    String requestURI = request.getRequestURI();
    log.info("***** JWTCheckFilter - shouldNotFilter : requestURI : {}", requestURI);
    // 필터 체크 안 하는 경로
    return requestURI.startsWith("/api/member/");
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    log.info("***** JWTCheckFilter - doFilterInternal!");

    String authValue = request.getHeader("Authorization");
    log.info("***** doFilterInternal - authValue : {}", authValue);

    if (authValue == null || !authValue.startsWith("Bearer ")) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    String accessToken = authValue.substring(7);
    log.info("***** doFilterInternal - accessToken : {}", accessToken);
    try {
      Map<String, Object> claims = JWTUtil.validateToken(accessToken);
      log.info("********* doFilterInternal - claims : {}", claims);
      if (claims != null) {
        // 인증 정보 claims로 MemberDTO 구성 -> 시큐리티에 반영 추가 (시큐리티용 권한)
        Long id = ((Number) claims.get("id")).longValue();
        String email = (String) claims.get("email");
        String nickname = (String) claims.get("nickname");
        String password = claims.containsKey("password") ? (String) claims.get("password") : "";
        List<ProfileImageDTO> profileImageDTO = claims.containsKey("profileImageDTO") ? (List<ProfileImageDTO>) claims.get("profileImageDTO") : List.of();
        String phone = claims.containsKey("phone") ? (String) claims.get("phone") : null;
        String introduction = claims.containsKey("introduction") ? (String) claims.get("introduction") : null;
        LocalDateTime birth = LocalDateTime.parse((String) claims.get("birth"));
        String nationality = (String) claims.get("nationality");
        Gender gender = Gender.valueOf((String) claims.get("gender"));
        Role role = Role.valueOf((String) claims.get("role"));
        Boolean disabled = (Boolean) claims.get("disabled");

        LocalDateTime disabledDate = null;
        if (claims.get("disabledDate") != null) {
          disabledDate = LocalDateTime.parse((String) claims.get("disabledDate"));
        }

        LocalDateTime createDate = null;
        if (claims.get("createDate") != null) {
          List<?> createDateList = (List<?>) claims.get("createDate");
          createDate = convertToLocalDateTime(createDateList);
        }

        LocalDateTime updateDate = null;
        if (claims.get("updateDate") != null) {
          List<?> updateDateList = (List<?>) claims.get("updateDate");
          updateDate = convertToLocalDateTime(updateDateList);
        }

        if (id == null || email == null || nickname == null || nationality == null || gender == null || role == null || disabled == null) {
          throw new IllegalArgumentException("필수 값이 누락되었습니다.");
        }

        MemberDTO memberDTO = new MemberDTO(id, email, nickname, password, profileImageDTO, phone, introduction, birth, disabled, disabledDate, nationality, gender, role, createDate, updateDate);
        log.info("******** doFileterInternal - memberDTO : {}", memberDTO);

        // 시큐리티 인증 추가 JWT <-> SpringSecurity 로그인 상태 호환
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberDTO, password, memberDTO.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
      filterChain.doFilter(request, response); // 필터 체인을 통해 요청을 계속 진행합니다.
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
    }
  }

  // 리스트를 LocalDateTime으로 변환하는 메서드 추가
  private LocalDateTime convertToLocalDateTime(List<?> dateList) {
    return LocalDateTime.of(
            ((Number) dateList.get(0)).intValue(),
            ((Number) dateList.get(1)).intValue(),
            ((Number) dateList.get(2)).intValue(),
            ((Number) dateList.get(3)).intValue(),
            ((Number) dateList.get(4)).intValue(),
            ((Number) dateList.get(5)).intValue(),
            ((Number) dateList.get(6)).intValue()
    );
  }
}
