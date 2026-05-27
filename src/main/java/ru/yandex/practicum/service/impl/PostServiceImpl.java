package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.entity.Post;
import ru.yandex.practicum.exception.PostNotFoundException;
import ru.yandex.practicum.mapper.PostMapper;
import ru.yandex.practicum.repository.PostRepository;
import ru.yandex.practicum.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public Page<PostResponse> getPosts(String search, Pageable pageable) {
        return null;
    }

    @Override
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findPostById(postId)
                .orElseThrow(() -> new PostNotFoundException("Пост с id " + postId + " не найден"));
        return postMapper.mapToPostResponse(post);
    }

    @Override
    public PostResponse addPost(NewPostRequest newPostRequest) {
        Post post = postRepository.addPost(newPostRequest);
        return postMapper.mapToPostResponse(post);
    }

    @Override
    public PostResponse updatePost(Long postId, UpdatePostRequest updatePostRequest) {
        Post post = postRepository.updatePost(postId, updatePostRequest);
        return postMapper.mapToPostResponse(post);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deletePost(postId);
    }

    @Override
    public Long addLike(Long postId) {
        Post post = postRepository.findPostById(postId)
                .orElseThrow(() -> new PostNotFoundException("Пост с id " + postId + " не найден"));
        post.setLikesCount(post.getLikesCount() + 1);
        postRepository.save(post);
        return post.getLikesCount();
    }

    @Override
    public byte[] getImage(Long postId) {
        return new byte[0];
    }

    @Override
    public void uploadImage(Long postId, MultipartFile image) {

    }

}