package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.service.CommentService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/posts")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable("postId") Long postId) {
        List<CommentResponse> comments = commentService.getComments(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable("postId") Long postId,
                                                      @PathVariable("commentId") Long commentId) {
        CommentResponse comment = commentService.getComment(postId, commentId);
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponse> addComment(@PathVariable("postId") Long postId,
                                                      @Valid @RequestBody NewCommentRequest newCommentRequest) {
        CommentResponse comment = commentService.addComment(postId, newCommentRequest);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable("postId") Long postId,
                                                         @PathVariable("commentId") Long commentId,
                                                         @Valid @RequestBody UpdateCommentRequest updateCommentRequest) {
        CommentResponse comment = commentService.updateComment(postId, commentId, updateCommentRequest);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("postId") Long postId,
                                              @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok().build();
    }

}