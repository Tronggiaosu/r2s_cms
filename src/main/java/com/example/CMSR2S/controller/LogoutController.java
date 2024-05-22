package com.example.CMSR2S.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CMSR2S.util.JwtUtils;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/logout")
public class LogoutController {
    @PostMapping
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        if (token != null) {
            JwtUtils.addToBlacklist(token);
            return ResponseEntity.ok("Đăng xuất thành công");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy token");
        }
    }

    // Trích xuất token từ request
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Bỏ qua tiền tố "Bearer " để lấy token thực sự
        }
        return null;
    }
}
