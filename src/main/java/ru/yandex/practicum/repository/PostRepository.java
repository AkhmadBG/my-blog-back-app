package ru.yandex.practicum.repository;

import ru.yandex.practicum.dto.CustomPage;
import ru.yandex.practicum.dto.NewPostRequest;
import ru.yandex.practicum.dto.PostResponse;
import ru.yandex.practicum.dto.UpdatePostRequest;
import ru.yandex.practicum.entity.Post;

public interface PostRepository {

    CustomPage<PostResponse> getPosts(String search, int pageNumber, int pageSize);

    Post findPostById(Long postId);

    Post addPost(NewPostRequest newPostRequest);

    Post updatePost(Long postId, UpdatePostRequest updatePostRequest);

    void deletePost(Long postId);

    void save(Post post);

}