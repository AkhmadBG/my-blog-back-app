package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.CommentResponse;
import ru.yandex.practicum.dto.NewCommentRequest;
import ru.yandex.practicum.dto.UpdateCommentRequest;

import java.util.List;

public interface CommentService {

    List<CommentResponse> getComments(Long postId);

    CommentResponse getComment(Long postId, Long commentId);

    CommentResponse addComment(Long postId, NewCommentRequest newCommentRequest);

    CommentResponse updateComment(Long postId, Long commentId, UpdateCommentRequest updateCommentRequest);

    void deleteComment(Long postId, Long commentId);

}