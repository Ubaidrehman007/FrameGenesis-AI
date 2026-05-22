package com.framegenesis.framegenesisai.service;

import com.framegenesis.framegenesisai.entity.User;
import com.framegenesis.framegenesisai.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        String email = normalizeEmail(user.getEmail());

        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }

        user.setEmail(email);
        user.setName(user.getName().trim());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
    public User loginUser(String email, String password) {

        User user = userRepository.findByEmail(normalizeEmail(email))
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        boolean isPasswordCorrect =
                passwordEncoder.matches(password, user.getPassword());

        if (!isPasswordCorrect) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return user;
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}
