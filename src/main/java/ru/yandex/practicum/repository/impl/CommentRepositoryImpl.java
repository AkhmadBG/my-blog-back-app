package ru.yandex.practicum.repository.impl;

import ru.yandex.practicum.dto.NewCommentRequest;
import ru.yandex.practicum.dto.UpdateCommentRequest;
import ru.yandex.practicum.entity.Comment;
import ru.yandex.practicum.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

public class CommentRepositoryImpl implements CommentRepository {

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        return List.of();
    }

    @Override
    public Optional<Comment> findByPostIdAndCommentId(Long postId, Long commentId) {
        return Optional.empty();
    }

    @Override
    public Comment addComment(Long postId, NewCommentRequest newCommentRequest) {
        return null;
    }

    @Override
    public Comment updateComment(Long postId, Long commentId, UpdateCommentRequest updateCommentRequest) {
        return null;
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {

    }

}