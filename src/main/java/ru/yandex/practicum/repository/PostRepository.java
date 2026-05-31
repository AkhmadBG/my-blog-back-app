package ru.yandex.practicum.repository;

import ru.yandex.practicum.dto.NewPostRequest;
import ru.yandex.practicum.dto.UpdatePostRequest;
import ru.yandex.practicum.entity.Post;

import java.util.List;

public interface PostRepository {

    List<Post> getPosts(String search, int pageNumber, int pageSize);

    Post findPostById(Long postId);

    Post addPost(NewPostRequest newPostRequest);

    Post updatePost(Long postId, UpdatePostRequest updatePostRequest);

    void deletePost(Long postId);

    void save(Post post);

    long countPosts(String search);

    void incrementOrDecrementPostCommentsCount(Long postId, boolean isIncrement);

}