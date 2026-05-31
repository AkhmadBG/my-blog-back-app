package ru.yandex.practicum.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.yandex.practicum.configuration.PostServiceTestConfig;
import ru.yandex.practicum.dto.CustomPage;
import ru.yandex.practicum.dto.NewPostRequest;
import ru.yandex.practicum.dto.PostResponse;
import ru.yandex.practicum.dto.UpdatePostRequest;
import ru.yandex.practicum.entity.Post;
import ru.yandex.practicum.mapper.PostMapper;
import ru.yandex.practicum.repository.PostRepository;
import ru.yandex.practicum.service.PostService;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Mockito.reset(postRepository, postMapper);
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
    void shouldIncrementLikeCount() {

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
    void shouldBuildPage() {

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

        when(postRepository.getPosts("java", 1, 5))
                .thenReturn(List.of(post));

        when(postRepository.countPosts("java"))
                .thenReturn(8L);

        when(postMapper.mapToPostResponse(post))
                .thenReturn(response);

        CustomPage<PostResponse> page =
                postService.getPosts(
                        "java",
                        1,
                        5
                );

        assertEquals(2, page.getLastPage());
    }
}