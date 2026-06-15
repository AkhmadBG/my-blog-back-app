package ru.yandex.practicum.service;

import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.dto.*;

public interface PostService {

    CustomPage<PostResponse> getPosts(String search, int pageNumber, int pageSize);

    PostResponse getPost(Long postId);

    PostResponse addPost(NewPostRequest newPostRequest);

    PostResponse updatePost(Long postId, UpdatePostRequest updatePostRequest);

    void deletePost(Long postId);

    Long addLike(Long postId);

    byte[] getImage(Long postId);

    void uploadImage(Long postId, MultipartFile image);

    void incrementOrDecrementPostCommentsCount(Long postId, boolean isIncrement);

}