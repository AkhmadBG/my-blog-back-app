package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.CommentResponse;
import ru.yandex.practicum.dto.NewCommentRequest;
import ru.yandex.practicum.dto.UpdateCommentRequest;
import ru.yandex.practicum.repository.CommentRepository;
import ru.yandex.practicum.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public List<CommentResponse> getComments(Long postId) {
        return List.of();
    }

    @Override
    public CommentResponse getComment(Long postId, Long commentId) {
        return null;
    }

    @Override
    public CommentResponse addComment(Long postId, NewCommentRequest newCommentRequest) {
        return null;
    }

    @Override
    public CommentResponse updateComment(Long postId, Long commentId, UpdateCommentRequest updateCommentRequest) {
        return null;
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {

    }
}
