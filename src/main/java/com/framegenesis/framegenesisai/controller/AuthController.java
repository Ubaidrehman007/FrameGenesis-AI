package com.framegenesis.framegenesisai.controller;

import com.framegenesis.framegenesisai.entity.User;
import com.framegenesis.framegenesisai.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.framegenesis.framegenesisai.dto.LoginRequest;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {

        return userService.loginUser(
                request.getEmail(),
                request.getPassword()
        );
    }
}