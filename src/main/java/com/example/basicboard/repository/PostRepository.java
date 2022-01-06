package com.example.basicboard.repository;

import com.example.basicboard.models.Post;
import com.example.basicboard.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAll();
    Optional<Post> findById(Long id);
    List<Post> findAllByUser(User user);
}
