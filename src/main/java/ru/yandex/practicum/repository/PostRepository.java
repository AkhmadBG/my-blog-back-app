package ru.yandex.practicum.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.dto.NewPostRequest;
import ru.yandex.practicum.dto.UpdatePostRequest;
import ru.yandex.practicum.entity.Post;

import java.util.Optional;

@Repository
public interface PostRepository {

    Optional<Post> findPostById(Long postId);

    Post addPost(NewPostRequest newPostRequest);

    Post updatePost(Long postId, UpdatePostRequest updatePostRequest);

    void deletePost(Long postId);

    void save(Post post);

}
