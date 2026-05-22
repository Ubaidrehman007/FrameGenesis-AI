package com.framegenesis.framegenesisai.controller;

import com.framegenesis.framegenesisai.dto.SignupRequest;
import com.framegenesis.framegenesisai.dto.UserResponse;
import com.framegenesis.framegenesisai.entity.User;
import com.framegenesis.framegenesisai.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.framegenesis.framegenesisai.dto.LoginRequest;
import com.framegenesis.framegenesisai.dto.AuthResponse;
import com.framegenesis.framegenesisai.security.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final JwtService jwtService;

    private final UserService userService;

    public AuthController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public AuthResponse signup(@Valid @RequestBody SignupRequest request) {
        User user = new User(
                request.name(),
                request.email(),
                request.password()
        );

        User registeredUser = userService.registerUser(user);
        String token = jwtService.generateToken(registeredUser.getEmail());

        return new AuthResponse(token, UserResponse.from(registeredUser));
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        User user = userService.loginUser(
                request.email(),
                request.password()
        );
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, UserResponse.from(user));
    }
}
