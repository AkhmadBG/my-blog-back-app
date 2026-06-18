package ru.yandex.practicum.repository.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.dto.NewPostRequest;
import ru.yandex.practicum.dto.UpdatePostRequest;
import ru.yandex.practicum.entity.Post;
import ru.yandex.practicum.mapper.PostRowMapper;
import ru.yandex.practicum.mapper.TagRowMapper;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({PostRepositoryImpl.class, PostRowMapper.class, TagRowMapper.class})
class PostRepositoryImplTest {

    @Autowired
    private PostRepositoryImpl postRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void getPosts_shouldReturnPostsWithTags() {
        jdbcTemplate.update("""
                INSERT INTO posts(title, text)
                VALUES (?, ?)
                """, "title1", "text1");

        Long postId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM posts", Long.class);

        jdbcTemplate.update("""
                INSERT INTO posts(title, text)
                VALUES (?, ?)
                """, "title2", "text2");

        jdbcTemplate.update("""
                INSERT INTO post_tags(post_id, tag)
                VALUES (?, ?)
                """, postId, "tag1");

        jdbcTemplate.update("""
                INSERT INTO post_tags(post_id, tag)
                VALUES (?, ?)
                """, postId, "tag2");

        List<Post> posts = postRepository.getPosts("", 1, 10);

        assertThat(posts).hasSize(2);

        Post post = posts.getFirst();

        assertThat(post.getTags())
                .containsExactlyInAnyOrder("tag1", "tag2");
    }

    @Test
    void countPosts_shouldReturnCount() {
        jdbcTemplate.update("""
                INSERT INTO posts(title, text)
                VALUES (?, ?)
                """, "title1", "text1");

        jdbcTemplate.update("""
                INSERT INTO posts(title, text)
                VALUES (?, ?)
                """, "title2", "text2");

        long count = postRepository.countPosts("");

        assertThat(count).isEqualTo(2);
    }

    @Test
    void countPosts_shouldReturnCountBySearch() {
        jdbcTemplate.update("""
                INSERT INTO posts(title, text)
                VALUES (?, ?)
                """, "title1", "text1");

        jdbcTemplate.update("""
                INSERT INTO posts(title, text)
                VALUES (?, ?)
                """, "title2", "text2");

        long count = postRepository.countPosts("title1");

        assertThat(count).isEqualTo(1);
    }

    @Test
    void incrementOrDecrementPostCommentsCount_shouldIncrement() {

        jdbcTemplate.update("""
                        INSERT INTO posts(title, text, comments_count)
                        VALUES (?, ?, ?)
                        """,
                "title",
                "text",
                0
        );

        Long postId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM posts", Long.class);

        postRepository.incrementOrDecrementPostCommentsCount(postId, true);

        Long commentsCount = jdbcTemplate.queryForObject(
                """
                        SELECT comments_count
                        FROM posts
                        WHERE id = ?
                        """,
                Long.class,
                postId
        );

        assertThat(commentsCount).isEqualTo(1);
    }

    @Test
    void incrementOrDecrementPostCommentsCount_shouldDecrement() {

        jdbcTemplate.update("""
                        INSERT INTO posts(title, text, comments_count)
                        VALUES (?, ?, ?)
                        """,
                "title",
                "text",
                2
        );

        Long postId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM posts", Long.class);

        postRepository.incrementOrDecrementPostCommentsCount(postId, false);

        Long commentsCount = jdbcTemplate.queryForObject(
                """
                        SELECT comments_count
                        FROM posts
                        WHERE id = ?
                        """,
                Long.class,
                postId
        );

        assertThat(commentsCount).isEqualTo(1);
    }

    @Test
    void findPostById_shouldReturnPost() {

        jdbcTemplate.update("""
                        INSERT INTO posts(title, text)
                        VALUES (?, ?)
                        """,
                "title",
                "text"
        );

        Long postId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM posts", Long.class);

        jdbcTemplate.update("""
                        INSERT INTO post_tags(post_id, tag)
                        VALUES (?, ?)
                        """,
                postId,
                "java"
        );

        Post post = postRepository.findPostById(postId);

        assertThat(post.getId()).isEqualTo(postId);
        assertThat(post.getTitle()).isEqualTo("title");
        assertThat(post.getTags()).contains("java");
    }

    @Test
    void addPost_shouldSavePost() {

        NewPostRequest request =
                new NewPostRequest(
                        "title",
                        "text",
                        Set.of("java", "spring")
                );

        Post post = postRepository.addPost(request);

        assertThat(post.getId()).isNotNull();
        assertThat(post.getTitle()).isEqualTo("title");
        assertThat(post.getText()).isEqualTo("text");

        assertThat(post.getTags())
                .containsExactlyInAnyOrder("java", "spring");
    }

    @Test
    void updatePost_shouldUpdatePost() {

        jdbcTemplate.update("""
                        INSERT INTO posts(title, text)
                        VALUES (?, ?)
                        """,
                "old title",
                "old text"
        );

        Long postId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM posts", Long.class);

        UpdatePostRequest request =
                new UpdatePostRequest(
                        postId,
                        "new title",
                        "new text",
                        Set.of("java")
                );

        Post updated = postRepository.updatePost(postId, request);

        assertThat(updated.getTitle())
                .isEqualTo("new title");

        assertThat(updated.getText())
                .isEqualTo("new text");

        assertThat(updated.getTags())
                .contains("java");
    }

    @Test
    void deletePost_shouldDeletePost() {

        jdbcTemplate.update("""
                        INSERT INTO posts(title, text)
                        VALUES (?, ?)
                        """,
                "title",
                "text"
        );

        Long postId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM posts", Long.class);

        postRepository.deletePost(postId);

        Integer count = jdbcTemplate.queryForObject(
                """
                        SELECT COUNT(*)
                        FROM posts
                        WHERE id = ?
                        """,
                Integer.class,
                postId
        );

        assertThat(count).isZero();
    }

    @Test
    void save_shouldSavePost() {

        jdbcTemplate.update("""
                        INSERT INTO posts(title, text)
                        VALUES (?, ?)
                        """,
                "title",
                "text"
        );

        Long postId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM posts", Long.class);

        Post post = postRepository.findPostById(postId);

        post.setTitle("updated");
        post.setText("updated text");
        post.setImagePath("/images/test.png");
        post.setLikesCount(10);
        post.setCommentsCount(5);

        postRepository.save(post);

        Post saved = postRepository.findPostById(postId);

        assertThat(saved.getTitle()).isEqualTo("updated");
        assertThat(saved.getText()).isEqualTo("updated text");
        assertThat(saved.getImagePath()).isEqualTo("/images/test.png");
        assertThat(saved.getLikesCount()).isEqualTo(10);
        assertThat(saved.getCommentsCount()).isEqualTo(5);
    }

}