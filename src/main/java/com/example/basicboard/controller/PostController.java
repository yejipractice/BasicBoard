package com.example.basicboard.controller;

import com.example.basicboard.dto.PostRequestDto;
import com.example.basicboard.models.Post;
import com.example.basicboard.models.response.CommonResult;
import com.example.basicboard.models.response.ListResult;
import com.example.basicboard.models.response.SingleResult;
import com.example.basicboard.repository.PostRepository;
import com.example.basicboard.security.JwtTokenProvider;
import com.example.basicboard.service.PostService;
import com.example.basicboard.service.ResponseService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = {"3. 게시글 컨트롤러"})
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;
    private final ResponseService responseService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public PostController(PostRepository postRepository, PostService postService,
                          ResponseService responseService, JwtTokenProvider jwtTokenProvider){
        this.postRepository = postRepository;
        this.postService = postService;
        this.responseService = responseService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 관리자 전용
    @Secured("ROLE_ADMIN")
    @ApiOperation(value = "모든 게시글 조회", notes = "모든 게시글을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/posts/admin")
    public ListResult<Post> getAllPosts() {
        return responseService.getListResult(postService.getAllPost());
    }

    @ApiOperation(value = "사용자가 작성한 모든 게시글 조회", notes = "사용자가 작성한 모든 게시글을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/posts")
    public ListResult<Post> getUsersPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        return responseService.getListResult(postService.getUserPosts(userId));
    }

    @ApiOperation(value = "단건 게시글 조회", notes = "단건 게시글을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/post/{postId}")
    public SingleResult<Post> getPost(@PathVariable("postId") long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        return responseService.getSingleResult(postService.getPost(id, userId));
    }

    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/post")
    public CommonResult createPost(@RequestBody PostRequestDto postRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        postService.createPost(postRequestDto, userId);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PutMapping("/post/{postId}")
    public CommonResult updatePost(@PathVariable("postId") long id, @RequestBody PostRequestDto postRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        postService.updatePost(id, postRequestDto, userId);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK !!"),
            @ApiResponse(code = 400, message = "BAD REQUEST !!"),
            @ApiResponse(code = 404, message = "NOT FOUND !!"),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR !!")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @DeleteMapping("post/{postId}")
    public CommonResult deletePost(@PathVariable("postId") long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        postService.deletePost(id, userId);
        return responseService.getSuccessResult();
    }
}
