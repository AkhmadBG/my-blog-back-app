package ru.yandex.practicum.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.dto.NewCommentRequest;
import ru.yandex.practicum.dto.UpdateCommentRequest;
import ru.yandex.practicum.entity.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository {

    List<Comment> findAllByPostId(Long postId);

    Optional<Comment> findByPostIdAndCommentId(Long postId, Long commentId);

    Comment addComment(Long postId, NewCommentRequest newCommentRequest);

    Comment updateComment(Long postId, Long commentId, UpdateCommentRequest updateCommentRequest);

    void deleteComment(Long postId, Long commentId);

}
