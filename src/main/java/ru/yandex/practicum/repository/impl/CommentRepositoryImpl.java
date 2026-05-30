package ru.yandex.practicum.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.dto.NewCommentRequest;
import ru.yandex.practicum.dto.UpdateCommentRequest;
import ru.yandex.practicum.entity.Comment;
import ru.yandex.practicum.exception.CommentNotFoundException;
import ru.yandex.practicum.mapper.CommentRowMapper;
import ru.yandex.practicum.repository.CommentRepository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CommentRowMapper commentRowMapper;

    @Autowired
    public CommentRepositoryImpl(JdbcTemplate jdbcTemplate, CommentRowMapper commentRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.commentRowMapper = commentRowMapper;
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        String findAllCommentsByIdQuery = """
                SELECT * FROM comments
                WHERE post_id = ?
                """;
            return jdbcTemplate.query(findAllCommentsByIdQuery, commentRowMapper, postId);
    }

    @Override
    public Comment findByPostIdAndCommentId(Long postId, Long commentId) {
        String findByPostIdAndCommentIdQuery = """
                SELECT * FROM comments
                WHERE post_id = ?
                AND id = ?
                """;
        try {
            return jdbcTemplate.queryForObject(findByPostIdAndCommentIdQuery, commentRowMapper, postId, commentId);
        } catch (EmptyResultDataAccessException e) {
            throw new CommentNotFoundException("Комментарий с id " + commentId + " не найден");
        }
    }

    @Override
    public Comment addComment(Long postId, NewCommentRequest newCommentRequest) {
        String addCommentQuery = """
                INSERT INTO comments (text, post_id) 
                VALUES (?, ?)
                """;
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            int update = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(addCommentQuery, new String[]{"id"});
                ps.setString(1,newCommentRequest.text());
                ps.setLong(2, newCommentRequest.postId());
                return ps;
                },
                keyHolder);

            if (update == 0) {
                throw new CommentNotFoundException("Комментарий не найден");
            }

            Long newCommentId = keyHolder.getKeyAs(Long.class);

            if (newCommentId == null) {
                throw new RuntimeException("не удалось сохранить комментарий");
            }

            return findByPostIdAndCommentId(postId, newCommentId);
    }

    @Override
    public Comment updateComment(Long postId, Long commentId, UpdateCommentRequest updateCommentRequest) {
        String updateCommentQuery = """
                UPDATE comments 
                SET text = ?
                WHERE post_id = ? 
                AND id = ?
                """;
        try {
            int update = jdbcTemplate.update(updateCommentQuery, updateCommentRequest.text(), postId, commentId);
            if (update == 0) {
                throw new CommentNotFoundException("Комментарий с id " + commentId + " не найден");
            }
            return findByPostIdAndCommentId(postId, commentId);
        } catch (EmptyResultDataAccessException e) {
            throw new CommentNotFoundException("Комментарий с id " + commentId + " не найден");
        }
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        String deleteCommentQuery = """
                DELETE FROM comments 
                WHERE post_id = ? 
                AND id = ?
                """;
        try {
            int delete = jdbcTemplate.update(deleteCommentQuery, postId, commentId);
            if (delete == 0) {
                throw new CommentNotFoundException("Комментарий с id " + commentId + " не найден");
            }
        } catch (EmptyResultDataAccessException e) {
            throw new CommentNotFoundException("Комментарий с id " + commentId + " не найден");
        }
    }

}