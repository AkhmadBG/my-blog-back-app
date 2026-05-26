package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.repository.PostRepository;
import ru.yandex.practicum.service.PostService;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Page<PostResponse> getPosts(String search, Pageable pageable) {
        return null;
    }

    @Override
    public PostResponse getPost(Long postId) {
        return null;
    }

    @Override
    public PostResponse addPost(NewPostRequest newPostRequest) {
        return null;
    }

    @Override
    public PostResponse updatePost(Long postId, UpdatePostRequest updatePostRequest) {
        return null;
    }

    @Override
    public void deletePost(Long postId) {

    }

    @Override
    public Long addLike(Long postId) {
        return 0L;
    }

    @Override
    public byte[] getImage(Long postId) {
        return new byte[0];
    }

    @Override
    public void uploadImage(Long postId, MultipartFile image) {

    }

}