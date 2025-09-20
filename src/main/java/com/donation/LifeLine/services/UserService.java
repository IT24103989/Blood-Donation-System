package com.donation.LifeLine.services;
import com.donation.LifeLine.model.User;
import com.donation.LifeLine.model.Role;
import com.donation.LifeLine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(User user, Set<Role> roles) {
        // Check if username exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }




        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set roles
        user.setRoles(roles);

        // Save user
        return userRepository.save(user);
    }
}