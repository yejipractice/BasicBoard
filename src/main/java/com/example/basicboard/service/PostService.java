package com.example.basicboard.service;

import com.example.basicboard.advice.exception.CPostNotFoundException;
import com.example.basicboard.dto.PostRequestDto;
import com.example.basicboard.dto.PostUpdateDto;
import com.example.basicboard.models.Post;
import com.example.basicboard.repository.PostRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    public Post getPost(@NotNull long postId) {
        return postRepository.findById(postId).orElseThrow(CPostNotFoundException::new);
    }

    public Long createPost(@NotNull PostRequestDto postRequestDto){
        Post post = new Post(postRequestDto);
        postRepository.save(post);
        return post.getId();
    }

    public Long updatePost(@NotNull long postId, @NotNull PostUpdateDto postUpdateDto){
        Post post = postRepository.findById(postId).orElseThrow(CPostNotFoundException::new);
        post.update(postUpdateDto);
        postRepository.save(post);
        return post.getId();
    }

    public long deletePost(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(CPostNotFoundException::new);
        postRepository.delete(post);
        return postId;
    }
}
