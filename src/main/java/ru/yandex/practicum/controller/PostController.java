package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.service.PostService;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/home")
    @ResponseBody
    public String homePage() {
        return "<h1>Hello, world!</h1>";
    }

    @GetMapping()
    public ResponseEntity<Page<PostResponse>> getPosts(@RequestParam("search") String search,
                                                       @RequestParam("pageNumber") int pageNumber,
                                                       @RequestParam("pageSize") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
        Page<PostResponse> posts = postService.getPosts(search, pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable("postId") Long postId) {
        PostResponse post = postService.getPost(postId);
        return ResponseEntity.ok(post);
    }

    @PostMapping()
    public ResponseEntity<PostResponse> addPost(@Valid @RequestBody NewPostRequest newPostRequest) {
        PostResponse post = postService.addPost(newPostRequest);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable("postId") Long postId,
                                                   @Valid @RequestBody UpdatePostRequest updatePostRequest) {
        PostResponse post = postService.updatePost(postId, updatePostRequest);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<Long> addLike(@PathVariable("postId") Long postId) {
        Long likeCount = postService.addLike(postId);
        return ResponseEntity.ok(likeCount);
    }

    @PutMapping("/{postId}/image")
    public ResponseEntity<Void> uploadImage(@PathVariable("postId") Long postId,
                                            @RequestParam("image") MultipartFile image) {
        postService.uploadImage(postId, image);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable("postId") Long postId) {
        byte[] image = postService.getImage(postId);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

}