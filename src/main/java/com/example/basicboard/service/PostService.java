package com.example.basicboard.service;

import com.example.basicboard.advice.exception.CPostNotFoundException;
import com.example.basicboard.advice.exception.CUserNotFoundException;
import com.example.basicboard.dto.PostRequestDto;
import com.example.basicboard.dto.PostResponseDto;
import com.example.basicboard.models.Post;
import com.example.basicboard.models.User;
import com.example.basicboard.repository.PostRepository;
import com.example.basicboard.repository.UserRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    public List<PostResponseDto> getUserPosts(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(CUserNotFoundException::new);
        List<Post> posts = postRepository.findAllByUser(user);
        List<PostResponseDto> responseDtoList = new ArrayList<>();
        for(Post post : posts){
            responseDtoList.add(new PostResponseDto(post));
        }
        return responseDtoList;
    }

    public PostResponseDto getPost(@NotNull long postId, String userId) {
        Post post = postRepository.findById(postId).orElseThrow(CPostNotFoundException::new);
        User writer = post.getUser();
        User user = userRepository.findByUserId(userId).orElseThrow(CUserNotFoundException::new);
        if(!writer.getUserId().equals(user.getUserId())){
            throw new CPostNotFoundException();
        }
        return new PostResponseDto(post);
    }

    public Long createPost(@NotNull PostRequestDto postRequestDto, String userId){
        User user = userRepository.findByUserId(userId).orElseThrow(CUserNotFoundException::new);
        Post post = new Post(postRequestDto, user);
        postRepository.save(post);
        return post.getId();
    }

    public Long updatePost(@NotNull long postId, @NotNull PostRequestDto postRequestDto, String userId){
        Post post = postRepository.findById(postId).orElseThrow(CPostNotFoundException::new);
        User user = userRepository.findByUserId(userId).orElseThrow(CUserNotFoundException::new);
        User writer = post.getUser();
        if(!writer.getUserId().equals(user.getUserId())){ // 객체로 비교하면 안된다. (user==writer? x)
            throw new CPostNotFoundException();
        }
        post.update(postRequestDto);
        postRepository.save(post);
        return post.getId();
    }

    public long deletePost(long postId, String userId) {
        Post post = postRepository.findById(postId).orElseThrow(CPostNotFoundException::new);
        User writer = post.getUser();
        User user = userRepository.findByUserId(userId).orElseThrow(CUserNotFoundException::new);
        if(!writer.getUserId().equals(user.getUserId())){
            throw new CPostNotFoundException();
        }
        postRepository.delete(post);
        return postId;
    }
}
