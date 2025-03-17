package com.project.customerapi.controller;

import com.project.customerapi.model.LoginRequest;
import com.project.customerapi.model.Role;
import com.project.customerapi.model.TokenResponse;
import com.project.customerapi.model.User;
import com.project.customerapi.repository.UserRepository;
import com.project.customerapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.project.customerapi.util.JwtUtil;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthService authService, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestParam String username, @RequestParam String password){
        User user = authService.registerUser(username, password, Collections.singleton(Role.SALES));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
        System.out.println("Gelen Kullanıcı: " + loginRequest.getUsername());
        System.out.println("Veritabanındaki Şifre: " + userOptional.get().getPassword());
        System.out.println("Girilen Şifre: " + loginRequest.getPassword());
        System.out.println("Şifreler Eşleşiyor mu?: " + passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword()));

        if (userOptional.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword())) {
            return ResponseEntity.status(401).body("Hatalı kullanıcı adı veya şifre");
        }

        String token = jwtUtil.generateToken(loginRequest.getUsername());
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
