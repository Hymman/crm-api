package com.project.customerapi.service;

import com.project.customerapi.model.Role;
import com.project.customerapi.model.User;
import com.project.customerapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;


@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public User registerUser(String username,String password, Set<Role> roles){
        User user = new User(username,passwordEncoder.encode(password),roles);
        return userRepository.save(user);
    }
}
