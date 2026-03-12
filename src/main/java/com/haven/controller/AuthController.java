package com.haven.controller;

import com.haven.model.User;
import com.haven.security.JwtService;
import com.haven.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@RequestBody User request) {
        User user = userService.registerUser(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        return jwtService.generateToken(user.getEmail());
    }

    @PostMapping("/login")
    public String login(@RequestBody User request) {

        User user = userService.findByEmailOrThrow(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.generateToken(user.getEmail());
    }
}