package com.example.CMSR2S.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.CMSR2S.entity.Member;
import com.example.CMSR2S.exception.MemberNotFoundException;
import com.example.CMSR2S.repository.MemberRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private MemberRepository memberRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
            UserDetailsService userDetailsService,
            JwtAuthFilter jwtAuthFilter) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable); // Vô hiệu hóa CSRF trong cấu hình bảo mật

        httpSecurity.sessionManagement(configure -> configure.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Cấu
                                                                                                                      // hình
                                                                                                                      // chế
                                                                                                                      // độ
                                                                                                                      // tạo
                                                                                                                      // phiên
                                                                                                                      // không
                                                                                                                      // trạng
                                                                                                                      // thái
                .authenticationProvider(authenticationProvider(userDetailsService)) // Cấu hình AuthenticationProvider
                                                                                    // để xác thực người dùng
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Thêm JwtAuthFilter trước
                                                                                             // UsernamePasswordAuthenticationFilter
                                                                                             // trong chuỗi bộ lọc

        httpSecurity
                // Bắt đầu cấu hình xác thực các yêu cầu HTTP
                .authorizeHttpRequests(auths -> auths
                        .requestMatchers("/api/member", "/api/login", "/api/logout").permitAll() // Cho phép truy cập
                                                                                                 // không xác thực
                        // vào
                        // các API "/api/users" và "/api/login" và "/api/logout"
                        .anyRequest().authenticated()) // Yêu cầu xác thực cho tất cả các yêu cầu còn lại
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Member member = memberRepository.findByUsername(username)
                    .orElseThrow(() -> new MemberNotFoundException("User not found: " + username));
            return new CustomUserDetails(member);
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
