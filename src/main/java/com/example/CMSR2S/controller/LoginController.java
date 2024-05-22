package com.example.CMSR2S.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CMSR2S.dto.LoginDTO;
import com.example.CMSR2S.dto.ResponseLoginDTO;
import com.example.CMSR2S.util.JwtUtils;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            if (authentication.isAuthenticated()) {
                ResponseLoginDTO responseLoginDTO = new ResponseLoginDTO();
                responseLoginDTO.setUsername(loginDTO.getUsername());
                responseLoginDTO.setToken("Bearer " + JwtUtils.generateToken(loginDTO.getUsername()));
                return new ResponseEntity<>(responseLoginDTO, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("Đăng nhập thất bại", HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
