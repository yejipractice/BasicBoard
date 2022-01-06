package com.example.basicboard.repository;

import com.example.basicboard.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String id);
    List<User> findAll();
    Optional<User> findById(long id);

}
