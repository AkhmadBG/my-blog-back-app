package ru.yandex.practicum.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.dto.NewPostRequest;
import ru.yandex.practicum.dto.UpdatePostRequest;
import ru.yandex.practicum.entity.Post;
import ru.yandex.practicum.exception.PostNotFoundException;
import ru.yandex.practicum.mapper.PostRowMapper;
import ru.yandex.practicum.mapper.TagRowMapper;
import ru.yandex.practicum.repository.PostRepository;

import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final JdbcTemplate jdbcTemplate;
    private final PostRowMapper postRowMapper;
    private final TagRowMapper tagRowMapper;

    @Autowired
    public PostRepositoryImpl(JdbcTemplate jdbcTemplate, PostRowMapper postRowMapper, TagRowMapper tagRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.postRowMapper = postRowMapper;
        this.tagRowMapper = tagRowMapper;
    }

    @Override
    public List<Post> getPosts(String search, int pageNumber, int pageSize) {
        String getPostsQuery = """
                SELECT * 
                FROM posts 
                WHERE LOWER(text) LIKE (?) 
                ORDER BY id 
                LIMIT ? 
                OFFSET ?
                """;
        int offset = (pageNumber - 1) * pageSize;
        return jdbcTemplate.query(getPostsQuery, postRowMapper, search, pageSize, offset);
    }

    @Override
    public long countPosts(String search) {
        if (search == null || search.isBlank()) {
            return jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM posts", Long.class
            );
        }
        String countPostsQuery = """
                SELECT COUNT(*) 
                FROM posts 
                WHERE LOWER(title) LIKE LOWER(?) 
                OR LOWER(text) LIKE LOWER(?)
                """;
        String pattern = "%" + search + "%";
        return jdbcTemplate.queryForObject(countPostsQuery, Long.class, pattern, pattern);
    }

    @Override
    public Post findPostById(Long postId) {
        String findPostQuery = """
                SELECT id, 
                title, 
                text, 
                image_path, 
                likes_count, 
                comments_count 
                FROM posts AS p 
                WHERE p.id = ?
                """;
        String findTagQuery = """
                SELECT tag 
                FROM post_tags AS pt 
                WHERE pt.post_id = ?
                """;
        try {
            Post post = jdbcTemplate.queryForObject(findPostQuery, postRowMapper, postId);
            Set<String> tags = new HashSet<>(jdbcTemplate.query(findTagQuery, tagRowMapper, postId));
            post.setTags(tags);
            return post;
        } catch (EmptyResultDataAccessException e) {
            throw new PostNotFoundException("Пост с id " + postId + " не найден");
        }
    }

    @Override
    public Post addPost(NewPostRequest newPostRequest) {
        String addPostQuery = """
                INSERT INTO posts (title, text, likes_count, comments_count) 
                VALUES (?, ?, ?, ?)
                """;
        String addPostsTagsQuery = """
                INSERT INTO post_tags (post_id, tag) 
                VALUES (?, ?)
                """;
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            int update = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        addPostQuery,
                        new String[]{"id"}
                );
                ps.setString(1, newPostRequest.title());
                ps.setString(2, newPostRequest.text());
                ps.setLong(3, 0);
                ps.setLong(4, 0);
                return ps;
            }, keyHolder);
            if (update == 0) {
                throw new PostNotFoundException("Пост не найден");
            }
            Long newPostId = keyHolder.getKeyAs(Long.class);
            if (newPostId == null) {
                throw new RuntimeException("не удалось сохранить пост");
            }
            newPostRequest.tags().forEach(
                    (tag) -> jdbcTemplate.update(addPostsTagsQuery, newPostId, tag)
            );
            return findPostById(newPostId);
        } catch (EmptyResultDataAccessException e) {
            throw new PostNotFoundException("Пост не найден");
        }
    }

    @Override
    public Post updatePost(Long postId, UpdatePostRequest updatePostRequest) {
        String updatePostQuery = """
                UPDATE posts 
                SET title = ?, 
                text = ?
                WHERE id = ?
                """;
        String updatePostsTagsQuery = """
                INSERT INTO post_tags (post_id, tag) 
                VALUES (?, ?)
                """;
        try {
            int update = jdbcTemplate.update(updatePostQuery,
                    updatePostRequest.title(),
                    updatePostRequest.text(),
                    updatePostRequest.id());
            if (update == 0) {
                throw new PostNotFoundException("Пост не найден");
            }
            updatePostRequest.tags().forEach(
                    (tag) -> jdbcTemplate.update(updatePostsTagsQuery, postId, tag)
            );
            return findPostById(postId);
        } catch (EmptyResultDataAccessException e) {
            throw new PostNotFoundException("Пост не найден");
        }
    }

    @Override
    public void deletePost(Long postId) {
        String deletePostQuery = """
                DELETE FROM posts WHERE id = ?
                """;
        try {
            jdbcTemplate.update(deletePostQuery, postId);
        } catch (EmptyResultDataAccessException e) {
            throw new PostNotFoundException("Пост с id " + postId + " не найден");
        }
    }

    @Override
    public void save(Post post) {
        String savePostQuery = """
                UPDATE posts 
                SET title = ?, 
                text = ?, 
                image_path = ?, 
                likes_count = ?, 
                comments_count = ? 
                WHERE id = ?
                """;
        try {
            jdbcTemplate.update(savePostQuery,
                    post.getTitle(),
                    post.getText(),
                    post.getImagePath(),
                    post.getLikesCount(),
                    post.getCommentsCount(),
                    post.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new PostNotFoundException("Пост с id " + post.getId() + " не найден");
        }
    }

}