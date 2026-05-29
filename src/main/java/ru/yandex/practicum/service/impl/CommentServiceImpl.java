package ru.yandex.practicum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.CommentResponse;
import ru.yandex.practicum.dto.NewCommentRequest;
import ru.yandex.practicum.dto.UpdateCommentRequest;
import ru.yandex.practicum.entity.Comment;
import ru.yandex.practicum.exception.CommentNotFoundException;
import ru.yandex.practicum.mapper.CommentMapper;
import ru.yandex.practicum.repository.CommentRepository;
import ru.yandex.practicum.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public List<CommentResponse> getComments(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return comments.stream()
                .map(commentMapper::mapToCommentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponse getComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findByPostIdAndCommentId(postId, commentId);
        return commentMapper.mapToCommentResponse(comment);
    }

    @Override
    public CommentResponse addComment(Long postId, NewCommentRequest newCommentRequest) {
        Comment comment = commentRepository.addComment(postId, newCommentRequest);
        return commentMapper.mapToCommentResponse(comment);
    }

    @Override
    public CommentResponse updateComment(Long postId, Long commentId, UpdateCommentRequest updateCommentRequest) {
        Comment comment = commentRepository.updateComment(postId, commentId, updateCommentRequest);
        return commentMapper.mapToCommentResponse(comment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        commentRepository.deleteComment(postId, commentId);
    }

}