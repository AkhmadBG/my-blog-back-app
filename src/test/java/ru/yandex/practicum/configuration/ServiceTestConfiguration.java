package ru.yandex.practicum.configuration;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.yandex.practicum.mapper.CommentMapper;
import ru.yandex.practicum.mapper.PostMapper;
import ru.yandex.practicum.repository.CommentRepository;
import ru.yandex.practicum.repository.PostRepository;
import ru.yandex.practicum.service.CommentService;
import ru.yandex.practicum.service.PostService;
import ru.yandex.practicum.service.impl.CommentServiceImpl;
import ru.yandex.practicum.service.impl.PostServiceImpl;

@TestConfiguration
public class ServiceTestConfiguration {

    @Bean
    public PostRepository mockPostRepository() {
        return Mockito.mock(PostRepository.class);
    }

    @Bean
    public CommentRepository mockCommentRepository() {
        return Mockito.mock(CommentRepository.class);
    }

    @Bean
    public PostService postService(@Qualifier("mockPostRepository") PostRepository postRepository,
                                   PostMapper postMapper) {
        return new PostServiceImpl(postRepository, postMapper);
    }

    @Bean
    public CommentService commentService(@Qualifier("mockCommentRepository") CommentRepository commentRepository,
                                         PostService postService,
                                         CommentMapper commentMapper) {
        return new CommentServiceImpl(commentRepository, postService, commentMapper);
    }

}