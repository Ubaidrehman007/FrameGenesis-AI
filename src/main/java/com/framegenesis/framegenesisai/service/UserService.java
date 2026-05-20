package com.framegenesis.framegenesisai.service;

import com.framegenesis.framegenesisai.entity.User;
import com.framegenesis.framegenesisai.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User registerUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
    public User loginUser(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean isPasswordCorrect =
                passwordEncoder.matches(password, user.getPassword());

        if (!isPasswordCorrect) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }
}