package ru.yandex.practicum.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.yandex.practicum.config.PostServiceTestConfig;
import ru.yandex.practicum.dto.CustomPage;
import ru.yandex.practicum.dto.NewPostRequest;
import ru.yandex.practicum.dto.PostResponse;
import ru.yandex.practicum.dto.UpdatePostRequest;
import ru.yandex.practicum.entity.Post;
import ru.yandex.practicum.exception.ImageNotFoundException;
import ru.yandex.practicum.exception.PostNotFoundException;
import ru.yandex.practicum.mapper.PostMapper;
import ru.yandex.practicum.repository.PostRepository;
import ru.yandex.practicum.service.PostService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PostServiceTestConfig.class)
class PostServiceImplTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @BeforeEach
    void setUp() {
        reset(postRepository, postMapper);
    }

    @Test
    void shouldGetPost() {

        Post post = Post.builder()
                .id(1L)
                .title("Title")
                .text("Text")
                .build();

        PostResponse response =
                new PostResponse(
                        1L,
                        "Title",
                        "Text",
                        Set.of(),
                        0,
                        0
                );

        when(postRepository.findPostById(1L))
                .thenReturn(post);

        when(postMapper.mapToPostResponse(post))
                .thenReturn(response);

        PostResponse actual = postService.getPost(1L);

        assertEquals(response, actual);

        verify(postRepository).findPostById(1L);
        verify(postMapper).mapToPostResponse(post);
    }

    @Test
    void shouldThrowWhenPostNotFound() {

        when(postRepository.findPostById(1L))
                .thenThrow(new PostNotFoundException("Пост не найден"));

        assertThrows(
                PostNotFoundException.class,
                () -> postService.getPost(1L)
        );
    }

    @Test
    void shouldAddPost() {

        NewPostRequest request =
                new NewPostRequest(
                        "Title",
                        "Text",
                        Set.of("java")
                );

        Post post = Post.builder()
                .id(1L)
                .title("Title")
                .text("Text")
                .build();

        PostResponse response =
                new PostResponse(
                        1L,
                        "Title",
                        "Text",
                        Set.of("java"),
                        0,
                        0
                );

        when(postRepository.addPost(request))
                .thenReturn(post);

        when(postMapper.mapToPostResponse(post))
                .thenReturn(response);

        PostResponse actual = postService.addPost(request);

        assertEquals(response, actual);

        verify(postRepository).addPost(request);
    }

    @Test
    void shouldUpdatePost() {

        UpdatePostRequest request =
                new UpdatePostRequest(
                        1L,
                        "Updated",
                        "Updated text",
                        Set.of("spring")
                );

        Post post = Post.builder()
                .id(1L)
                .title("Updated")
                .text("Updated text")
                .build();

        PostResponse response =
                new PostResponse(
                        1L,
                        "Updated",
                        "Updated text",
                        Set.of("spring"),
                        0,
                        0
                );

        when(postRepository.updatePost(1L, request))
                .thenReturn(post);

        when(postMapper.mapToPostResponse(post))
                .thenReturn(response);

        PostResponse actual =
                postService.updatePost(1L, request);

        assertEquals(response, actual);
    }

    @Test
    void shouldDeletePost() {

        postService.deletePost(1L);

        verify(postRepository).deletePost(1L);
    }

    @Test
    void shouldIncreaseLikesCount() {

        Post post = Post.builder()
                .id(1L)
                .likesCount(5)
                .build();

        when(postRepository.findPostById(1L))
                .thenReturn(post);

        Long likes = postService.addLike(1L);

        assertEquals(6L, likes);

        verify(postRepository).save(post);
        assertEquals(6L, post.getLikesCount());
    }

    @Test
    void shouldThrowWhenAddLikeToMissingPost() {

        when(postRepository.findPostById(1L))
                .thenThrow(new PostNotFoundException("Пост не найден"));

        assertThrows(
                PostNotFoundException.class,
                () -> postService.addLike(1L)
        );
    }

    @Test
    void shouldIncrementCommentsCount() {

        postService.incrementOrDecrementPostCommentsCount(
                1L,
                true
        );

        verify(postRepository)
                .incrementOrDecrementPostCommentsCount(
                        1L,
                        true
                );
    }

    @Test
    void shouldDecrementCommentsCount() {

        postService.incrementOrDecrementPostCommentsCount(
                1L,
                false
        );

        verify(postRepository)
                .incrementOrDecrementPostCommentsCount(
                        1L,
                        false
                );
    }

    @Test
    void shouldBuildFirstPage() {

        when(postRepository.getPosts("", 1, 5))
                .thenReturn(List.of());

        when(postRepository.countPosts(""))
                .thenReturn(8L);

        CustomPage<PostResponse> page =
                postService.getPosts("", 1, 5);

        assertFalse(page.isHasPrev());
        assertTrue(page.isHasNext());
        assertEquals(2, page.getLastPage());
    }

    @Test
    void shouldBuildLastPage() {

        when(postRepository.getPosts("", 2, 5))
                .thenReturn(List.of());

        when(postRepository.countPosts(""))
                .thenReturn(8L);

        CustomPage<PostResponse> page =
                postService.getPosts("", 2, 5);

        assertTrue(page.isHasPrev());
        assertFalse(page.isHasNext());
        assertEquals(2, page.getLastPage());
    }

    @Test
    void shouldBuildSinglePage() {

        when(postRepository.getPosts("", 1, 10))
                .thenReturn(List.of());

        when(postRepository.countPosts(""))
                .thenReturn(5L);

        CustomPage<PostResponse> page =
                postService.getPosts("", 1, 10);

        assertFalse(page.isHasPrev());
        assertFalse(page.isHasNext());
        assertEquals(1, page.getLastPage());
    }

    @Test
    void shouldGetImage() throws IOException {

        Path tempFile = Files.createTempFile("image", ".jpg");

        byte[] expected = "test image".getBytes();

        Files.write(tempFile, expected);

        Post post = Post.builder()
                .id(1L)
                .imagePath(tempFile.toString())
                .build();

        when(postRepository.findPostById(1L))
                .thenReturn(post);

        byte[] actual = postService.getImage(1L);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldThrowImageNotFoundException() {

        Post post = Post.builder()
                .id(1L)
                .imagePath("not-existing-file.jpg")
                .build();

        when(postRepository.findPostById(1L))
                .thenReturn(post);

        assertThrows(
                ImageNotFoundException.class,
                () -> postService.getImage(1L)
        );
    }

    @Test
    void shouldUploadImage() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "image",
                        "avatar.jpg",
                        "image/jpeg",
                        "content".getBytes()
                );

        Post post = Post.builder()
                .id(1L)
                .build();

        when(postRepository.findPostById(1L))
                .thenReturn(post);

        postService.uploadImage(1L, file);

        verify(postRepository).save(post);

        assertNotNull(post.getImagePath());

        assertTrue(post.getImagePath().contains("1.jpg"));
    }

    @Test
    void shouldShortenLongText() {

        String longText = "a".repeat(150);

        Post post = Post.builder()
                .id(1L)
                .title("Title")
                .text(longText)
                .build();

        PostResponse response =
                postMapper.mapToPostResponseForList(post);

        assertEquals(131, response.text().length());

        assertTrue(response.text().endsWith("..."));
    }

}