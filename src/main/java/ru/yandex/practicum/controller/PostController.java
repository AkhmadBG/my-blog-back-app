package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.service.PostService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping()
    public ResponseEntity<Page<PostResponse>> getPosts(@RequestParam String search,
                                                       @RequestParam int pageNumber,
                                                       @RequestParam int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
        Page<PostResponse> posts = postService.getPosts(search, pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        PostResponse post = postService.getPost(postId);
        return ResponseEntity.ok(post);
    }

    @PostMapping()
    public ResponseEntity<PostResponse> addPost(@Valid @RequestBody NewPostRequest newPostRequest) {
        PostResponse post = postService.addPost(newPostRequest);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long postId,
                                                   @Valid @RequestBody UpdatePostRequest updatePostRequest) {
        PostResponse post = postService.updatePost(postId, updatePostRequest);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<Long> addLike(@PathVariable Long postId) {
        Long likeCount = postService.addLike(postId);
        return ResponseEntity.ok(likeCount);
    }

    @PutMapping("/{postId}/image")
    public ResponseEntity<Void> updateImage(@PathVariable Long postId,) {
        postService.updateImage(postId, );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long postId) {
        byte[] image = postService.getImage(postId);
        return ResponseEntity.ok(image);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        List<CommentResponse> comments = postService.getComments(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long postId,
                                                      @PathVariable Long commentId) {
        CommentResponse comment = postService.getComment(postId, commentId);
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponse> addComment(@PathVariable Long postId,
                                                      @Valid @RequestBody NewCommentRequest newCommentRequest) {
        CommentResponse comment = postService.addComment(postId, newCommentRequest);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long postId,
                                                         @PathVariable Long commentId,
                                                         @Valid @RequestBody UpdateCommentRequest updateCommentRequest) {
        CommentResponse comment = postService.updateComment(postId, commentId, updateCommentRequest);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long postId,
                                              @PathVariable Long commentId) {
        postService.deleteComment(postId, commentId);
        return ResponseEntity.ok().build();
    }

}