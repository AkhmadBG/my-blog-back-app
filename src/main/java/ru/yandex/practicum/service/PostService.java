package ru.yandex.practicum.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.dto.*;

public interface PostService {

    Page<PostResponse> getPosts(String search, Pageable pageable);

    PostResponse getPost(Long postId);

    PostResponse addPost(NewPostRequest newPostRequest);

    PostResponse updatePost(Long postId, UpdatePostRequest updatePostRequest);

    void deletePost(Long postId);

    Long addLike(Long postId);

    byte[] getImage(Long postId);

    void uploadImage(Long postId, MultipartFile image);

}