package ru.yandex.practicum.configuration;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.yandex.practicum.mapper.CommentMapper;
import ru.yandex.practicum.repository.CommentRepository;
import ru.yandex.practicum.service.CommentService;
import ru.yandex.practicum.service.PostService;
import ru.yandex.practicum.service.impl.CommentServiceImpl;

@Configuration
public class CommentServiceTestConfiguration {

    @Bean
    @Primary
    public CommentRepository commentRepository() {
        return Mockito.mock(CommentRepository.class);
    }

    @Bean
    public PostService postService() {
        return Mockito.mock(PostService.class);
    }

    @Bean
    public CommentMapper commentMapper() {
        return Mockito.mock(CommentMapper.class);
    }

    @Bean
    public CommentService commentService(CommentRepository repository,
                                         PostService postService,
                                         @Qualifier("commentMapper") CommentMapper mapper) {
        return new CommentServiceImpl(
                repository,
                postService,
                mapper
        );
    }

}