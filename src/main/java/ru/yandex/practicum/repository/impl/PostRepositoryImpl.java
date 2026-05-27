package ru.yandex.practicum.repository.impl;

import ru.yandex.practicum.dto.NewPostRequest;
import ru.yandex.practicum.dto.UpdatePostRequest;
import ru.yandex.practicum.entity.Post;
import ru.yandex.practicum.repository.PostRepository;

import java.util.Optional;

public class PostRepositoryImpl implements PostRepository {

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