package ru.yandex.practicum.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.yandex.practicum.configuration.CommentServiceTestConfiguration;
import ru.yandex.practicum.dto.CommentResponse;
import ru.yandex.practicum.dto.NewCommentRequest;
import ru.yandex.practicum.dto.UpdateCommentRequest;
import ru.yandex.practicum.entity.Comment;
import ru.yandex.practicum.mapper.CommentMapper;
import ru.yandex.practicum.repository.CommentRepository;
import ru.yandex.practicum.service.CommentService;
import ru.yandex.practicum.service.PostService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CommentServiceTestConfiguration.class)
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentMapper commentMapper;

    @BeforeEach
    void resetMocks() {
        Mockito.reset(
                commentRepository,
                postService,
                commentMapper
        );
    }

    @Test
    void shouldGetComments() {

        Comment comment1 = Comment.builder()
                .id(1L)
                .text("comment1")
                .postId(10L)
                .build();

        Comment comment2 = Comment.builder()
                .id(2L)
                .text("comment2")
                .postId(10L)
                .build();

        CommentResponse response1 =
                new CommentResponse(
                        1L,
                        "comment1",
                        10L
                );

        CommentResponse response2 =
                new CommentResponse(
                        2L,
                        "comment2",
                        10L
                );

        when(commentRepository.findAllByPostId(10L))
                .thenReturn(List.of(comment1, comment2));

        when(commentMapper.mapToCommentResponse(comment1))
                .thenReturn(response1);

        when(commentMapper.mapToCommentResponse(comment2))
                .thenReturn(response2);

        List<CommentResponse> result =
                commentService.getComments(10L);

        assertEquals(2, result.size());

        assertEquals(response1, result.get(0));
        assertEquals(response2, result.get(1));

        verify(commentRepository)
                .findAllByPostId(10L);
    }

    @Test
    void shouldGetComment() {

        Comment comment = Comment.builder()
                .id(1L)
                .text("comment")
                .postId(10L)
                .build();

        CommentResponse response =
                new CommentResponse(
                        1L,
                        "comment",
                        10L
                );

        when(commentRepository.findByPostIdAndCommentId(
                10L,
                1L))
                .thenReturn(comment);

        when(commentMapper.mapToCommentResponse(comment))
                .thenReturn(response);

        CommentResponse result =
                commentService.getComment(
                        10L,
                        1L
                );

        assertEquals(response, result);

        verify(commentRepository)
                .findByPostIdAndCommentId(
                        10L,
                        1L
                );
    }

    @Test
    void shouldAddComment() {

        NewCommentRequest request =
                new NewCommentRequest(
                        "new comment",
                        10L
                );

        Comment comment = Comment.builder()
                .id(1L)
                .text("new comment")
                .postId(10L)
                .build();

        CommentResponse response =
                new CommentResponse(
                        1L,
                        "new comment",
                        10L
                );

        when(commentRepository.addComment(
                10L,
                request))
                .thenReturn(comment);

        when(commentMapper.mapToCommentResponse(comment))
                .thenReturn(response);

        CommentResponse result =
                commentService.addComment(
                        10L,
                        request
                );

        assertEquals(response, result);

        verify(commentRepository)
                .addComment(10L, request);

        verify(postService)
                .incrementOrDecrementPostCommentsCount(
                        10L,
                        true
                );
    }

    @Test
    void shouldUpdateComment() {

        UpdateCommentRequest request =
                new UpdateCommentRequest(
                        1L,
                        "updated",
                        10L
                );

        Comment comment = Comment.builder()
                .id(1L)
                .text("updated")
                .postId(10L)
                .build();

        CommentResponse response =
                new CommentResponse(
                        1L,
                        "updated",
                        10L
                );

        when(commentRepository.updateComment(
                10L,
                1L,
                request))
                .thenReturn(comment);

        when(commentMapper.mapToCommentResponse(comment))
                .thenReturn(response);

        CommentResponse result =
                commentService.updateComment(
                        10L,
                        1L,
                        request
                );

        assertEquals(response, result);

        verify(commentRepository)
                .updateComment(
                        10L,
                        1L,
                        request
                );
    }

    @Test
    void shouldDeleteComment() {

        commentService.deleteComment(
                10L,
                1L
        );

        verify(commentRepository)
                .deleteComment(
                        10L,
                        1L
                );

        verify(postService)
                .incrementOrDecrementPostCommentsCount(
                        10L,
                        false
                );
    }

}