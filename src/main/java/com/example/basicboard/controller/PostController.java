package com.example.basicboard.controller;

import com.example.basicboard.dto.PostRequestDto;
import com.example.basicboard.models.Post;
import com.example.basicboard.models.response.CommonResult;
import com.example.basicboard.models.response.ListResult;
import com.example.basicboard.models.response.SingleResult;
import com.example.basicboard.repository.PostRepository;
import com.example.basicboard.service.PostService;
import com.example.basicboard.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = {"1. 게시글 컨트롤러"})
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;
    private final ResponseService responseService;

    @Autowired
    public PostController(PostRepository postRepository, PostService postService, ResponseService responseService){
        this.postRepository = postRepository;
        this.postService = postService;
        this.responseService = responseService;
    }

    @ApiOperation(value = "모든 게시글 조회", notes = "모든 게시글을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @GetMapping("/post")
    public ListResult<Post> getAllPost() {
        return responseService.getListResult(postService.getAllPost());
    }

    @ApiOperation(value = "단건 게시글 조회", notes = "단건 게시글을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @GetMapping("/post/{postId}")
    public SingleResult<Post> getPost(@PathVariable("postId") long id) {
        return responseService.getSingleResult(postService.getPost(id));
    }

    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @PostMapping("/post")
    public CommonResult createPost(@RequestBody PostRequestDto postRequestDto){
        postService.createPost(postRequestDto);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @PutMapping("/post/{postId}")
    public CommonResult updatePost(@PathVariable("postId") long id, @RequestBody PostRequestDto postRequestDto){
        postService.updatePost(id, postRequestDto);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @DeleteMapping("post/{postId}")
    public CommonResult deletePost(@PathVariable("postId") long id){
        postService.deletePost(id);
        return responseService.getSuccessResult();
    }
}
