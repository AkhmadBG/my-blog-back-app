package ru.yandex.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.dto.NewPostRequest;
import ru.yandex.practicum.dto.PostResponse;
import ru.yandex.practicum.dto.UpdatePostRequest;
import ru.yandex.practicum.entity.Post;

import java.util.Optional;

public interface PostRepository {

    Page<PostResponse> getPosts(String search, Pageable pageable);

    Optional<Post> findPostById(Long postId);

    Post addPost(NewPostRequest newPostRequest);

    Post updatePost(Long postId, UpdatePostRequest updatePostRequest);

    void deletePost(Long postId);

    void save(Post post);

}