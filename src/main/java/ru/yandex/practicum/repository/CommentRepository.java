package ru.yandex.practicum.repository;

import ru.yandex.practicum.dto.NewCommentRequest;
import ru.yandex.practicum.dto.UpdateCommentRequest;
import ru.yandex.practicum.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    List<Comment> findAllByPostId(Long postId);

    Comment findByPostIdAndCommentId(Long postId, Long commentId);

    Comment addComment(Long postId, NewCommentRequest newCommentRequest);

    Comment updateComment(Long postId, Long commentId, UpdateCommentRequest updateCommentRequest);

    void deleteComment(Long postId, Long commentId);

}