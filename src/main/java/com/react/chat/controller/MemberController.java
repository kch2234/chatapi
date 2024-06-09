package com.react.chat.controller;

import com.react.chat.dto.MemberDTO;
import com.react.chat.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/login")
    public ResponseEntity<?> getMemberByEmail(@RequestParam String email) {
        try {
            MemberDTO memberDTO = (MemberDTO) customUserDetailsService.loadUserByUsername(email);
            return ResponseEntity.ok(memberDTO);
        } catch (UsernameNotFoundException e) {
            log.error("User not found: {}", email, e);
            return ResponseEntity.status(404).body("User not found");
        } catch (Exception e) {
            log.error("Error occurred while retrieving user: {}", email, e);
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
}
