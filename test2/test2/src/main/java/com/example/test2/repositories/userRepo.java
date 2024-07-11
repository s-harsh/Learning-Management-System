package com.example.test2.repositories;

import com.example.test2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepo extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
