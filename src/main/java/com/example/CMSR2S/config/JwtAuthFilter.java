package com.example.CMSR2S.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.CMSR2S.util.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Phương thức này được triệu hồi khi một request được thực hiện
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException,
            IOException {
        // Lấy token từ header "Authorization"
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Xóa tiền tố "Bearer " để lấy token
            token = authHeader.substring(7);
            // Trích xuất tên người dùng từ token
            username = JwtUtils.extractUsername(token);
        }

        // Nếu tên người dùng không null và không có xác thực trong
        // SecurityContextHolder
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Tải thông tin người dùng từ userDetailsService
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // Kiểm tra tính hợp lệ của token
            if (JwtUtils.validateToken(token, userDetails)) {
                // Xác thực người dùng và thiết lập Authentication trong SecurityContextHolder
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Chuyển tiếp request và response cho các filter khác trong filterChain
        filterChain.doFilter(request, response);
    }
}
