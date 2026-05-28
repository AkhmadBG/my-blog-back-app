package ru.yandex.practicum.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.dto.NewPostRequest;
import ru.yandex.practicum.dto.PostResponse;
import ru.yandex.practicum.dto.UpdatePostRequest;
import ru.yandex.practicum.entity.Post;
import ru.yandex.practicum.repository.PostRepository;

import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Page<PostResponse> getPosts(String search, Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Post> findPostById(Long postId) {
        return Optional.empty();
    }

    @Override
    public Post addPost(NewPostRequest newPostRequest) {
        return null;
    }

    @Override
    public Post updatePost(Long postId, UpdatePostRequest updatePostRequest) {
        return null;
    }

    @Override
    public void deletePost(Long postId) {

    }

    @Override
    public void save(Post post) {

    }

}