package com.example.test2.service;



import com.example.test2.entity.User;
import com.example.test2.repositories.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    public userRepo userRepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSecurityAnswer1(passwordEncoder.encode(user.getSecurityAnswer1()));
        user.setSecurityAnswer2(passwordEncoder.encode(user.getSecurityAnswer2()));
        userRepo.save(user);
    }
}

