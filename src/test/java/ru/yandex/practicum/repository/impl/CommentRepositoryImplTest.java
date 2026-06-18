package ru.yandex.practicum.repository.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.dto.NewCommentRequest;
import ru.yandex.practicum.dto.UpdateCommentRequest;
import ru.yandex.practicum.entity.Comment;
import ru.yandex.practicum.mapper.CommentRowMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@JdbcTest
@Import({CommentRepositoryImpl.class, CommentRowMapper.class})
class CommentRepositoryImplTest {

    @Autowired
    private CommentRepositoryImpl commentRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    void shouldReturnComments() {
        jdbcTemplate.update("""
                        INSERT INTO posts(title, text)
                        VALUES (?, ?)
                        """,
                "title",
                "text"
        );

        Long postId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM posts", Long.class);

        jdbcTemplate.update(
                "INSERT INTO comments(text, post_id) VALUES (?, ?)",
                "comment1", postId
        );

        jdbcTemplate.update(
                "INSERT INTO comments(text, post_id) VALUES (?, ?)",
                "comment2", postId
        );

        jdbcTemplate.update(
                "INSERT INTO comments(text, post_id) VALUES (?, ?)",
                "comment3", postId
        );

        List<Comment> comments = commentRepository.findAllByPostId(postId);

        assertThat(comments)
                .hasSize(3);
    }

    @Test
    void shouldFindCommentsByPostId() {
        jdbcTemplate.update("""
                        INSERT INTO posts(title, text)
                        VALUES (?, ?)
                        """,
                "title",
                "text"
        );

        Long postId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM posts", Long.class);

        jdbcTemplate.update(
                "INSERT INTO comments(text, post_id) VALUES (?, ?)",
                "comment1", postId
        );

        jdbcTemplate.update(
                "INSERT INTO comments(text, post_id) VALUES (?, ?)",
                "comment2", postId
        );

        jdbcTemplate.update(
                "INSERT INTO comments(text, post_id) VALUES (?, ?)",
                "comment3", postId
        );

        List<Comment> comments = commentRepository.findAllByPostId(postId);

        assertThat(comments)
                .hasSize(3);
        assertThat(comments.get(0).getText())
                .isEqualTo("comment1");
        assertThat(comments.get(1).getText())
                .isEqualTo("comment2");
        assertThat(comments.get(2).getText())
                .isEqualTo("comment3");
    }

    @Test
    void shouldReturnCommentByPostIdAndCommentId() {
        jdbcTemplate.update("""
                        INSERT INTO posts(title, text)
                        VALUES (?, ?)
                        """,
                "title",
                "text"
        );

        Long postId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM posts", Long.class);

        jdbcTemplate.update(
                "INSERT INTO comments(text, post_id) VALUES (?, ?)",
                "comment1", postId
        );

        Long commentId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM comments", Long.class);

        Comment comment = commentRepository.findByPostIdAndCommentId(postId, commentId);

        assertThat(comment.getText())
                .isEqualTo("comment1");
    }

    @Test
    void shouldAddComment() {
        jdbcTemplate.update("""
                        INSERT INTO posts(title, text)
                        VALUES (?, ?)
                        """,
                "title",
                "text"
        );

        Long postId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM posts", Long.class);

        NewCommentRequest request = new NewCommentRequest("text", postId);

        Comment comment = commentRepository.addComment(postId, request);

        assertThat(comment)
                .isNotNull();
        assertThat(comment.getText())
                .isEqualTo("text");
        assertThat(comment.getPostId())
                .isEqualTo(postId);
    }

    @Test
    void shouldUpdateComment() {
        jdbcTemplate.update("""
                        INSERT INTO posts(title, text)
                        VALUES (?, ?)
                        """,
                "title",
                "text"
        );

        Long postId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM posts", Long.class);

        jdbcTemplate.update(
                "INSERT INTO comments(text, post_id) VALUES (?, ?)",
                "comment1", postId
        );

        Long commentId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM comments", Long.class);

        UpdateCommentRequest updateComment = new UpdateCommentRequest(commentId, "update comment1", postId);

        Comment comment = commentRepository.updateComment(postId, commentId, updateComment);

        assertThat(comment.getText())
                .isEqualTo("update comment1");
    }

    @Test
    void shouldDeleteComment() {
        jdbcTemplate.update("""
                        INSERT INTO posts(title, text)
                        VALUES (?, ?)
                        """,
                "title",
                "text"
        );

        Long postId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM posts", Long.class);

        jdbcTemplate.update("""
                        INSERT INTO comments(text, post_id)
                        VALUES (?, ?)
                        """,
                "comment1",
                postId
        );

        Long commentId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM comments", Long.class);

        commentRepository.deleteComment(postId, commentId);

        Integer count = jdbcTemplate.queryForObject("""
                        SELECT COUNT(*)
                        FROM comments
                        WHERE id = ?
                        """,
                Integer.class,
                commentId
        );

        assertThat(count).isZero();
    }

}