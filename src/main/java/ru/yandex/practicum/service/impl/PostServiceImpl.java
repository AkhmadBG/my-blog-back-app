package ru.yandex.practicum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.entity.Post;
import ru.yandex.practicum.exception.ImageNotFoundException;
import ru.yandex.practicum.mapper.PostMapper;
import ru.yandex.practicum.repository.PostRepository;
import ru.yandex.practicum.service.PostService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    public static final String UPLOAD_DIR = "uploads/";

    @Autowired
    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public CustomPage<PostResponse> getPosts(String search, int pageNumber, int pageSize) {
        List<Post> posts = postRepository.getPosts(search, pageNumber, pageSize);
        long totalCount = postRepository.countPosts(search);
        int lastPage = (int) Math.ceil((double) totalCount / pageSize);
        boolean hasPrev = pageNumber > 1;
        boolean hasNext = pageNumber < lastPage;
        List<PostResponse> responses = posts.stream()
                .map(postMapper::mapToPostResponseForList)
                .toList();
        return new CustomPage<>(responses, hasPrev, hasNext, lastPage);
    }

    @Override
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findPostById(postId);
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
        Post post = postRepository.findPostById(postId);
        post.setLikesCount(post.getLikesCount() + 1);
        postRepository.save(post);
        return post.getLikesCount();
    }

    @Override
    public void incrementOrDecrementPostCommentsCount(Long postId, boolean isIncrement) {
        postRepository.incrementOrDecrementPostCommentsCount(postId, isIncrement);
    }

    @Override
    public byte[] getImage(Long postId) {
        Post post = postRepository.findPostById(postId);
        try {
            Path filePath = Paths.get(post.getImagePath());
            if (!Files.exists(filePath)) {
                throw new ImageNotFoundException("Изображение не найдено");
            }
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Произошла ошибка при чтении изображения", e);
        }
    }

    @Override
    public void uploadImage(Long postId, MultipartFile image) {
        try {
            Path uploadDir = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String originalFilename = image.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            Path filePath = uploadDir.resolve(postId.toString() + extension);
            image.transferTo(filePath);

            Post post = postRepository.findPostById(postId);
            post.setImagePath(filePath.toString());
            postRepository.save(post);
        } catch (IOException e) {
            throw new RuntimeException("Произошла ошибка при загрузке изображения", e);
        }
    }

}